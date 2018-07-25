package com.sendfriend.controllers;

import com.sendfriend.models.Route;
import com.sendfriend.models.data.RouteDao;
import com.sendfriend.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "route")
public class RouteController {

    @Autowired
    UserDao userDao;

    @Autowired
    RouteDao routeDao;

    @RequestMapping(value = "")
    public String routeIndex(Model model) {

        model.addAttribute("routes", routeDao.findAll());
        model.addAttribute("title", "All Routes");

        return "route/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayRouteAddForm(Model model) {

        model.addAttribute("title", "Add Route!");
        model.addAttribute(new Route());

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

}
