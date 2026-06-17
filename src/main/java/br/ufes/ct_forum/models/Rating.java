package br.ufes.ct_forum.models;

import jakarta.persistence.*;

/**
 * Entidade associativa que registra a avaliação (voto) de um usuário em uma publicação.
 * <p>
 * Esta classe resolve o relacionamento de muitos-para-muitos entre as entidades
 * {@link User} e {@link Post}. A avaliação é estritamente binária (positiva ou negativa).
 * </p>
 * <p>
 * A tabela correspondente ("ratings") possui uma restrição de unicidade composta 
 * nas colunas de publicação e usuário, garantindo que um usuário não possa
 * avaliar o mesmo post mais de uma vez.
 * </p>
 */
@Entity
@Table(name = "ratings", uniqueConstraints = {@UniqueConstraint(columnNames = {"post_id", "user_id"})})
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    /**
     * Define o tipo da avaliação.
     * <p>
     * {@code true} representa uma avaliação positiva, 
     * enquanto {@code false} representa uma avaliação negativa.
     * </p>
     */
    @Column(name = "is_positive", nullable = false)
    private boolean isPositive;

    /**
     * A publicação (tópico ou comentário) que está sendo avaliada.
     */
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    /**
     * O usuário que avaliou.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Construtor padrão sem argumentos, exigido pela especificação da JPA.
     */ 
    public Rating() {
    }

    /**
     * Cria um novo registro de avaliação de um usuário para uma publicação.
     *
     * @param isPositive Indica se a avaliação é positiva (true) ou negativa (false).
     * @param post       A publicação sendo avaliada.
     * @param user       O usuário autor da avaliação.
     */
    public Rating(boolean isPositive, Post post, User user) {
        this.isPositive = isPositive;
        this.post = post;
        this.user = user;
    }

    // --- Getters e Setters (Omitidos os comentários por serem autoexplicativos) ---
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
