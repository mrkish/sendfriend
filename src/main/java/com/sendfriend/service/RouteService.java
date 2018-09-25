package com.sendfriend.service;

import com.sendfriend.models.Route;
import com.sendfriend.models.data.AreaDao;
import com.sendfriend.models.data.CragDao;
import com.sendfriend.models.data.RouteDao;
import com.sendfriend.models.forms.RouteForm;
import org.springframework.beans.factory.annotation.Autowired;

public class RouteService {

    @Autowired
    RouteDao routeDao;

    @Autowired
    CragDao cragDao;

    @Autowired
    AreaDao areaDao;

    public void addAndAssociateRoute(RouteForm routeForm) {

    }
}
