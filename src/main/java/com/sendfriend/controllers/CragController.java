package com.sendfriend.controllers;

import com.sendfriend.models.data.CragDao;
import com.sendfriend.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "crag")
public class CragController {

    @Autowired
    private CragDao cragDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "")
    public String index(Model model, HttpSession session) {

        model.addAttribute("title", "Crags");
        model.addAttribute("crags", cragDao.findAll());
        if (session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }

        return "crag/index";
    }
}
