package br.ufes.ct_forum.models;

import jakarta.persistence.*;

/**
 * Entidade que representa um Comentário ou Resposta no fórum.
 * <p>
 * Estende a classe {@link Post}, herdando atributos como autor, conteúdo e datas.
 * Os comentários suportam uma estrutura recursiva, permitindo que um comentário
 * seja uma resposta direta a outro comentário através do atributo {@code parent}.
 * </p>
 *
 */
@Entity
public class Comment extends Post {
    /**
     * O comentário pai ao qual este comentário está respondendo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    /**
     * Construtor padrão sem argumentos, exigido pela especificação da JPA.
     */ 
    public Comment() {
    }

    /**
     * Cria um comentário que responde diretamente ao tópico e não a outro
     * comentário.
     *
     * @param content O conteúdo em texto do comentário.
     * @param author  O usuário que está comentando.
     */
    public Comment(String content, User author) {
        // Aqui não precisa de nada indicando o post relacionado não ??
        super(content, author);
    }

    /**
     * Cria um comentário que é uma resposta a um comentário existente.
     *
     * @param content O conteúdo em texto da resposta.
     * @param author  O usuário que está respondendo.
     * @param parent  O comentário que está sendo respondido.
     */
    public Comment(String content, User author, Comment parent) {
        super(content, author);
        this.parent = parent;
    }

    // --- Getters e Setters (omitidos do javadoc por serem autoexplicativos) ---
    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    /**
     * Verifica se este comentário é uma raiz (responde diretamente ao post).
     * <p>
     * A anotação {@code @Transient} informa à que este método é apenas uma
     * regra de negócio em memória e não corresponde a um atributo da classe.
     * </p>
     *
     * @return {@code true} se não possuir um comentário pai, {@code false} caso seja uma resposta a outro comentário.
     */
    @Transient
    public boolean isRootComment() {
        return parent == null;
    }
}
