package com.sendfriend.controllers;

import com.sendfriend.models.data.AreaDao;
import com.sendfriend.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "area")
public class AreaController {

    @Autowired
    AreaDao areaDao;

    @Autowired
    UserDao userDao;

    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("title", "Areas");
        model.addAttribute("areas", areaDao.findAll());

        return "area/index";
    }
}
