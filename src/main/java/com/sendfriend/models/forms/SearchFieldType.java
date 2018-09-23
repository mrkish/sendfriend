package com.sendfriend.models.forms;

public enum SearchFieldType {

    USER ("User"),
    ROUTE ("Route"),
    CRAG ("Crag"),
    AREA ("Area"),
    ALL ("All");

    private final String name;

    SearchFieldType(String name) { this.name = name; }

    public String getName() { return name; }

}
