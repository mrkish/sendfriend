package com.sendfriend.models;

import com.sendfriend.models.Route;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Crag {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    private String description;

    @OneToMany
    @JoinColumn(name = "crag_id")
    private List<Route> routes = new ArrayList<>();

    @ManyToOne
    private Area area;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}