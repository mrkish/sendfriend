package com.sendfriend.service;

import com.sendfriend.models.Area;
import com.sendfriend.models.Route;
import com.sendfriend.models.data.AreaDao;
import com.sendfriend.models.data.CragDao;
import com.sendfriend.models.data.RouteDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RouteService {

    @Autowired
    RouteDao routeDao;

    @Autowired
    CragDao cragDao;

    @Autowired
    AreaDao areaDao;

    public void addAndAssociateRoute(Route route, String areaName) {

        List<Area> area = areaDao.findByName(areaName);


    }
}
