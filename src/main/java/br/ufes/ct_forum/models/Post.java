package br.ufes.ct_forum.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entidade base que representa uma publicação no fórum.
 * <p>
 * Classe abstrata que serve como base para os tipos específicos
 * de publicações (Tópicos e Comentários).
 * </p>
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "posts")
public abstract class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(nullable = false)
    private String content;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * O autor da publicação. 
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

   /**
     * Construtor padrão sem argumentos, exigido pela especificação da JPA.
     */ 
    public Post() {
    }

    /**
     * Cria uma nova publicação.
     * A data de criação ({@code createdAt}) é inicializada automaticamente 
     * no momento da instanciação.
     *
     * @param content O conteúdo em texto da publicação.
     * @param author  O usuário criador da publicação.
     */
    public Post(String content, User author) {
        this.content = content;
        this.author = author;
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Callback do JPA.
     * Executado antes de um UPDATE ser enviado para o banco de dados.
     */
    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Verifica se a publicação sofreu alguma edição desde que foi criada.
     * A anotação {@code @Transient} informa à que este método é apenas uma
     * regra de negócio em memória e não corresponde a um atributo da classe.
     *
     * @return {@code true} se a data de atualização não for nula (ou seja, se o post foi editado),
     * {@code false} caso contrário.
     */
    @Transient
    public boolean isEdited() {
        return updatedAt != null;
    }

    // --- Getters e Setters (omitidos do javadoc por serem autoexplicativos) ---
    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
