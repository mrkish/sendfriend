package com.sendfriend.controllers;

import com.sendfriend.data.AreaDao;
import com.sendfriend.data.CragDao;
import com.sendfriend.data.UserDao;
import com.sendfriend.models.Area;
import com.sendfriend.models.Crag;
import com.sendfriend.models.forms.AddForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "crag")
public class CragController extends AbstractController {

    private CragDao cragDao;
    private UserDao userDao;
    private AreaDao areaDao;

    public CragController(CragDao cragDao,
                          UserDao userDao,
                          AreaDao areaDao) {
        this.cragDao = cragDao;
        this.userDao = userDao;
        this.areaDao = areaDao;
    }

    @GetMapping(value = "")
    public String index(Model model, HttpServletRequest request) {

        model.addAttribute("title", "Crags");
        model.addAttribute("crags", cragDao.findAll());
        if (request.getSession().getAttribute("user") != null) {
            model.addAttribute("user", request.getSession().getAttribute("user"));
        }

        return "crag/index";
    }

    @GetMapping(value = "view/{cragId}")
    public String viewSingleCrag(Model model, HttpServletRequest request, @PathVariable int cragId) {

        Crag crag = cragDao.findById(cragId);

        if (crag == null) {
            model.addAttribute("errors", "Crag not found");
            return "redirect:/crag";
        }

        model.addAttribute("title", crag.getName());
        model.addAttribute("crag", crag);
        model.addAttribute("routes", crag.getRoutes());
        if (request.getSession().getAttribute("user") != null) {
            model.addAttribute("user", request.getSession().getAttribute("user"));
        }

        return "crag/view";
    }

    @GetMapping(value = "add")
    public String displayAddCragForm(Model model, HttpServletRequest request) {

        model.addAttribute("title", "Add Crag");
        model.addAttribute(new AddForm());
        model.addAttribute("areas", areaDao.findAll());
        if (request.getSession().getAttribute("user") != null) {
            model.addAttribute("user", request.getSession().getAttribute("user"));
        }

        return "crag/add";
    }

    @PostMapping(value = "add")
    public String processAddCragForm(Model model, @ModelAttribute @Valid AddForm form, Errors errors, int areaId) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Crag");

            return "redirect:/crag/add";
        }

        if (areaDao.findById(areaId) == null) {
            model.addAttribute("error", "Please add parent area first!");

            return "crag/add";
        }

        Area areaToSet = areaDao.findById(areaId);
        Crag crag = new Crag(form.getName(), form.getDescription());
        crag.setArea(areaToSet);
        cragDao.save(crag);

        return "redirect:view/" + crag.getId();
    }

}
