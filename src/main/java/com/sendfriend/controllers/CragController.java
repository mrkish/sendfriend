package com.sendfriend.controllers;

import com.sendfriend.models.Area;
import com.sendfriend.models.Crag;
import com.sendfriend.models.data.AreaDao;
import com.sendfriend.models.data.CragDao;
import com.sendfriend.models.data.UserDao;
import com.sendfriend.models.forms.AddForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "crag")
public class CragController extends AbstractController {

    @Autowired
    private CragDao cragDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AreaDao areaDao;

    @RequestMapping(value = "")
    public String index(Model model, HttpServletRequest request) {

        model.addAttribute("title", "Crags");
        model.addAttribute("crags", cragDao.findAll());
        if (request.getSession().getAttribute("user") != null) {
            model.addAttribute("user", request.getSession().getAttribute("user"));
        }

        return "crag/index";
    }

    @RequestMapping(value = "view/{cragId}")
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

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCragForm(Model model, HttpServletRequest request) {

        model.addAttribute("title", "Add Crag");
        model.addAttribute(new AddForm());
        model.addAttribute("areas", areaDao.findAll());
        if (request.getSession().getAttribute("user") != null) {
            model.addAttribute("user", request.getSession().getAttribute("user"));
        }

        return "crag/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
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

    @ResponseBody
    @RequestMapping(value = "api", method = RequestMethod.GET)
    public HashMap<Integer, String> getCragsJson(@RequestParam(value = "areaId") int areaId) {

        HashMap<Integer, String> response = new HashMap<Integer, String>();
        List<Crag> crags = cragDao.findByAreaId(areaId);
        for (Crag aCrag : crags) {
            response.put(aCrag.getId(), aCrag.getName());
        }
        return response;
    }

}
