package br.ufes.ct_forum.services;

import br.ufes.ct_forum.dtos.CreateUserDto;
import br.ufes.ct_forum.exceptions.EmailAlreadyExists;
import br.ufes.ct_forum.exceptions.NotFoundException;
import br.ufes.ct_forum.exceptions.PasswordsDoNotMatch;
import br.ufes.ct_forum.models.User;
import br.ufes.ct_forum.repositories.UsersRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

/**
 * Serviço responsável por concentrar a lógica de negócio relacionada aos Usuários.
 */
@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final PasswordService passwordService;

    /**
     * Construtor da classe com Injeção de Dependências.
     * * @param usersRepository Repositório para operações de I/O na tabela de usuários.
     * @param passwordService Serviço isolado para o hash seguro de credenciais.
     */
    public UsersService(UsersRepository usersRepository, PasswordService passwordService) {
        this.usersRepository = usersRepository;
        this.passwordService = passwordService;
    }

    /**
     * Retorna uma lista paginada de todos os usuários cadastrados.
     *
     * @param page Objeto contendo os parâmetros da paginação.
     * @return Um objeto {@link Page} contendo os usuários da página solicitada e 
     * metadados.
     */
    public Page<User> findAll(Pageable page) {
        return usersRepository.findAll(page);
    }

    /**
     * Busca um usuário específico pela sua chave primária (ID).
     *
     * @param id O identificador único do usuário.
     * @return A entidade {@link User} correspondente.
     * @throws NotFoundException Se nenhum usuário possuir o ID fornecido.
     */
    public User findById(long id) {
        return usersRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuário com id " + id + " não encontrado"));
    }

    /**
     * Busca um usuário utilizando seu endereço de e-mail.
     *
     * @param email O e-mail exato cadastrado.
     * @return A entidade {@link User} correspondente.
     * @throws NotFoundException Se o e-mail não estiver registrado no sistema.
     */
    public User findByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Usuário com email " + email + " não encontrado"));
    }

    /**
     * Registra um novo usuário no sistema.
     * <p>
     * Este método valida a igualdade das senhas e a disponibilidade do e-mail. 
     * Somente após estas checagens em memória a senha é criptografada e o 
     * objeto é salvo no banco.
     * </p>
     *
     * @param dto O Data Transfer Object contendo os dados crus enviados pelo cliente.
     * @return A entidade {@link User} recém-criada e persistida (com ID preenchido).
     * @throws PasswordsDoNotMatch Se a senha e a confirmação de senha diferirem.
     * @throws EmailAlreadyExists  Se já houver um registro ativo com o mesmo e-mail.
     */
    public User save(@NonNull CreateUserDto dto) {
        if (!Objects.equals(dto.password(), dto.passwordConfirm())) throw new PasswordsDoNotMatch();

        Optional<User> existingUser = usersRepository.findByEmail(dto.email());

        if (existingUser.isPresent()) throw new EmailAlreadyExists();

        String passwordHash = passwordService.hash(dto.password());
        User user = new User(dto.name(), dto.email(), passwordHash, dto.role());
        return usersRepository.save(user);
    }

    /**
     * Atualiza os dados de um usuário existente de forma parcial e transacional.
     * <p>
     * Aplica apenas as modificações para os campos que não vieram nulos no DTO. 
     * No caso da alteração de e-mail, verifica ativamente se o novo e-mail já 
     * não pertence a <i>outro</i> usuário no banco de dados.
     * </p>
     *
     * @param id  O ID do usuário a ser atualizado.
     * @param dto O DTO contendo os campos novos. Campos com valor {@code null} serão ignorados.
     * @throws NotFoundException   Se o usuário a ser atualizado não existir.
     * @throws PasswordsDoNotMatch Se houver intenção de mudar a senha e as novas senhas não baterem.
     * @throws EmailAlreadyExists  Se houver intenção de mudar o e-mail e ele já estiver em uso.
     */
    @Transactional
    public void updateById(long id, @NonNull CreateUserDto dto) {
        User user = usersRepository.findById(id).orElseThrow(() -> new NotFoundException("Usuário com id " + id + " não encontrado"));

        if (dto.email() != null) {
            usersRepository.findByEmail(dto.email()).ifPresent(found -> {
                if (found.getId() != id) throw new EmailAlreadyExists();
            });
            user.setEmail(dto.email());
        }

        if (dto.password() != null) {
            if (!Objects.equals(dto.password(), dto.passwordConfirm())) throw new PasswordsDoNotMatch();
            user.setPasswordHash(passwordService.hash(dto.password()));
        }

        if (dto.name() != null) user.setName(dto.name());
        if (dto.role() != null) user.setRole(dto.role());
    }

    /**
     * Remove fisicamente um usuário do banco de dados.
     *
     * @param id O identificador do usuário a ser removido.
     * @throws NotFoundException Se o ID fornecido não existir previamente.
     */
    public void deleteById(long id) {
        if (!usersRepository.existsById(id)) throw new NotFoundException("Usuário com id " + id + " não encontrado");

        usersRepository.deleteById(id);
    }
}
