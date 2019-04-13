<<<<<<< HEAD
package com.sendfriend.controllers;

import com.sendfriend.domain.Area;
import com.sendfriend.domain.Crag;
import com.sendfriend.domain.Route;
import com.sendfriend.repository.AreaDao;
import com.sendfriend.repository.CragDao;
import com.sendfriend.repository.RouteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "api")
public class ApiController {

    @Autowired
    AreaDao areaDao;

    @Autowired
    CragDao cragDao;

    @Autowired
    RouteDao routeDao;

    @RequestMapping(value = "crags", method = RequestMethod.GET)
    public HashMap<Integer, String> getCragsJson(@RequestParam(value = "areaId") int areaId) {

        HashMap<Integer, String> response = new HashMap<>();
        List<Crag> crags = cragDao.findByAreaId(areaId);
        for (Crag aCrag : crags) {
            response.put(aCrag.getId(), aCrag.getName());
        }
        return response;
    }

    @RequestMapping(value = "routes", method = RequestMethod.GET)
    public HashMap<Integer, String> getRoutesJson(@RequestParam(value = "cragId") int cragId) {

        HashMap<Integer, String> response = new HashMap<Integer, String>();
        List<Route> routes = routeDao.findByCragId(cragId);
        for (Route aRoute : routes) {
            response.put(aRoute.getId(), aRoute.getName());
        }
        return response;
    }

    @RequestMapping(value = "areas")
    public HashMap<Integer, String> getAreasJson() {

        HashMap<Integer, String> response = new HashMap<Integer, String>();
        List<Area> areas = (List<Area>) areaDao.findAll();
        for (Area anArea : areas) {
            response.put(anArea.getId(), anArea.getName());
        }
        return response;
    }

}
=======
package com.sendfriend.controllers;

import com.sendfriend.models.Area;
import com.sendfriend.models.Crag;
import com.sendfriend.models.Route;
import com.sendfriend.models.data.AreaDao;
import com.sendfriend.models.data.CragDao;
import com.sendfriend.models.data.RouteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "api")
public class ApiController {

    @Autowired
    AreaDao areaDao;

    @Autowired
    CragDao cragDao;

    @Autowired
    RouteDao routeDao;

    @RequestMapping(value = "crags", method = RequestMethod.GET)
    public HashMap<Integer, String> getCragsJson(@RequestParam(value = "areaId") int areaId) {

        HashMap<Integer, String> response = new HashMap<Integer, String>();
        List<Crag> crags = cragDao.findByAreaId(areaId);
        for (Crag aCrag : crags) {
            response.put(aCrag.getId(), aCrag.getName());
        }
        return response;
    }

    @RequestMapping(value = "routes", method = RequestMethod.GET)
    public HashMap<Integer, String> getRoutesJson(@RequestParam(value = "cragId") int cragId) {

        HashMap<Integer, String> response = new HashMap<Integer, String>();
        List<Route> routes = routeDao.findByCragId(cragId);
        for (Route aRoute : routes) {
            response.put(aRoute.getId(), aRoute.getName());
        }
        return response;
    }

    @RequestMapping(value = "areas")
    public HashMap<Integer, String> getAreasJson() {

        HashMap<Integer, String> response = new HashMap<Integer, String>();
        List<Area> areas = (List<Area>) areaDao.findAll();
        for (Area anArea : areas) {
            response.put(anArea.getId(), anArea.getName());
        }
        return response;
    }

}
>>>>>>> develop
