package com.sendfriend.controllers;

import com.sendfriend.models.*;
import com.sendfriend.models.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "beta")
public class BetaController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RouteDao routeDao;

    @Autowired
    private CragDao cragDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private BetaDao betaDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String displayAllBeta(Model model, HttpSession session) {

        model.addAttribute("title", "Betas");
        model.addAttribute("betas", betaDao.findAll());
        if (session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }

        return "beta/index";
    }

    @RequestMapping(value = "select-area")
    public String displaySelectAreaForm(Model model, HttpSession session) {

        if (session.getAttribute("user") == null) {
            model.addAttribute("errors", "You can only add beta if you're a registered user.");

            return "redirect:/beta";
        } else if (session.getAttribute("user") != null) {
            User username = (User) session.getAttribute("user");
            User user = userDao.findByUsername(username.getUsername());
            model.addAttribute("user", user);
        }

        List<Area> areasOptions = (List<Area>) areaDao.findAll();
        areasOptions.add(new Area("none"));

        model.addAttribute("areas", areasOptions);
        model.addAttribute("title", "Select Area");
        if (session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }

        return "beta/select-area";
    }

    @RequestMapping(value = "add")
    public String displayAddBetaForm(Model model, HttpSession session, @RequestParam(required = false) String newAreaName, @RequestParam int areaId) {

        if (session.getAttribute("user") == null) {
            model.addAttribute("errors", "You can only add beta if you're a registered user.");
            return "beta/index";
        } else if (session.getAttribute("user") != null) {
            User username = (User) session.getAttribute("user");
            User user = userDao.findByUsername(username.getUsername());
            model.addAttribute("user", user);
        }

        Area area = areaDao.findById(areaId);
        if (!newAreaName.isEmpty() && area != null) {
            model.addAttribute("title", "Select Area");
            model.addAttribute("errors", "Please only select an area or enter a name for a new area.");

            return "redirect:/beta/select-area";
        }

        if (newAreaName.isEmpty() && area != null) {
            List<Crag> crags = area.getCrags();
            model.addAttribute("crags", crags);
            model.addAttribute("area", area);
        } else if (!newAreaName.isEmpty()) {
            Area newArea = new Area(newAreaName);
            areaDao.save(newArea);
            model.addAttribute("area", newArea);
        }

        model.addAttribute("title", "Add Beta");
        model.addAttribute(new Beta());

        return "beta/add";
    }

    @RequestMapping(value = "process-add", method = RequestMethod.POST)
    public String processAddBetaForm(Model model, HttpSession session, @Valid Beta newBeta, Errors errors,
                                     @RequestParam(required = false) Integer cragId, @RequestParam String routeName,
                                     @RequestParam(required = false) String cragName, @RequestParam int userId,
                                     @RequestParam int areaId, boolean isShared) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Beta");
            return "beta/add";
        }

        User user = userDao.findById(userId);
        Area area = areaDao.findById(areaId);

        if (!cragName.isEmpty()) {
            Crag newCrag = new Crag(cragName);
            newCrag.setArea(area);
            cragDao.save(newCrag);

            area.addCrag(newCrag);
            areaDao.save(area);

            Route newRoute = new Route(routeName);
            newRoute.setCrag(newCrag);
            routeDao.save(newRoute);

            newBeta.setRoute(newRoute);
            newBeta.setIsShared(isShared);
            newBeta.setUser(user);
            betaDao.save(newBeta);

            newRoute.addBeta(newBeta);
            routeDao.save(newRoute);

            model.addAttribute("beta", newBeta);
            model.addAttribute("title", "Viewing Beta for: " + newBeta.getRoute().getName());
            model.addAttribute("routeId", newRoute.getId());
            if (session.getAttribute("user") != null) {
                model.addAttribute("user", session.getAttribute("user"));
            }

            return "redirect:/beta/view/" + newBeta.getId();
        }

        if (cragId == null && !cragName.isEmpty()) {
            return "redirect:/beta/add";
        }

        int cragIdInt;
        cragIdInt = cragId.intValue();
        Crag crag = cragDao.findById(cragIdInt);

        List<Route> foundRoutes = routeDao.findByName(routeName);
        List<Route> cragRoutes = crag.getRoutes();
        List<String> cragRouteNames = new ArrayList<>();
        for (Route route : cragRoutes) {
            cragRouteNames.add(route.getName());
        }

        if (cragRouteNames.contains(routeName)) {
            if (foundRoutes.size() > 1) {
                for (Route route : foundRoutes) {
                    if (route.getCrag().equals(crag.getName())) {
                        newBeta.setRoute(route);
                        newBeta.setIsShared(isShared);
                        newBeta.setUser(user);
                        betaDao.save(newBeta);

                        model.addAttribute("beta", newBeta);
                        model.addAttribute("title", "Viewing Beta for: " + newBeta.getRoute().getName());
                        model.addAttribute("routeId", route.getId());
                        if (session.getAttribute("user") != null) {
                            model.addAttribute("user", session.getAttribute("user"));
                        }

                        return "redirect:/beta/view/" + newBeta.getId();
                    }
                }
            }
        }

        Route newRoute = new Route(routeName);
        newRoute.setCrag(crag);
        routeDao.save(newRoute);

        newBeta.setRoute(newRoute);
        newBeta.setIsShared(isShared);
        newBeta.setUser(user);
        betaDao.save(newBeta);

        model.addAttribute("beta", newBeta);
        model.addAttribute("title", "Viewing Beta for: " + newBeta.getRoute().getName());
        model.addAttribute("routeId", newRoute.getId());
        if (session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }

        return "redirect:/beta/view/" + newBeta.getId();
    }

    @RequestMapping(value = "view/{betaId}")
    public String viewSingleBeta(Model model, HttpSession session, @PathVariable int betaId) {

        Beta beta = betaDao.findById(betaId);
        Route route = routeDao.findById(beta.getRoute().getId());

        if (route == null || beta == null) {
            model.addAttribute("errors", "Route or Beta not found!");
            return "beta/index";
        }

        model.addAttribute("title", "View Beta for " + route.getName());
        model.addAttribute("route", route);
        model.addAttribute("beta", beta);
        if (session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }

        return "beta/view";
    }
}
