package com.sendfriend.models;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Route {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 1, max = 25)
    private String name;

    private String grade;

    private String description;

    private double rating;

    @ManyToOne
    private User user;

    public Route() {};

    public Route(String name) {
        this.name = name;
    }

    public Route(String name, String grade, String description) {
        this.name = name;
        this.grade = grade;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
