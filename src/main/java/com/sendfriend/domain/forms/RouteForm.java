<<<<<<< HEAD:src/main/java/com/sendfriend/domain/forms/RouteForm.java
package com.sendfriend.domain.forms;

import javax.validation.constraints.NotNull;

public class RouteForm {

    @NotNull(message = "You must specify the name of the route.")
    private String name;

    private String description;

    private int rating;

    private String grade;

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

=======
package com.sendfriend.models.forms;

import javax.validation.constraints.NotNull;

public class RouteForm {

    @NotNull(message = "You must specify the name of the route.")
    private String name;

    private String description;

    private int rating;

    private String grade;

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

>>>>>>> develop:src/main/java/com/sendfriend/models/forms/RouteForm.java
}