package br.ufes.ct_forum.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * Entidade que representa um Tópico de discussão no fórum.
 * <p>
 * Sendo uma extensão de {@link Post}, o Tópico herda atributos base como
 * conteúdo, autor e datas de criação/atualização. Ele atua como a publicação
 * principal que pode receber múltiplos comentários.
 * </p>
 */
@Entity
public class Topic extends Post {
    /**
     * Lista de tags associadas ao tópico para facilitar a categorização e busca.
     * <p>
     * <b>Nota:</b> Devido ao uso de {@code SINGLE_TABLE},
     * esta coluna deve ser anuçável no banco de dados. A presença da coluna para
     * os tópicos deve ser verificada apenas na aplicação.
     * </p>
     */
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(columnDefinition = "text[]")
    private String[] tags;

    /**
     * O título do tópico.
     */
    @Column(nullable = false)
    private String title;

    /**
     * Construtor padrão sem argumentos, exigido pela especificação da JPA.
     */ 
    public Topic() {
    }

    /**
     * Cria um novo Tópico com título, conteúdo, autor e tags.
     *
     * @param title   O título da discussão.
     * @param content O conteúdo detalhado ou pergunta inicial.
     * @param author  O usuário que criou o tópico.
     * @param tags    Um array de categorias/tags relevantes para o assunto.
     */
    public Topic(String title, String content, User author, String[] tags) {
        super(content, author);
        this.title = title;
        this.tags = tags;
    }

    // --- Getters e Setters (omitidos do javadoc por serem autoexplicativos) ---
    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
