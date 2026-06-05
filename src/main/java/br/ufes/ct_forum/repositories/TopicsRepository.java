package br.ufes.ct_forum.repositories;

import br.ufes.ct_forum.models.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicsRepository extends JpaRepository<Topic, Long> {
    Page<Topic> findByAuthorId(long authorId, Pageable pageable);

    @Query(value = "select * from posts where :tag = any(tags)", nativeQuery = true)
    Page<Topic> findByTag(@Param("tag") String tag, Pageable pageable);
}
