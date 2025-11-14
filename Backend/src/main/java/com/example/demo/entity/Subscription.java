package com.example.demo.entity;

// Subscription.java
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionId;

    @Column(nullable = false)
    private String planName;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String status; // "ACTIVE", "EXPIRED"

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // --- Constructor ---

    public Subscription() {
    }

    public Subscription(String planName, LocalDate startDate, LocalDate endDate, String status, User user) {
        this.planName = planName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.user = user;
    }

    // --- Getters and Setters ---

    public Long getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Long subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // --- equals(), hashCode(), toString() ---

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Subscription that = (Subscription) o;
        return subscriptionId != null && Objects.equals(subscriptionId, that.subscriptionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscriptionId);
    }

}