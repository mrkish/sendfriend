package com.sendfriend.models;

import com.sendfriend.models.Route;
import com.sendfriend.models.User;

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
    private boolean shared;

    @ManyToOne
    private User user;

    @ManyToOne
    private Route route;

    public Beta() {}

    public Beta(String description) {
        this.shared = shared;
        this.description = description;
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

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
