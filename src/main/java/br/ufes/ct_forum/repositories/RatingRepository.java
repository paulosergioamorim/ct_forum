package br.ufes.ct_forum.repositories;

import br.ufes.ct_forum.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositório para operações de persistência da entidade associativa {@link Rating}.
 * <p>
 * Centraliza o acesso aos dados de avaliação do fórum. Como esta 
 * interface lida com a relação entre usuários e publicações, seus métodos são 
 * importantes tanto para validar restrições de voto único quanto para computar os 
 * contadores de engajamento expostos na interface.
 * </p>
 */
@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    /**
     * Busca uma avaliação específica cruzando os identificadores da publicação e do usuário.
     *
     * @param postId O ID da publicação que recebeu o voto.
     * @param userId O ID do usuário que realizou a votação.
     * @return Um {@link Optional} contendo a avaliação se ela existir, ou vazio caso contrário.
     */
    Optional<Rating> findByPostIdAndUserId(long postId, long userId);

    /**
     * Conta a quantidade total de avaliações de um determinado tipo (positiva ou negativa) 
     * que uma publicação recebeu.
     *
     * @param postId     O ID da publicação (Tópico ou Comentário) a ser contabilizada.
     * @param isPositive {@code true} para contar as avaliações positivas, {@code false} 
     * para contar as avaliações negativas.
     * @return O número total de avaliações que correspondem aos critérios fornecidos.
     */
    long countByPostIdAndIsPositive(long postId, boolean isPositive);
}
