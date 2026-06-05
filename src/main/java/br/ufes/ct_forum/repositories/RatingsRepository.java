package br.ufes.ct_forum.repositories;

import br.ufes.ct_forum.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingsRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByPostIdAndUserId(long postId, long userId);
    long countByPostIdAndIsPositive(long postId, boolean isPositive);
}
