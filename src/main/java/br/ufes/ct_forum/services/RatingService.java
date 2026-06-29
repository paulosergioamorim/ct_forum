package br.ufes.ct_forum.services;

import br.ufes.ct_forum.dtos.CreateRatingDto;
import br.ufes.ct_forum.exceptions.NotFoundException;
import br.ufes.ct_forum.models.Post;
import br.ufes.ct_forum.models.Rating;
import br.ufes.ct_forum.models.User;
import br.ufes.ct_forum.repositories.PostRepository;
import br.ufes.ct_forum.repositories.RatingRepository;
import br.ufes.ct_forum.repositories.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço responsável por concentrar a lógica de negócio relacionada às Avaliações (Ratings).
 * <p>
 * Gerencia o registro, atualização, contagem e remoção de votos associados
 * às publicações do fórum.
 * </p>
 */
@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    /**
     * Construtor da classe com Injeção de Dependências.
     *
     * @param ratingRepository Repositório para operações de I/O na tabela de avaliações[cite: 20].
     * @param userRepository   Repositório para busca da entidade do usuário autor.
     * @param postRepository   Repositório genérico para busca da publicação alvo.
     */
    public RatingService(RatingRepository ratingRepository, UserRepository userRepository, PostRepository postRepository) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    /**
     * Cria uma nova avaliação (voto) para uma publicação.
     * <p>
     * Valida ativamente a restrição de unicidade[cite: 18], impedindo que um usuário
     * avalie a mesma publicação mais de uma vez. Se o voto já existir, uma exceção de estado
     * ilegal é lançada.
     * </p>
     *
     * @param dto O Data Transfer Object contendo os identificadores e o tipo de voto[cite: 19].
     * @return A entidade {@link Rating} recém-criada e persistida.
     * @throws IllegalStateException Se o usuário já possuir uma avaliação registrada para este post.
     * @throws NotFoundException     Se o usuário ou a publicação informados não existirem no banco.
     */
    @Transactional
    public Rating save(@NonNull CreateRatingDto dto) {
        ratingRepository.findByPostIdAndUserId(dto.postId(), dto.userId())
                .ifPresent(existing -> {
                    throw new IllegalStateException("O usuário já avaliou esta publicação.");
                });

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
        Post post = postRepository.findById(dto.postId())
                .orElseThrow(() -> new NotFoundException("Publicação não encontrada"));

        Rating newRating = new Rating(dto.isPositive(), post, user);

        return ratingRepository.save(newRating);
    }

    /**
     * Atualiza a intenção (positiva ou negativa) de uma avaliação já existente.
     *
     * @param dto O Data Transfer Object contendo os identificadores para localizar a avaliação
     *            e o novo valor do voto[cite: 19].
     * @return A entidade {@link Rating} atualizada.
     * @throws NotFoundException Se a avaliação prévia não for encontrada no banco de dados.
     */
    @Transactional
    public Rating update(@NonNull CreateRatingDto dto) {
        Rating rating = ratingRepository.findByPostIdAndUserId(dto.postId(), dto.userId())
                .orElseThrow(() -> new NotFoundException("Avaliação prévia não encontrada para atualização."));

        rating.setPositive(dto.isPositive());

        return ratingRepository.save(rating);
    }

    /**
     * Contabiliza o total de avaliações positivas associadas a uma publicação.
     *
     * @param postId O identificador da publicação (tópico ou comentário).
     * @return O somatório de votos positivos obtidos a partir do repositório[cite: 20].
     */
    public long countPositiveRatings(long postId) {
        return ratingRepository.countByPostIdAndIsPositive(postId, true);
    }

    /**
     * Contabiliza o total de avaliações negativas associadas a uma publicação.
     *
     * @param postId O identificador da publicação (tópico ou comentário).
     * @return O somatório de votos negativos obtidos a partir do repositório[cite: 20].
     */
    public long countNegativeRatings(long postId) {
        return ratingRepository.countByPostIdAndIsPositive(postId, false);
    }

    /**
     * Remove fisicamente uma avaliação do banco de dados, caso ela exista.
     * <p>
     * Este método é utilizado para anular o voto de um usuário em uma publicação,
     * apagando o registro associativo da tabela[cite: 18].
     * </p>
     *
     * @param postId O identificador da publicação associada à avaliação.
     * @param userId O identificador do usuário autor da avaliação.
     */
    @Transactional
    public void deleteRating(long postId, long userId) {
        ratingRepository.findByPostIdAndUserId(postId, userId)
                .ifPresent(ratingRepository::delete);
    }
}
