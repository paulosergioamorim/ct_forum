package br.ufes.ct_forum.repositories;

import br.ufes.ct_forum.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações de persistência da entidade {@link Comment}.
 * <p>
 * Atua como a camada de acesso a dados.
 * </p>
 */
@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {
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
     * Busca todas as respostas diretas (comentários filhos) de um comentário específico,
     * de forma paginada.
     * 
     * @param parentId O ID do comentário pai (nó superior na hierarquia).
     * @param pageable Configurações da página.
     * @return Um objeto {@link Page} contendo as respostas encontradas.
     */
    Page<Comment> findByParentId(long parentId, Pageable pageable);
}
