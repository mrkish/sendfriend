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
import java.util.Map;

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
    public Map<Integer, String> getCragsJson(@RequestParam(value = "areaId") int areaId) {

        Map<Integer, String> response = new HashMap<>();
        List<Crag> crags = cragDao.findByAreaId(areaId);
        for (Crag aCrag : crags) {
            response.put(aCrag.getId(), aCrag.getName());
        }
        return response;
    }

    @GetMapping(value = "routes")
    public Map<Integer, String> getRoutesJson(@RequestParam(value = "cragId") int cragId) {

        Map<Integer, String> response = new HashMap<>();
        List<Route> routes = routeDao.findByCragId(cragId);
        for (Route aRoute : routes) {
            response.put(aRoute.getId(), aRoute.getName());
        }
        return response;
    }

    @GetMapping(value = "areas")
    public Map<Integer, String> getAreasJson() {

        Map<Integer, String> response = new HashMap<>();
        List<Area> areas = (List<Area>) areaDao.findAll();
        for (Area anArea : areas) {
            response.put(anArea.getId(), anArea.getName());
        }
        return response;
    }

}
