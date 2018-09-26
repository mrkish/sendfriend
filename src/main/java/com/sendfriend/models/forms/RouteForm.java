package com.sendfriend.models.forms;

import javax.validation.constraints.NotNull;

public class RouteForm {

    @NotNull(message = "You must specify the name of the route.")
    private String name;

    private String description;

    private int rating;

    private String grade;

    @NotNull(message = "You must specify which climbing area the route is in.")
    private String area;

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}