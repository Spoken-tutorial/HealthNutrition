package com.health.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private int commentId;

    @Column(length = 2000)
    private String comment;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    private String type;

    private String roleName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Tutorial_id")
    private Tutorial tutorialInfos;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp timestamp) {
        this.dateAdded = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tutorial getTutorialInfos() {
        return tutorialInfos;
    }

    public void setTutorialInfos(Tutorial tutorialInfos) {
        this.tutorialInfos = tutorialInfos;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Comment [commentId=").append(commentId);
        sb.append(", comment=").append(comment);
        sb.append(", dateAdded=").append(dateAdded);
        sb.append(", type=").append(type);
        sb.append("]");
        return sb.toString();
    }

}
