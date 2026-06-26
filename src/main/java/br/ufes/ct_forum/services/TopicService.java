package br.ufes.ct_forum.services;

import br.ufes.ct_forum.dtos.CreateTopicDto;
import br.ufes.ct_forum.exceptions.NotFoundException;
import br.ufes.ct_forum.models.Topic;
import br.ufes.ct_forum.models.User;
import br.ufes.ct_forum.repositories.TopicRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço responsável por concentrar a lógica de negócio relacionada aos Tópicos.
 */
@Service
public class TopicService {
    private final TopicRepository topicRepository;
    private final UserService userService;

    /**
     * Construtor da classe com Injeção de Dependências.
     *
     * @param topicRepository Repositório para operações de I/O na tabela de tópicos.
     * @param userService     Serviço de domínio de usuários para recuperação de dados de autoria.
     */
    public TopicService(TopicRepository topicRepository, UserService userService) {
        this.topicRepository = topicRepository;
        this.userService = userService;
    }

    /**
     * Retorna uma lista paginada de todos os tópicos cadastrados.
     *
     * @param page Objeto contendo os parâmetros da paginação.
     * @return Um objeto {@link Page} contendo os tópicos da página solicitada e
     * metadados.
     */
    public Page<Topic> findAll(Pageable page) {
        return topicRepository.findAll(page);
    }

    /**
     * Busca um tópico específico pela sua chave primária (ID).
     *
     * @param id O identificador único do tópico.
     * @return A entidade {@link Topic} correspondente.
     * @throws NotFoundException Se nenhum tópico possuir o ID fornecido.
     */
    public Topic findById(long id) {
        return topicRepository.findById(id).orElseThrow(() -> new NotFoundException("Tópico com id " + id + " não encontrado"));
    }

    /**
     * Realiza uma busca paginada por tópicos que contenham uma tag específica.
     *
     * @param tag  A tag em formato de texto a ser procurada.
     * @param page Configurações de limite, deslocamento e ordenação da página.
     * @return Um objeto {@link Page} contendo os tópicos filtrados.
     */
    public Page<Topic> findAllByTags(String tag, Pageable page) {
        return topicRepository.findByTag(tag, page);
    }

    /**
     * Registra um novo tópico no sistema.
     * <p>
     * Este método busca o usuário autor no banco de dados utilizando o identificador
     * recebido. Após garantir a existência do autor, a entidade do tópico é instanciada
     * e salva no banco de dados.
     * </p>
     *
     * @param dto O Data Transfer Object contendo os dados enviados pelo cliente.
     * @return A entidade {@link Topic} recém-criada e persistida (com ID preenchido).
     * @throws NotFoundException Se o usuário fornecido como autor não existir.
     */
    public Topic save(@NonNull CreateTopicDto dto) {
        User author = userService.findById(dto.authorId());
        Topic topic = new Topic(dto.title(), dto.content(), author, dto.tags());
        return topicRepository.save(topic);
    }

    /**
     * Atualiza os dados de um tópico existente de forma parcial e transacional.
     * <p>
     * Aplica apenas as modificações para os campos que não vieram nulos no DTO.
     * </p>
     *
     * @param id  O ID do tópico a ser atualizado.
     * @param dto O DTO contendo os campos novos. Campos com valor {@code null} serão ignorados.
     * @throws NotFoundException Se o tópico a ser atualizado não existir.
     */
    @Transactional
    public void updateById(long id, @NonNull CreateTopicDto dto) {
        Topic topic = findById(id);

        if (dto.title() != null) topic.setTitle(dto.title());

        if (dto.content() != null) topic.setContent(dto.content());

        if (dto.tags() != null) topic.setTags(dto.tags());
    }

    /**
     * Remove fisicamente um tópico do banco de dados.
     *
     * @param id O identificador do tópico a ser removido.
     * @throws NotFoundException Se o ID fornecido não existir previamente.
     */
    public void deleteById(long id) {
        if (!topicRepository.existsById(id)) {
            throw new NotFoundException("Tópico com id " + id + " não encontrado");
        }

        topicRepository.deleteById(id);
    }
}
