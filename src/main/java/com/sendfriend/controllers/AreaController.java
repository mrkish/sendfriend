package com.sendfriend.controllers;

import com.sendfriend.models.data.AreaDao;
import com.sendfriend.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "area")
public class AreaController {

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "")
    public String index(Model model, HttpSession session) {

        model.addAttribute("title", "Areas");
        model.addAttribute("areas", areaDao.findAll());
        if (session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        };

        return "area/index";
    }
}
