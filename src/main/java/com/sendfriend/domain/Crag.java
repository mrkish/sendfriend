package com.sendfriend.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Crag extends AbstractEntity {

    @NotNull
    private String name;

    private String description;

    @OneToMany
    @JoinColumn(name = "crag_id")
    private List<Route> routes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    public Crag() {}

    public Crag(@NotNull String name) {
        this.name = name;
    }

    public Crag(@NotNull String name, String description) {
        this.name = name;
        this.description = description;
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

    public void addRoute(Route route) {
        this.routes.add(route);
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