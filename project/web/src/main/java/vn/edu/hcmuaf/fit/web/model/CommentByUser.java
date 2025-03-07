package vn.edu.hcmuaf.fit.web.model;

import java.io.Serializable;

public class CommentByUser implements Serializable {
    private Comment comment;
    private User user;

    public CommentByUser() {
    }

    public CommentByUser(Comment comment, User user) {
        this.comment = comment;
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

