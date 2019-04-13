<<<<<<< HEAD
package com.sendfriend.controllers;

import com.sendfriend.domain.Area;
import com.sendfriend.repository.AreaDao;
import com.sendfriend.domain.forms.AddForm;
import com.sendfriend.util.AppConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping(value = AppConstants.AREA)
public class AreaController extends AbstractController {

    private AreaDao areaDao;

    public AreaController(AreaDao areaDao) {
        this.areaDao = areaDao;
    }

    @RequestMapping(value = "")
    public String index(Model model, HttpServletRequest request) {

        model.addAttribute(AppConstants.TITLE, AppConstants.AREAS);
        model.addAttribute("areas", areaDao.findAll());
        if (request.getSession().getAttribute(AppConstants.USER) != null) {
            model.addAttribute(AppConstants.USER, request.getSession().getAttribute(AppConstants.USER));
        }

        return "area/index";
    }

    @GetMapping(value = "add")
    public String displayAddAreaForm(Model model, HttpServletRequest request) {

        model.addAttribute(AppConstants.TITLE, "Add Area");
        model.addAttribute(new AddForm());
        if (request.getSession().getAttribute(AppConstants.USER) != null) {
            model.addAttribute(AppConstants.USER, request.getSession().getAttribute(AppConstants.USER));
        }

        return "area/add";
    }

    @PostMapping(value = "add")
    public String processAddAreaForm(Model model, @ModelAttribute @Valid AddForm form, Errors errors) {

       if (errors.hasErrors()) {
           model.addAttribute(AppConstants.TITLE, "Add Area");
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

        model.addAttribute(AppConstants.TITLE, areaToView.getName());
        model.addAttribute(AppConstants.AREA, areaToView);
        model.addAttribute("crags", areaToView.getCrags());
        if (request.getSession().getAttribute(AppConstants.USER) != null) {
            model.addAttribute(AppConstants.USER, request.getSession().getAttribute(AppConstants.USER));
        }

        return "area/view";
    }

}
=======
package com.sendfriend.controllers;

import com.sendfriend.models.Area;
import com.sendfriend.models.data.AreaDao;
import com.sendfriend.models.data.UserDao;
import com.sendfriend.models.forms.AddForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        model.addAttribute(new AddForm());
        if (request.getSession().getAttribute("user") != null) {
            model.addAttribute("user", request.getSession().getAttribute("user"));
        }

        return "area/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddAreaForm(Model model, @ModelAttribute @Valid AddForm form, Errors errors) {

       if (errors.hasErrors()) {
           model.addAttribute("title", "Add Area");
           return "redirect:/area/add";
       }

       Area area = new Area(form.getName(), form.getDescription());
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
>>>>>>> develop
