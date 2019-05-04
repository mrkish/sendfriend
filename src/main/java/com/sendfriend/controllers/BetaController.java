package com.sendfriend.controllers;

import com.sendfriend.data.*;
import com.sendfriend.models.Beta;
import com.sendfriend.models.Route;
import com.sendfriend.models.User;
import com.sendfriend.models.forms.AddForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;

@Controller
@RequestMapping(value = "beta")
public class BetaController extends AbstractController {

    private UserDao userDao;
    private RouteDao routeDao;
    private CragDao cragDao;
    private AreaDao areaDao;
    private BetaDao betaDao;

    public BetaController(UserDao userDao,
                          RouteDao routeDao,
                          CragDao cragDao,
                          AreaDao areaDao,
                          BetaDao betaDao)  {
        this.userDao = userDao;
        this.routeDao = routeDao;
        this.cragDao = cragDao;
        this.areaDao = areaDao;
        this.betaDao = betaDao;
    }

    @GetMapping(value = "")
    public String displayAllBeta(Model model, HttpServletRequest request) {

        model.addAttribute("title", "Betas");
        model.addAttribute("betas", betaDao.findAll());
        if (request.getSession().getAttribute("user") != null) {
            model.addAttribute("user", request.getSession().getAttribute("user"));
        }

        return "beta/index";
    }

    @GetMapping(value = "/add/routeId={routeId}")
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

    @PostMapping(value = "/add/routeId={routeId}")
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

    @GetMapping(value = "view/{betaId}")
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

        return "beta/view";
    }

    @GetMapping(value = "edit/{betaId}")
    public String displayEditBetaForm(Model model, HttpServletRequest request, @PathVariable int betaId) {

        Beta betaToEdit = betaDao.findById(betaId);
        model.addAttribute("title", "Editing: " + betaToEdit.getName());
        model.addAttribute("betaToEdit", betaToEdit);

        return "beta/edit";
    }

    @PostMapping(value = "edit/{betaId}")
    public String processEditBetaForm(Model model, @ModelAttribute Beta betaToEdit, @PathVariable int betaId, boolean isPublic) {

        Beta beta = betaDao.findById(betaToEdit.getId());
        Route route = routeDao.findById(beta.getRoute().getId());

        beta.setDescription(beta.getDescription());
        beta.setIsPublic(isPublic);
        beta.setRoute(route);
        betaDao.save(beta);

        return "redirect:beta/view/" + beta.getId();
    }

    @GetMapping(value = "remove")
    public String displayDeleteBetaForm(Model model, HttpServletRequest request) {

        User currentUser = getUserFromSession(request.getSession());
        User userFromDao = userDao.findById(currentUser.getId());
        Set<Beta> userBetas = userDao.getUserBetaByUserId(userFromDao.getId());

        model.addAttribute("betas", userBetas);
        model.addAttribute("title", "Remove Betas");

        return "beta/remove";
    }

    @PostMapping(value = "remove")
    public String processDeleteBetaForm(@RequestParam int[] betasToDelete) {

        for (int betaId : betasToDelete) {
            betaDao.deleteById(betaId);
        }

        return "user/betas";
    }
}
