<<<<<<< HEAD:src/main/java/com/sendfriend/domain/Beta.java
package com.sendfriend.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Beta extends AbstractEntity {

    @NotNull
    private String description;

    @NotNull
    private boolean isPublic = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "shares", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "beta_id"))
    private List<User> shares = new ArrayList<>();

    @ManyToOne
    private User user;

    @ManyToOne
    private Route route;

    private String name;

    public Beta() {
        this.isPublic = false;
    }

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

    public Beta(@NotNull String description, @NotNull boolean isPublic, User user, Route route, String name) {
        this.description = description;
        this.isPublic = isPublic;
        this.user = user;
        this.route = route;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
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

    public void setPublic(boolean shared) {
        isPublic = shared;
    }

    public boolean isShared() {
        return this.shares.size() >= 1;
    }

    public List<User> getShares() {
        return shares;
    }

    public void setShares(List<User> shares) {
        this.shares = shares;
    }

    public void addShare(User user) {
        this.shares.add(user);
    }

    public void removeShare(User user) {
        this.shares.remove(user);
    }
}
=======
package com.sendfriend.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Beta extends AbstractEntity {

    @NotNull
    private String description;

    @NotNull
    private boolean isPublic = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "shares", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "beta_id"))
    private List<User> shares = new ArrayList<>();

    @ManyToOne
    private User user;

    @ManyToOne
    private Route route;

    private String name;

    public Beta() {
        this.isPublic = false;
    }

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

    public Beta(@NotNull String description, @NotNull boolean isPublic, User user, Route route, String name) {
        this.description = description;
        this.isPublic = isPublic;
        this.user = user;
        this.route = route;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
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

    public void setPublic(boolean shared) {
        isPublic = shared;
    }

    public boolean isShared() {
        return this.shares.size() >= 1;
    }

    public List<User> getShares() {
        return shares;
    }

    public void setShares(List<User> shares) {
        this.shares = shares;
    }

    public void addShare(User user) {
        this.shares.add(user);
    }

    public void removeShare(User user) {
        this.shares.remove(user);
    }
}
>>>>>>> develop:src/main/java/com/sendfriend/models/Beta.java
