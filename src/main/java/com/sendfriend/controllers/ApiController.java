package com.sendfriend.controllers;

import com.sendfriend.data.AreaDao;
import com.sendfriend.data.CragDao;
import com.sendfriend.data.RouteDao;
import com.sendfriend.models.Area;
import com.sendfriend.models.Crag;
import com.sendfriend.models.Route;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "api")
public class ApiController {

    AreaDao areaDao;
    CragDao cragDao;
    RouteDao routeDao;

    public ApiController(AreaDao areaDao,
                         CragDao cragDao,
                         RouteDao routeDao) {
        this.areaDao = areaDao;
        this.cragDao = cragDao;
        this.routeDao = routeDao;
    }

    @GetMapping(value = "crags")
    public HashMap<Integer, String> getCragsJson(@RequestParam(value = "areaId") int areaId) {

        HashMap<Integer, String> response = new HashMap<Integer, String>();
        List<Crag> crags = cragDao.findByAreaId(areaId);
        for (Crag aCrag : crags) {
            response.put(aCrag.getId(), aCrag.getName());
        }
        return response;
    }

    @GetMapping(value = "routes")
    public HashMap<Integer, String> getRoutesJson(@RequestParam(value = "cragId") int cragId) {

        HashMap<Integer, String> response = new HashMap<Integer, String>();
        List<Route> routes = routeDao.findByCragId(cragId);
        for (Route aRoute : routes) {
            response.put(aRoute.getId(), aRoute.getName());
        }
        return response;
    }

    @GetMapping(value = "areas")
    public HashMap<Integer, String> getAreasJson() {

        HashMap<Integer, String> response = new HashMap<Integer, String>();
        List<Area> areas = (List<Area>) areaDao.findAll();
        for (Area anArea : areas) {
            response.put(anArea.getId(), anArea.getName());
        }
        return response;
    }

}
