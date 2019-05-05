package com.sendfriend.service.impl;

import com.sendfriend.data.AreaDao;
import com.sendfriend.data.CragDao;
import com.sendfriend.data.RouteDao;
import com.sendfriend.models.Area;
import com.sendfriend.models.Route;

import java.util.List;

public class RouteServiceImpl {

    RouteDao routeDao;
    CragDao cragDao;
    AreaDao areaDao;

    public RouteServiceImpl(RouteDao routeDao,
                            CragDao cragDao,
                            AreaDao areaDao) {
        this.routeDao = routeDao;
        this.cragDao = cragDao;
        this.areaDao = areaDao;
    }

    public void addAndAssociateRoute(Route route, String areaName) {

        List<Area> area = areaDao.findByName(areaName);
    }
}