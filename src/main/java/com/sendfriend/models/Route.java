package com.sendfriend.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

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

    @Min(0)
    @Max(5)
    private double rating;

    @OneToMany
    @JoinTable(name = "route_id")
    private List<Beta> betas = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "crag_id")
    private Crag crag;

    public Route() {}

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

    public Crag getCrag() {
        return crag;
    }

    public void setCrag(Crag crag) {
        this.crag = crag;
    }

    public List<Beta> getBetas() {
        return betas;
    }

    public void setBetas(List<Beta> betas) {
        this.betas = betas;
    }

    public void addBeta(Beta beta) {
        this.betas.add(beta);
    }

    public void removeBeta(Beta beta) {
        this.betas.remove(beta);
    }

}
