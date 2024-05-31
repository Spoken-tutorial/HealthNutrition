package com.health.domain.security;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.health.model.Category;
import com.health.model.Language;
import com.health.model.User;

@Entity
@Table(name = "user_role")
public class UserRole implements Comparable<UserRole>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long userRoleId;

    private Timestamp created;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lan_id")
    private Language lan;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cat_id")
    private Category cat;

    private boolean status = false;

    private boolean revoked = false;

    private boolean rejected = false;

    public Category getCategory() {
        return cat;
    }

    public void setCategory(Category category) {
        this.cat = category;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public UserRole() {

    }

    public UserRole(User user, Role role) {
        this.user = user;
        this.role = role;
    }

    public UserRole(User user, boolean status) {
        this.user = user;
        this.status = status;
    }

    public Long getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Language getLanguage() {
        return lan;
    }

    public void setLanguage(Language language) {
        this.lan = language;
    }

    public Language getLan() {
        return lan;
    }

    public void setLan(Language lan) {
        this.lan = lan;
    }

    public Category getCat() {
        return cat;
    }

    public void setCat(Category cat) {
        this.cat = cat;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public static Comparator<UserRole> RoleNameComp = new Comparator<UserRole>() {

        @Override
        public int compare(UserRole ur1, UserRole ur2) {

            if (ur1.getCategory() != null && ur2.getCategory() != null && ur1.getLanguage() != null
                    && ur2.getLanguage() != null && ur1.getRole().getName() == ur2.getRole().getName()
                    && ur1.getCategory().getCatName() == ur2.getCategory().getCatName()) {

                return ur1.getLanguage().getLangName().compareTo(ur2.getLanguage().getLangName());

            }

            else if (ur1.getCategory() != null && ur2.getCategory() != null
                    && ur1.getRole().getName() == ur2.getRole().getName()) {

                return ur1.getCategory().getCatName().compareTo(ur2.getCategory().getCatName());
            }

            else {

                return ur1.getRole().getName().compareTo(ur2.getRole().getName());
            }

        }
    };

    @Override
    public int compareTo(UserRole o) {

        this.getUser().getFirstName();
        return this.getUser().getFirstName().compareToIgnoreCase(o.getUser().getFirstName());

    }

}
