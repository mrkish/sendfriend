package com.sendfriend.controllers;

import com.sendfriend.domain.Beta;
import com.sendfriend.domain.Crag;
import com.sendfriend.domain.Route;
import com.sendfriend.repository.BetaDao;
import com.sendfriend.repository.CragDao;
import com.sendfriend.repository.RouteDao;
import com.sendfriend.repository.UserDao;
import com.sendfriend.domain.forms.RouteForm;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserDao userDao;

    @Autowired
    private BetaDao betaDao;

    @Autowired
    private RouteDao routeDao;

    @Autowired
    CragDao cragDao;

    @RequestMapping(value = "")
    public String routeIndex(Model model, HttpServletRequest request) {

        model.addAttribute("routes", routeDao.findAll());
        model.addAttribute("title", "Routes");
//        if (request.getSession().getAttribute("user") != null) {
//            model.addAttribute("user", request.getSession().getAttribute("user"));
//        }

        return "route/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayRouteAddForm(Model model, HttpServletRequest request, @RequestParam(value = "cragId") int cragId) {

        model.addAttribute("title", "Add Route!");
        model.addAttribute("cragId", cragId);
        model.addAttribute(new RouteForm());
        if (request.getSession().getAttribute("user") != null) {
            model.addAttribute("user", request.getSession().getAttribute("user"));
        }

        return "route/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processRouteAddForm(Model model, @ModelAttribute @Valid RouteForm routeForm, Errors errors, @RequestParam(value = "cragId") int cragId) {

        List<Route> routeNames = routeDao.findByName(routeForm.getName());
        Crag crag = cragDao.findById(cragId);

        if (errors.hasErrors()) {
            model.addAttribute("route", routeForm);
            model.addAttribute("title", "Add Route!");
            return "route/add";
        }

        if (routeNames.size() > 0) {
            model.addAttribute("error", "Crag already has route!");
            return "route/add";
        }

        Route newRoute = new Route(routeForm.getName(), routeForm.getGrade(), routeForm.getDescription());
        newRoute.setCrag(crag);
        routeDao.save(newRoute);

        return "redirect:route/view/" + newRoute.getId();
    }

    @RequestMapping(value = "edit/{routeId}", method = RequestMethod.GET)
    public String displayEditRouteForm(Model model, @PathVariable int routeId) {

        Route routeToEdit = routeDao.findById(routeId);
        model.addAttribute("title", "Editing: " + routeToEdit.getName());
        model.addAttribute("routeToEdit", routeToEdit);

        return "route/edit";
    }

    @RequestMapping(value = "/edit/{routeId}", method = RequestMethod.POST)
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

    @RequestMapping(value = "view/{routeId}", method = RequestMethod.GET)
    public String displaySingleRoute(Model model, @PathVariable int routeId) {

        Route route = routeDao.findById(routeId);
        model.addAttribute("title", "Route: " + route.getName());
        model.addAttribute("route", route);
        List<Beta> betas = betaDao.findPublicBetasByRouteId(routeId);
        model.addAttribute("betas", betas);

        return "route/view";
    }

    @RequestMapping(value = "view/{routeId}?beta={betaId}")
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
