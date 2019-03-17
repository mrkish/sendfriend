package com.sendfriend.domain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User extends AbstractEntity {

    @NotNull
    @Size(min = 3, max = 15, message = "Username must be between 3-15 characters.")
    @Pattern(regexp = "[a-zA-Z][a-zA-Z0-9_-]{3,15}", message = "Invalid username")
    @Column(unique = true)
    private String username;

    @NotNull
    private String pwHash;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @NotNull
    @Column(unique = true)
    private String email;


    @ManyToMany
    @JoinTable(name = "shares", joinColumns = @JoinColumn(name = "beta_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Beta> receivedShares = new ArrayList<>();

    @ManyToMany
    @JoinTable(name =  "friends", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private Set<User> friends = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "friends", joinColumns = @JoinColumn(name = "friend_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> friendsOf = new HashSet<>();

    @OneToMany
    @JoinTable(name = "user_id")
    private List<Beta> betas = new ArrayList<>();

    public User() { }

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password) {
        this.username = username;
        this.pwHash= hashPassword(password);
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.pwHash= hashPassword(password);
        this.email = email;
    }

    private static String hashPassword(String password) {
        return encoder.encode(password);
    }

    public void setPwHash(String password) {
        this.pwHash = hashPassword(password);
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

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwHash() {
        return pwHash;
    }

    public static BCryptPasswordEncoder getEncoder() {
        return encoder;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Beta> getReceivedShares() {
        return receivedShares;
    }

    public void setReceivedShares(List<Beta> receivedShares) {
        this.receivedShares = receivedShares;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public Set<User> getFriendsOf() {
        return friendsOf;
    }

    public void setFriendsOf(Set<User> friendsOf) {
        this.friendsOf = friendsOf;
    }

    public List<Beta> getBetas() {
        return betas;
    }

    public void setBetas(List<Beta> betas) {
        this.betas = betas;
    }
}
