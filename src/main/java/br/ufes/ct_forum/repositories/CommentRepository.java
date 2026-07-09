package br.ufes.ct_forum.repositories;

import br.ufes.ct_forum.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório para operações de persistência da entidade {@link Comment}.
 * <p>
 * Atua como a camada de acesso a dados.
 * </p>
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    /**
     * Busca todos os comentários feitos por um usuário específico, retornando
     * os resultados em lotes (paginados).
     *
     * @param authorId O ID do usuário (autor) cujos comentários serão buscados.
     * @param pageable Configurações da página.
     * @return Um objeto {@link Page} contendo a lista de comentários da página e os metadados.
     */
    Page<Comment> findByAuthorId(long authorId, Pageable pageable);

    /**
     * Conta todos os comentários de um tópico
     * @param topicId O ID do tópico
     * @return quantidade de comentários feitos no tópico
     */
    long countByTopicId(long topicId);

    /**
     * Busca todas as respostas diretas (comentários filhos) de um comentário específico,
     * de forma paginada.
     * 
     * @param parentId O ID do comentário pai (nó superior na hierarquia).
     * @param pageable Configurações da página.
     * @return Um objeto {@link Page} contendo as respostas encontradas.
     */
    Page<Comment> findByParentId(long parentId, Pageable pageable);

    @Query("select c from Comment c join fetch c.author where c.topic.id = :topicId order by c.createdAt asc")
    List<Comment> findByTopicIdWithAuthor(@Param("topicId") long topicId);
}
