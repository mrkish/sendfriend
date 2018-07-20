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
    public String processRouteAddForm(Model model, Errors errors, @ModelAttribute @Valid Route route) {


        return "route/index";
    }

}
