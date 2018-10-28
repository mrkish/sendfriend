package com.sendfriend.controllers;

import com.sendfriend.models.*;
import com.sendfriend.models.data.*;
import com.sendfriend.models.forms.AddForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "beta")
public class BetaController extends AbstractController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RouteDao routeDao;

    @Autowired
    private CragDao cragDao;

    @Autowired
    private AreaDao areaDao;

    @Autowired
    private BetaDao betaDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String displayAllBeta(Model model, HttpServletRequest request) {

        model.addAttribute("title", "Betas");
        model.addAttribute("betas", betaDao.findAll());
        if (request.getSession().getAttribute("user") != null) {
            model.addAttribute("user", request.getSession().getAttribute("user"));
        }

        return "beta/index";
    }

    @RequestMapping(value = "/add/routeId={routeId}")
    public String displayAddBetaForm(Model model, HttpServletRequest request, @PathVariable int routeId) {

        if (!model.containsAttribute("user")) {
            model.addAttribute("errors", "You can only add beta if you're a registered user.");
            return "beta/index";
        }

        Route route = routeDao.findById(routeId);
        model.addAttribute("route", route);
        model.addAttribute("title", "Add Beta");
        model.addAttribute("AddForm", new AddForm());

        return "beta/add";
    }

    @RequestMapping(value = "/add/routeId={routeId}", method = RequestMethod.POST)
    public String processAddBetaForm(@Valid AddForm addForm, Model model, Errors errors, int userId, @PathVariable int routeId, boolean isPublic) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Beta");
            return "redirect:/add/routeId=" + routeId;
        }

        Route route = routeDao.findById(routeId);
        User user = userDao.findById(userId);
        Beta newBeta = new Beta(addForm.getDescription(), isPublic, user, route, addForm.getName());
        betaDao.save(newBeta);

        return "redirect:/beta/view/" + newBeta.getId();
    }

    @RequestMapping(value = "view/{betaId}")
    public String viewSingleBeta(Model model, HttpServletRequest request, @PathVariable int betaId) {

        Beta beta = betaDao.findById(betaId);
        Route route = routeDao.findById(beta.getRoute().getId());

        if (route == null || beta == null) {
            model.addAttribute("errors", "Route or Beta not found!");
            return "beta/index";
        }

        model.addAttribute("title", "View Beta for " + route.getName());
        model.addAttribute("route", route);
        model.addAttribute("beta", beta);
//        if (request.getSession().getAttribute("user") != null) {
//            model.addAttribute("user", request.getSession().getAttribute("user"));
//        }

        return "beta/view";
    }

    @RequestMapping(value = "edit/{betaId")
    public String processEditBetaForm(Model model, @PathVariable int betaId, int routeId, String description, boolean isPublic) {

        Beta beta = betaDao.findById(betaId);
        Route route = routeDao.findById(routeId);

        beta.setDescription(description);
        beta.setIsPublic(isPublic);
        beta.setRoute(route);
        betaDao.save(beta);

        return "redirect:beta/view/" + beta.getId();
    }

    @RequestMapping(value = "remove")
    public String displayDeleteBetaForm(Model model, HttpServletRequest request) {

        User currentUser = getUserFromSession(request.getSession());
        User userFromDao = userDao.findById(currentUser.getId());
        Set<Beta> userBetas = userDao.getUserBetaByUserId(userFromDao.getId());

        model.addAttribute("betas", userBetas);
        model.addAttribute("title", "Remove Betas");

        return "beta/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processDeleteBetaForm(@RequestParam int[] betasToDelete) {

        for (int betaId : betasToDelete) {
            betaDao.deleteById(betaId);
        }

        return "user/betas";
    }
}
