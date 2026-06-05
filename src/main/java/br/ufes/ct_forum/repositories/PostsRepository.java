package br.ufes.ct_forum.repositories;

import br.ufes.ct_forum.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Post, Long> {
    Page<Post> findByAuthorId(long authorId, Pageable pageable);
}
