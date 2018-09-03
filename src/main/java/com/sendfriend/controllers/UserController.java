package com.sendfriend.controllers;

import com.sendfriend.models.*;
import com.sendfriend.models.data.*;
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
    private AreaDao areaDao;

    @Autowired
    private BetaDao betaDao;

    @RequestMapping(value = "")
    public String index(Model model, HttpSession session) {

        model.addAttribute("title", "Sendfriend! | Index");
        model.addAttribute("users", userDao.findAll());

        Route featured;
        do {
            ArrayList<Route> routes = (ArrayList<Route>) routeDao.findAll();
            int max = routes.size();
            int random = ThreadLocalRandom.current().nextInt(max);
            featured = routes.get(random);
        } while (featured == null);
        model.addAttribute("featured", featured);

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

    @RequestMapping(value = "beta/select-area")
    public String displaySelectAreaForm(Model model, HttpSession session) {

        List<Area> areasOptions = (List<Area>) areaDao.findAll();
        areasOptions.add(new Area());

        model.addAttribute("areas", areasOptions);
        model.addAttribute("title", "Select Area");
        if (session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        }

        return "beta/select-area";
    }

    @RequestMapping(value = "beta/add", method = RequestMethod.GET)
    public String displayAddBetaForm(Model model, HttpSession session, @RequestParam String newArea, @RequestParam Area area) {

        if (newArea.isEmpty() && area != null) {
            List<Crag> crags = area.getCrags();
            model.addAttribute("crags", crags);
        }
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
    public String processAddBetaForm(Model model, HttpSession session, @Valid Beta newBeta, Errors errors, String routeName, String cragName, User user, boolean shared) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Beta");
            return "beta/add";
        }



        return "redirect:/route/view/";
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
