package com.health.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class OrganizationRole implements Comparable<OrganizationRole>, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "org_role_id", updatable = false, nullable = false)
    private int roleId;

    @Column(nullable = false)
    private String role;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    @Override
    public int compareTo(OrganizationRole o) {

        return this.getRole().compareTo(o.getRole());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OrganizationRole [roleId=").append(roleId);
        sb.append(", role=").append(role);
        sb.append(", dateAdded=").append(dateAdded);
        sb.append("]");
        return sb.toString();
    }

}
