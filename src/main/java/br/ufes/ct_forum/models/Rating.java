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
    @Column(name = "post_id", nullable = false)
    private long postId;
    @Column(name = "user_id", nullable = false)
    private long userId;
    @ManyToOne
    @JoinColumn(name = "post_id", updatable = false, insertable = false)
    private Post post;
    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    private User user;

    public Rating() {
    }

    public Rating(boolean isPositive, long postId, long userId) {
        this.isPositive = isPositive;
        this.postId = postId;
        this.userId = userId;
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
