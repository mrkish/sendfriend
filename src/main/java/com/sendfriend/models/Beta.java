package com.sendfriend.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Beta {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String description;

    @NotNull
    private boolean isShared = false;

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

    public Beta(String description, boolean isShared) {
        this.description = description;
        this.isShared = isShared;
    }

    public Beta(String description, Route route, User user, boolean isShared) {
        this.description = description;
        this.route = route;
        this.user = user;
        this.isShared = isShared;
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
        return isShared;
    }

    public void setIsShared(boolean isShared) {
        this.isShared = isShared;
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
}
