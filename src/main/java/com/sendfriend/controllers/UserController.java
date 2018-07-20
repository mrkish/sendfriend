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
import java.util.List;

@Controller
@RequestMapping(value = "")
public class UserController {

    @Autowired
    UserDao userDao;

    @Autowired
    RouteDao routeDao;
//
//    @Autowired
//    AreaDao areaDao;
//
//    @Autowired
//    CragDao cragDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("title", "Sendfriend! | Index");
        model.addAttribute("users",userDao.findAll());

        return "user/index";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String displayRegisterForm(Model model) {

        model.addAttribute("title", "Sendfriend | Register New User");
        model.addAttribute(new User());

        return "user/register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String processRegisterForm(@ModelAttribute @Valid User user, Errors errors, Model model, String verify) {

        List<User> foundName = userDao.findByUsername(user.getUsername());

        if (errors.hasErrors() || !verify.equals(user.getPassword()) || (foundName.equals(user.getUsername()) && user.getUsername() == null)) {
            model.addAttribute("title", "Sendfriend | Register New User");

            return "user/register";
        }

        userDao.save(user);

        return "redirect:index/";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String displayLogin(Model model) {

        model.addAttribute("title", "Sendfriend | Login!");

        return "user/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processLogin(Model model) {

        return "redirect:index/";
    }
 }
