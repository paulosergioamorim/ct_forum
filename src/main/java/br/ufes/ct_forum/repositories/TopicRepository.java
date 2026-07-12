package br.ufes.ct_forum.repositories;

import br.ufes.ct_forum.models.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para operações de persistência específicas da entidade {@link Topic}.
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    /**
     * Busca de forma paginada todos os tópicos iniciados por um usuário específico.
     *
     * @param authorId O ID do usuário criador do tópico.
     * @param pageable Configurações da página.
     * @return Um objeto {@link Page} contendo os tópicos encontrados.
     */
    Page<Topic> findByAuthorId(long authorId, Pageable pageable);

    /**
     * Realiza uma busca paginada por tópicos que contenham uma tag específica 
     * em seu array de tags.

     * @param tag      A tag em formato de texto a ser procurada no array.
     * @param pageable Configurações de limite, deslocamento e ordenação da página.
     * @return Um objeto {@link Page} contendo os tópicos que possuem a tag.
     */
    @Query(value = "select * from posts where :tag = any(tags)", nativeQuery = true)
    Page<Topic> findByTag(@Param("tag") String tag, Pageable pageable);
    
    @Query("select t from Topic t join fetch t.author where t.id = :id")
    Optional<Topic> findByIdWithAuthor(@Param("id") long id);
}
