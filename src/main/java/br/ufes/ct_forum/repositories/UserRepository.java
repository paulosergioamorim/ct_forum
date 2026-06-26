package br.ufes.ct_forum.repositories;

import br.ufes.ct_forum.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para operações de persistência da entidade {@link User}.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Busca um usuário baseando-se estritamente no seu endereço de e-mail.
     *
     * @param email O endereço de e-mail a ser pesquisado.
     * @return Um {@link Optional} contendo o usuário se o e-mail existir, ou vazio caso contrário.
     */
    Optional<User> findByEmail(String email);
}
