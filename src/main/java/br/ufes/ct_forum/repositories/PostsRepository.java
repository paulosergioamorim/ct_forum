package br.ufes.ct_forum.repositories;

import br.ufes.ct_forum.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações de persistência da entidade base {@link Post}.
 * <p>
 * Como a classe {@link Post} utiliza a estratégia de herança {@code SINGLE_TABLE}, 
 * este repositório é capaz de realizar consultas polimórficas. Isso significa que 
 * buscar dados por meio desta interface pode retornar tanto instâncias de 
 * {@link br.ufes.ct_forum.models.Topic} quanto de {@link br.ufes.ct_forum.models.Comment}.
 * </p>
 */
@Repository
public interface PostsRepository extends JpaRepository<Post, Long> {
    /**
     * Busca o histórico de publicações de um usuário, retornando de forma 
     * paginada.
     *
     * @param authorId O ID do usuário cujo histórico de publicações será listado.
     * @param pageable Parâmetros de paginação e ordenação (ex: ordenar por data de criação).
     * @return Um objeto {@link Page} contendo a lista polimórfica de publicações (Tópicos e Comentários).
     */
    Page<Post> findByAuthorId(long authorId, Pageable pageable);
}
