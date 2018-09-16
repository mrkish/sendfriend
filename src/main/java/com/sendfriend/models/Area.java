package com.sendfriend.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Area extends AbstractEntity {

    @NotNull
    private String name;

    private String description;

    private String location;

    @OneToMany
    @JoinColumn(name = "area_id")
    private List<Crag> crags = new ArrayList<>();

    public Area() {}

    public Area(String name) {
        this.name = name;
    }

    public Area(@NotNull String name, String description) {
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Crag> getCrags() {
        return crags;
    }

    public void addCrag(Crag crag) {
        this.crags.add(crag);
    }

    public void setCrags(List<Crag> crags) {
        this.crags = crags;
    }
}