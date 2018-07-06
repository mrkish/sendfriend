package com.sendfriend.controllers;

import com.sendfriend.models.User;
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
@RequestMapping(value = "")
public class UserController {

    @Autowired
    UserDao userDao;

    @Autowired
    RouteDao routeDao;

    @RequestMapping(value = "index")
    public String index(Model model) {

        return "user/index";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String displayRegisterForm(Model model) {

        return "user/register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String processRegisterForm(Model model, @ModelAttribute @Valid User user, Errors errors) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Register New User");

            return "user/register";
        }

        userDao.save(user);

        return "redirect:index/";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String displayLogin(Model model) {

        model.addAttribute("title", "Login!");

        return "user/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processLogin(Model model) {

        return "redirect:index/";
    }
 }
