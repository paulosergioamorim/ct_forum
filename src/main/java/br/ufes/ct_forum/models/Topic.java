package br.ufes.ct_forum.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Entity
public final class Topic extends Post {
    /*
    Devido ao uso de SINGLE_TABLE, esta coluna
    deve ser anulável no banco de dados, e deve
    ser verificada como não nula apenas na aplicação
     */
    @NotNull
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(columnDefinition = "text[]")
    private String[] tags;

    public Topic() {
    }

    public Topic(String content, LocalDateTime createdAt, LocalDateTime updatedAt, long authorId, String[] tags) {
        super(content, createdAt, updatedAt, authorId);
        this.tags = tags;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
