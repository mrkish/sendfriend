package com.sendfriend.controllers;

import com.sendfriend.models.Area;
import com.sendfriend.models.data.AreaDao;
import com.sendfriend.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "area")
public class AreaController extends AbstractController {

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "")
    public String index(Model model, HttpServletRequest request) {

        model.addAttribute("title", "Areas");
        model.addAttribute("areas", areaDao.findAll());
        if (request.getSession().getAttribute("user") != null) {
            model.addAttribute("user", request.getSession().getAttribute("user"));
        }

        return "area/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddAreaForm(Model model, HttpServletRequest request) {

        model.addAttribute("title", "Add Area");
        model.addAttribute(new Area());
        if (request.getSession().getAttribute("user") != null) {
            model.addAttribute("user", request.getSession().getAttribute("user"));
        }

        return "area/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddAreaForm(Model model, @ModelAttribute @Valid Area area, Errors errors) {

       if (errors.hasErrors()) {
           model.addAttribute("title", "Add Area");
           return "redirect:/area/add";
       }

       areaDao.save(area);

        return "redirect:/area/view/" + area.getId();
    }

    @RequestMapping(value = "view/{areaId}")
    public String viewArea(Model model, HttpServletRequest request, @PathVariable int areaId) {

        Area areaToView = areaDao.findById(areaId);

        if (areaToView == null) {
            model.addAttribute("errors", "Area not found.");
            return "redirect:/area/";
        }

        model.addAttribute("title", areaToView.getName());
        model.addAttribute("area", areaToView);
        model.addAttribute("crags", areaToView.getCrags());
        if (request.getSession().getAttribute("user") != null) {
            model.addAttribute("user", request.getSession().getAttribute("user"));
        }

        return "area/view";
    }

}
