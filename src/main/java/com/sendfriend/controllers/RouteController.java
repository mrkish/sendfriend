package com.sendfriend.controllers;

import com.sendfriend.data.BetaDao;
import com.sendfriend.data.CragDao;
import com.sendfriend.data.RouteDao;
import com.sendfriend.data.UserDao;
import com.sendfriend.models.Beta;
import com.sendfriend.models.Crag;
import com.sendfriend.models.Route;
import com.sendfriend.models.forms.RouteForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "route")
public class RouteController extends AbstractController {

    private UserDao userDao;
    private BetaDao betaDao;
    private RouteDao routeDao;
    private CragDao cragDao;

    public RouteController(UserDao userDao,
                           BetaDao betaDao,
                           RouteDao routeDao,
                           CragDao cragDao) {
        this.userDao = userDao;
        this.betaDao = betaDao;
        this.routeDao = routeDao;
        this.cragDao = cragDao;
    }

    @GetMapping(value = "")
    public String routeIndex(Model model, HttpServletRequest request) {

        model.addAttribute("routes", routeDao.findAll());
        model.addAttribute("title", "Routes");

        return "route/index";
    }

    @GetMapping(value = "add")
    public String displayRouteAddForm(Model model, HttpServletRequest request, @RequestParam(value = "cragId") int cragId) {

        model.addAttribute("title", "Add Route!");
        model.addAttribute("cragId", cragId);
        model.addAttribute(new RouteForm());
        if (request.getSession().getAttribute("user") != null) {
            model.addAttribute("user", request.getSession().getAttribute("user"));
        }

        return "route/add";
    }

    @PostMapping(value = "add")
    public String processRouteAddForm(Model model, @ModelAttribute @Valid RouteForm routeForm, Errors errors, @RequestParam(value = "cragId") int cragId) {

        List<Route> routeNames = routeDao.findByName(routeForm.getName());
        Crag crag = cragDao.findById(cragId);

        if (errors.hasErrors()) {
            model.addAttribute("route", routeForm);
            model.addAttribute("title", "Add Route!");
            return "route/add";
        }

        if (routeNames.isEmpty()) {
            model.addAttribute("error", "Crag already has route!");
            return "route/add";
        }

        Route newRoute = new Route(routeForm.getName(), routeForm.getGrade(), routeForm.getDescription());
        newRoute.setCrag(crag);
        routeDao.save(newRoute);

        return "redirect:route/view/" + newRoute.getId();
    }

    @GetMapping(value = "edit/{routeId}")
    public String displayEditRouteForm(Model model, @PathVariable int routeId) {

        Route routeToEdit = routeDao.findById(routeId);
        model.addAttribute("title", "Editing: " + routeToEdit.getName());
        model.addAttribute("routeToEdit", routeToEdit);

        return "route/edit";
    }

    @PostMapping(value = "/edit/{routeId}")
    public String processEditRouteForm(@ModelAttribute @Valid Route routeToEdit, @PathVariable int routeId, Errors errors) {

        if (errors.hasErrors()){
            return "redirect:route/edit/" + routeToEdit.getId();
        }

        Route route = routeDao.findById(routeId);
        route.setName(routeToEdit.getName());
        route.setGrade(routeToEdit.getGrade());
        route.setDescription(routeToEdit.getDescription());
        routeDao.save(route);

        return "redirect:/view/" + route.getId();
    }

    @GetMapping(value = "view/{routeId}")
    public String displaySingleRoute(Model model, @PathVariable int routeId) {

        Route route = routeDao.findById(routeId);
        model.addAttribute("title", "Route: " + route.getName());
        model.addAttribute("route", route);
        List<Beta> betas = betaDao.findPublicBetasByRouteId(routeId);
        model.addAttribute("betas", betas);

        return "route/view";
    }

    @GetMapping(value = "view/{routeId}?beta={betaId}")
    public String viewRouteSingleBeta(Model model, HttpServletRequest request, @PathVariable int betaId, @PathVariable int routeId) {

        Beta beta = betaDao.findById(betaId);
        Route route = routeDao.findById(routeId);
        model.addAttribute("beta", beta);
        model.addAttribute("route", route);
        model.addAttribute("title", "Beta: " + beta.getRoute());
        if (request.getSession().getAttribute("user") != null) {
            model.addAttribute("user", request.getSession().getAttribute("user"));
        }

        return "beta/view" + beta.getId();
    }

}
