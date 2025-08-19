package com.health.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;
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

/**
 * category Object to store category related data on database
 * 
 * @author om prakash soni
 * @version 1.0
 */
@Entity
@Table(name = "category")
public class Category implements Comparable<Category>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * unique id to identify object
     */
    @Id
    @Column(name = "category_id", nullable = false, updatable = false)
    private int categoryId;

    /**
     * category name
     */
    @Column(name = "category_name", nullable = false)
    private String catName;

    @Column(name = "orderValue", nullable = false)
    private int order = 0;

    /**
     * Timestamp on added
     */
    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    /**
     * boolean value to mark visible/invisible in application
     */
    @Column(name = "status", nullable = false)
    private boolean status = true;

    /**
     * relative path of image stored
     */
    @Column(name = "Image_path", nullable = false)
    private String posterPath;

    /**
     * description of category
     */
    @Column(name = "Description", nullable = false, length = 2000)
    private String description;

    /**
     * user who created it
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<TopicCategoryMapping> topicCategoryMap = new HashSet<TopicCategoryMapping>();

    @OneToMany(mappedBy = "cat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserRole> userRoles = new HashSet<UserRole>();

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SubCategory> subCategoriess = new HashSet<SubCategory>();

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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Set<SubCategory> getSubCategoriess() {
        return subCategoriess;
    }

    public void setSubCategoriess(Set<SubCategory> subCategoriess) {
        this.subCategoriess = subCategoriess;

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
        // TODO Auto-generated method stub

        return this.getCatName().compareTo(o.getCatName());

    }

    public static Comparator<Category> SortByOrderValue = new Comparator<Category>() {

        // Method
        @Override
        public int compare(Category c1, Category c2) {

            if (c1.getOrder() == c2.getOrder()) {
                return c1.getCatName().compareTo(c2.getCatName());
            }

            else if (c1.getOrder() > c2.getOrder()) {
                return 1;
            } else {
                return -1;
            }

        }
    };

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
