package br.ufes.ct_forum.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public final class Comment extends Post {
    @Column(name = "parent_id")
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Comment parent;

    public Comment() {
    }

    public Comment(String content, long authorId) {
        super(content, authorId);
    }

    public Comment(String content, long authorId, long parentId) {
        super(content, authorId);
        this.parentId = parentId;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    @Transient
    public boolean isRootComment() {
        return parentId == null;
    }
}
