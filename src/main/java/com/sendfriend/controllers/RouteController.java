package com.sendfriend.controllers;

import com.sendfriend.models.Beta;
import com.sendfriend.models.Route;
import com.sendfriend.models.User;
import com.sendfriend.models.data.BetaDao;
import com.sendfriend.models.data.RouteDao;
import com.sendfriend.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
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

    @RequestMapping(value = "")
    public String routeIndex(Model model, HttpServletRequest request) {

        model.addAttribute("routes", routeDao.findAll());
        model.addAttribute("title", "Routes");
        if (request.getSession().getAttribute("user") != null) {
            model.addAttribute("user", request.getSession().getAttribute("user"));
        }

        return "route/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayRouteAddForm(Model model, HttpServletRequest request) {

        model.addAttribute("title", "Add Route!");
        model.addAttribute(new Route());
        if (request.getSession().getAttribute("user") != null) {
            model.addAttribute("user", request.getSession().getAttribute("user"));
        }

        return "route/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processRouteAddForm(Model model, @ModelAttribute @Valid Route route, Errors errors) {

        List<Route> routeNames = routeDao.findByName(route.getName());

        if (errors.hasErrors()) {
            model.addAttribute("route", route);
            model.addAttribute("title", "Add Route!");

            return "route/add";
        }

        routeDao.save(route);

        return "route/index";
    }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String displayEditRouteForm(Model model) {

        model.addAttribute("title", "Edit Route");
        model.addAttribute(new Route());

        return "route/edit";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String displayEditRouteForm(Model model, HttpServletRequest request, @ModelAttribute @Valid Route route, Errors errors) {



        return "redirect:/route/view/" + route.getId();
    }

    @RequestMapping(value = "view/{routeId}", method = RequestMethod.GET)
    public String displaySingleRoute(Model model, @PathVariable int routeId) {

        Route route = routeDao.findById(routeId);
        model.addAttribute("title", "Route: " + route.getName());
        model.addAttribute("route", route);
        List<Beta> betas = getPublicBetasList(route);
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

    private List<Beta> getPublicBetasList(Route route) {

        List<Beta> allBetas = route.getBetas();
        List<Beta> publicBetas = new ArrayList<>();

        for (Beta beta : allBetas) {
            if (beta.getIsPublic()) {
                publicBetas.add(beta);
            }
        }
        return publicBetas;
    }

}
