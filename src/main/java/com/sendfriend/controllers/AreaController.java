package com.sendfriend.controllers;

import com.sendfriend.data.AreaDao;
import com.sendfriend.data.UserDao;
import com.sendfriend.models.Area;
import com.sendfriend.models.forms.AddForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "area")
public class AreaController extends AbstractController {

    private AreaDao areaDao;
    private UserDao userDao;

    public AreaController(AreaDao areaDao,
                          UserDao userDao) {
        this.areaDao = areaDao;
        this.userDao = userDao;
    }

    @GetMapping(value = "")
    public String index(Model model, HttpServletRequest request) {

        model.addAttribute("title", "Areas");
        model.addAttribute("areas", areaDao.findAll());
        if (request.getSession().getAttribute("user") != null) {
            model.addAttribute("user", request.getSession().getAttribute("user"));
        }

        return "area/index";
    }

    @GetMapping(value = "add")
    public String displayAddAreaForm(Model model, HttpServletRequest request) {

        model.addAttribute("title", "Add Area");
        model.addAttribute(new AddForm());
        if (request.getSession().getAttribute("user") != null) {
            model.addAttribute("user", request.getSession().getAttribute("user"));
        }

        return "area/add";
    }

    @PostMapping(value = "add")
    public String processAddAreaForm(Model model, @ModelAttribute @Valid AddForm form, Errors errors) {

       if (errors.hasErrors()) {
           model.addAttribute("title", "Add Area");
           return "redirect:/area/add";
       }

       Area area = new Area(form.getName(), form.getDescription());
       areaDao.save(area);

        return "redirect:/area/view/" + area.getId();
    }

    @GetMapping(value = "view/{areaId}")
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
