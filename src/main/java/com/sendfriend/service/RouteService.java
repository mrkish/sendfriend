package com.sendfriend.service;

import com.sendfriend.models.Route;

public interface RouteService {

    void addAndAssociateRoute(Route route, String areaName);

}
