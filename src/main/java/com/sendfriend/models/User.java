package com.sendfriend.models;

import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 4, max = 15)
    private String password;

    @NotNull
    @Size(min =4, max = 15, message = "Username must be between 4-15 characters.")
    @Column(unique = true)
    private String username;

    @NotNull
    @Column(unique = true)
    private String email;

    @ManyToMany
    @JoinTable(name = "shares", joinColumns = @JoinColumn(name = "beta_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Beta> receivedShares = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name =  "friends", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private Set<User> friends = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "friends", joinColumns = @JoinColumn(name = "friend_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> friendsOf = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_id")
    private List<Beta> betas = new ArrayList<>();

    public User() { }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Beta> getBetas() {
        return betas;
    }

    public void setBetas(List<Beta> betas) {
        this.betas = betas;
    }

    public void addFriend(User user) {
        this.friends.add(user);
        this.friendsOf.add(this);
    }

    public void removeFriend(User user) {
        this.friends.remove(friends);
        this.friendsOf.remove(this);
    }

    public boolean isFriendOf(User user) {
        return friendsOf.contains(user);
    }

    public boolean hasFriend(User user) {
        return friends.contains(user);
    }

    public void setFriends(Set<User> friends) {
        this.friends.clear();
        this.friends.addAll(friends);
    }

    public Set<User> getFriends() {
        return this.friends;
    }

    public Set<User> getFriendsInitalized() {
        Hibernate.initialize(this.friends);
        Set<User> friends = this.friends;

        return this.friends;
    }
}
