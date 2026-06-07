package br.ufes.ct_forum.models;

import jakarta.persistence.*;

@Entity
@Table(name = "ratings", uniqueConstraints = {@UniqueConstraint(columnNames = {"post_id", "user_id"})})
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "is_positive", nullable = false)
    private boolean isPositive;
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Rating() {
    }

    public Rating(boolean isPositive, Post post, User user) {
        this.isPositive = isPositive;
        this.post = post;
        this.user = user;
    }

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
