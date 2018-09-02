package com.sendfriend.controllers;

import com.sendfriend.models.Beta;
import com.sendfriend.models.Crag;
import com.sendfriend.models.Route;
import com.sendfriend.models.User;
import com.sendfriend.models.data.BetaDao;
import com.sendfriend.models.data.CragDao;
import com.sendfriend.models.data.RouteDao;
import com.sendfriend.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequestMapping(value = "")
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RouteDao routeDao;

    @Autowired
    private CragDao cragDao;

    @Autowired
    private BetaDao betaDao;

    @RequestMapping(value = "")
    public String index(Model model, HttpSession session) {

        model.addAttribute("title", "Sendfriend! | Index");
        model.addAttribute("users", userDao.findAll());

//      #TODO: 1) Get a random route; .size() is not working on the routeDao.findAll() return for some reason.

        if (session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }

        return "user/index";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String displayRegisterForm(Model model) {

        model.addAttribute("title", "Sendfriend | Register New User");
        model.addAttribute(new User());

        return "user/register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String processRegisterForm(@ModelAttribute @Valid User user, Errors errors, Model model,
                                      String verify, HttpSession session) {

        User foundName = userDao.findByUsername(user.getUsername());

        if (foundName != null) {
            if (errors.hasErrors() || (foundName.equals(user.getUsername()))) {
                model.addAttribute("title", "Sendfriend | Register New User");
                model.addAttribute("user", user);

                return "redirect:/register";
            }
        } else {
           if (errors.hasErrors()) {
               model.addAttribute("title", "Sendfriend | Register New User");
               model.addAttribute("user", user);

               return "redirect:/register";
           }
        }

        userDao.save(user);
        session.setAttribute("user", user);

        return "redirect:/";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String displayLogin(Model model) {
        model.addAttribute("title", "Sendfriend | Login!");
        return "user/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processLogin(Model model, @RequestParam String username, @RequestParam String password, HttpSession session) {

        User userExists = userDao.findByUsername(username);

        if (username.isEmpty() || password.isEmpty()) {
            model.addAttribute("errors", "Login failed");
            return "redirect:user/login";
        }

        String userExistsPassword = userExists.getPassword();
        if (userExistsPassword.equals(password)) {
            session.setAttribute("user", userExists);
            model.addAttribute("user", userExists);

            return "user/index";
        }

        return "redirect:/login";
    }

    @RequestMapping(value = "logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/login";
    }

    @RequestMapping(value = "beta", method = RequestMethod.GET)
    public String displayAllBeta(Model model, HttpSession session) {

        model.addAttribute("title", "Betas");
        model.addAttribute("betas", betaDao.findAll());
        if (session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }

        return "beta/index";
    }

    @RequestMapping(value = "beta/add", method = RequestMethod.GET)
    public String displayAddBetaForm(Model model, HttpSession session) {

        model.addAttribute("title", "Add Beta");
        model.addAttribute(new Beta());

        if (session.getAttribute("user") == null) {
            model.addAttribute("errors", "You can only add beta if you're a registered user.");
        } else if (session.getAttribute("user") != null) {
            User username = (User) session.getAttribute("user");
            User user = userDao.findByUsername(username.getUsername());
            model.addAttribute("user", user);
        }

        return "beta/add";
    }

    @RequestMapping(value = "beta/add", method = RequestMethod.POST)
    public String processAddBetaForm(Model model, HttpSession session, @Valid Beta newBeta, Errors errors, String route, String crag, User user, boolean shared) {

        List<Route> routeCandidates = routeDao.findByName(route);
        List<Crag> cragCandidates = cragDao.findByName(crag);



        if ((routeCandidates.size() == 1) && (cragCandidates.size() == 1) &&
                (routeCandidates.get(0).getCrag().equals(crag))) {
            newBeta.setRoute(routeCandidates.get(0));
            newBeta.setUser(user);
            Route routeCandidate = routeCandidates.get(0);
            newBeta.setRoute(routeDao.findById(routeCandidate.getId()));

            betaDao.save(newBeta);

            return "redirect:/beta/view/" + newBeta.getId();
        }

        return "redirect:/route/view/";
    }

    private Beta addBetaProcess(Beta newBeta, Route route, Crag crag) {

        if (routeDao.findByName(route.getName()) != null && cragDao.findByName(crag.getName()) != null) {


        }

        if (routeDao.findByName(route.getName()) != null && cragDao.findByName(crag.getName()) == null) {

        }

        if (routeDao.findByName(route.getName()) == null && cragDao.findByName(crag.getName()) != null) {

        }

        if (routeDao.findByName(route.getName()) == null && cragDao.findByName(crag.getName()) == null) {

        }       


        return newBeta;
    }

    @RequestMapping(value = "profile")
    public String displayCurrentUserProfile(Model model, HttpSession session) {

        if (session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        } else if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        User username = (User) session.getAttribute("user");
        User user = userDao.findByUsername(username.getUsername());
        model.addAttribute("title", user.getUsername() + " | Profile");
        model.addAttribute("betas", betaDao.findByUserId(user.getId()));

        return "user/profile";
    }

   @RequestMapping(value = "profile/{userId}")
    public String viewOtherUserProfile(Model model, HttpSession session, @RequestParam int userId) {

       if (session.getAttribute("user") != null) {
           model.addAttribute("user", session.getAttribute("user"));
       }

       List<Beta> allUserBetas = betaDao.findByUserId(userId);
       List<Beta> userPublicBetas = new ArrayList<>();
       for(Beta beta : allUserBetas) {
           if (beta.isShared() == true) {
               userPublicBetas.add(beta);
           }
       }

       User userToView = userDao.findById(userId);
       String userProfile = userToView.getUsername();
       model.addAttribute("betas", userPublicBetas);
       model.addAttribute("userProfile", userProfile);
       model.addAttribute("title", userProfile + " | Profile");

        return "/user/view-profile";
   }
}
