package com.health.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private int courseId;

    @Column(name = "course_name", nullable = false, unique = true)
    private String courseName;

    @Column(name = "date_added", nullable = false)
    private Timestamp dateAdded;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CourseCatTopicMapping> courseCatTopicMappings = new HashSet<CourseCatTopicMapping>();

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

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Set<CourseCatTopicMapping> getCourseCatTopicMapping() {
        return courseCatTopicMappings;
    }

    public void setCourseCatTopicMapping(Set<CourseCatTopicMapping> courseCatTopicMappings) {
        this.courseCatTopicMappings = courseCatTopicMappings;
    }

    public Course(String courseName, Timestamp dateAdded) {

        this.courseName = courseName;
        this.dateAdded = dateAdded;
    }

    public Course(String courseName, Timestamp dateAdded, Set<CourseCatTopicMapping> courseCatTopicMappings) {

        this.courseName = courseName;
        this.dateAdded = dateAdded;
        this.courseCatTopicMappings = courseCatTopicMappings;
    }

    public Course() {

    }

}
