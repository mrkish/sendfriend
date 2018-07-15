package com.sendfriend.models;

import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class Crag {

    @GeneratedValue
    private int id;

    @NotNull
    private String name;

//    @ManyToOne
//    Area area;

    @OneToMany
    private List<Route> routes = new ArrayList<>();

    public Crag() {};

    public Crag(String name) {
        this.name = name;
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

//    public Area getArea() {
//        return area;
//    }
//
//    public void setArea(Area area) {
//        this.area = area;
//    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}
