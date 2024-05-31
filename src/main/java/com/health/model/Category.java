package com.health.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.health.domain.security.UserRole;

@Entity
@Table(name = "category")
public class Category implements Comparable<Category>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "category_id", nullable = false, updatable = false)
    private int categoryId;

    @Column(name = "category_name", nullable = false)
    private String catName;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @Column(name = "Image_path", nullable = false)
    private String posterPath;

    @Column(name = "Description", nullable = false, length = 2000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TopicCategoryMapping> topicCategoryMap = new HashSet<TopicCategoryMapping>();

    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserRole> userRoles = new HashSet<UserRole>();

    @OneToMany(mappedBy = "catId", cascade = CascadeType.ALL)
    private Set<Brouchure> brochures = new HashSet<Brouchure>();

    public Set<Brouchure> getBrochures() {
        return brochures;
    }

    public void setBrochures(Set<Brouchure> brochures) {
        this.brochures = brochures;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<TopicCategoryMapping> getTopicCategoryMap() {
        return topicCategoryMap;
    }

    public void setTopicCategoryMap(Set<TopicCategoryMapping> topicCategoryMap) {
        this.topicCategoryMap = topicCategoryMap;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    @Override
    public int compareTo(Category o) {

        return this.getCatName().compareTo(o.getCatName());

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Category [categoryId=").append(categoryId);
        sb.append(", catName=").append(catName);
        sb.append(", dateAdded=").append(dateAdded);
        sb.append(", status=").append(status);
        sb.append("]");
        return sb.toString();
    }

}
