package com.sendfriend.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Beta {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String description;

    @NotNull
    @Column(name = "isPublic")
    private boolean isPublic = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "shares", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "beta_id"))
    private List<User> shares = new ArrayList<>();

    @ManyToOne
    private User user;

    @ManyToOne
    private Route route;

    public Beta() {}

    public Beta(String description) {
        this.description = description;
    }

    public Beta(Route route, User user) {
        this.route = route;
        this.user = user;
    }

    public Beta(String description, boolean isPublic) {
        this.description = description;
        this.isPublic = isPublic;
    }

    public Beta(String description, Route route, User user, boolean isPublic) {
        this.description = description;
        this.route = route;
        this.user = user;
        this.isPublic = isPublic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsShared() {
        return isPublic;
    }

    public void setIsShared(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setShared(boolean shared) {
        isPublic = shared;
    }

    public List<User> getShares() {
        return shares;
    }

    public void setShares(List<User> shares) {
        this.shares = shares;
    }

    public void addshare(User user) {
        this.shares.add(user);
    }

    public void removeShare(User user) {
        this.shares.remove(user);
    }
}
