package br.ufes.ct_forum.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
public class Topic extends Post {
    /*
    Devido ao uso de SINGLE_TABLE, esta coluna
    deve ser anulável no banco de dados, e deve
    ser verificada como não nula apenas na aplicação
     */
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(columnDefinition = "text[]")
    private String[] tags;

    @Column(nullable = false)
    private String title;

    public Topic() {
    }

    public Topic(String title, String content, User author, String[] tags) {
        super(content, author);
        this.title = title;
        this.tags = tags;
    }

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
