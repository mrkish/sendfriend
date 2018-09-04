package com.sendfriend.controllers;

import com.sendfriend.models.Area;
import com.sendfriend.models.Crag;
import com.sendfriend.models.data.AreaDao;
import com.sendfriend.models.data.CragDao;
import com.sendfriend.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "crag")
public class CragController {

    @Autowired
    private CragDao cragDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AreaDao areaDao;

    @RequestMapping(value = "")
    public String index(Model model, HttpSession session) {

        model.addAttribute("title", "Crags");
        model.addAttribute("crags", cragDao.findAll());
        if (session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }

        return "crag/index";
    }

    @RequestMapping(value = "view/{cragId}")
    public String viewSingleCrag(Model model, HttpSession session, @PathVariable int cragId) {

        Crag crag = cragDao.findById(cragId);

        if (crag == null) {
            model.addAttribute("errors", "Crag not found");
            return "redirect:/crag";
        }

        model.addAttribute("title", crag.getName());
        model.addAttribute("crag", crag);
        model.addAttribute("routes", crag.getRoutes());
        if (session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }

        return "crag/view";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCragForm(Model model, HttpSession session) {

        model.addAttribute("title", "Add Crag");
        model.addAttribute(new Crag());
        model.addAttribute("areas", areaDao.findAll());
        if (session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }

        return "crag/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCragForm(Model model, HttpSession session, @ModelAttribute @Valid Crag crag, Errors errors, String area) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Crag");

            return "redirect:/crag/add";
        }

        if (areaDao.findByName(area) == null) {
            Area newArea = new Area(area);
            areaDao.save(newArea);
        }

        List<Area> areaToSet = areaDao.findByName(area);

        if(areaToSet.size() == 1) {
            crag.setArea(areaToSet.get(0));
            cragDao.save(crag);
        } else {
            cragDao.save(crag);
        }

        return "redirect:view/" + crag.getId();
    }
}
