package br.ufes.ct_forum.models;

import jakarta.persistence.*;

@Entity
public class Comment extends Post {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    public Comment() {
    }

    public Comment(String content, User author) {
        super(content, author);
    }

    public Comment(String content, User author, Comment parent) {
        super(content, author);
        this.parent = parent;
    }

    public Comment getParent() {
        return parent;
    }

    public void setParent(Comment parent) {
        this.parent = parent;
    }

    @Transient
    public boolean isRootComment() {
        return parent == null;
    }
}
