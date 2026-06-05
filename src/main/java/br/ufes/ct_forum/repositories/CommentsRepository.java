package br.ufes.ct_forum.repositories;

import br.ufes.ct_forum.models.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByAuthorId(long authorId, Pageable pageable);
    Page<Comment> findByParentId(long parentId, Pageable pageable);
}
