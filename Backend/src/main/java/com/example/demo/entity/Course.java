package com.example.demo.entity;

// Course.java
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;

    private String level; // "Beginner", "Intermediate"

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Lesson> lessons = new HashSet<>();

    // --- Constructor ---
    public Course() {
    }

    public Course(String title, String description, String level) {
        this.title = title;
        this.description = description;
        this.level = level;
    }

    // --- Getters and Setters ---
    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Set<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(Set<Lesson> lessons) {
        this.lessons = lessons;
    }

    // --- equals(), hashCode(), toString() ---
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Course course = (Course) o;
        return courseId != null && Objects.equals(courseId, course.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", title='" + title + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}