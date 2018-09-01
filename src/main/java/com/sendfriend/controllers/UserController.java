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
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequestMapping(value = "")
public class UserController {

    @Autowired
    UserDao userDao;

    @Autowired
    RouteDao routeDao;

    @Autowired
    CragDao cragDao;

    @Autowired
    BetaDao betaDao;

    public boolean checkLogin(HttpServletRequest request) {

        boolean loggedIn = false;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().contains("user") && c.getValue() != null) {
                    loggedIn = true;
                }
            }
        }
        return loggedIn;
    }

    @RequestMapping(value = "")
    public String index(Model model, @CookieValue(value = "user", defaultValue = "none") String username) {

        model.addAttribute("title", "Sendfriend! | Index");
        model.addAttribute("users", userDao.findAll());

//      #TODO: 1) Get a random route; .size() is not working on the routeDao.findAll() return for some reason.

        List<User> user = userDao.findByUsername(username);

        if (!username.equals("none") && (user.size() >= 1)) {
            User loggedIn = user.get(0);
            model.addAttribute("user", loggedIn);
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
                                      String verify, HttpServletResponse response) {

        List<User> foundName = userDao.findByUsername(user.getUsername());

        if (errors.hasErrors() || (foundName.equals(user.getUsername()))) {
            model.addAttribute("title", "Sendfriend | Register New User");
            model.addAttribute("user", user);

            return "redirect:/register";
        }
        userDao.save(user);
        Cookie c = new Cookie("user", user.getUsername());
        c.setPath("/");
        response.addCookie(c);

        return "user/index";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String displayLogin(Model model) {
        model.addAttribute("title", "Sendfriend | Login!");
        return "user/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String processLogin(Model model, String username, String password, HttpServletResponse response) {

        List<User> userExists = userDao.findByUsername(username);

        if (userExists.isEmpty()) {
            model.addAttribute("title", "Sendfriend | Login!");
            model.addAttribute("errors", "Login failed");
        }

        User loggedIn = userExists.get(0);
        if (loggedIn.getPassword().equals(password)) {
            Cookie c = new Cookie("user", loggedIn.getUsername());
            c.setPath("/");
            response.addCookie(c);

            return "redirect:/";
        }

        model.addAttribute("title", "Login!");
        model.addAttribute("error", "Login failed!");

        return "redirect:/login";
    }

    @RequestMapping(value = "logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                c.setMaxAge(0);
                c.setPath("/");
                response.addCookie(c);
            }
        }
        return "user/login";
    }

    @RequestMapping(value = "beta", method = RequestMethod.GET)
    public String displayAddBetaForm(Model model) {

        model.addAttribute("title", "Add Beta");
        model.addAttribute(new Beta());

        return "beta/add";
    }

    @RequestMapping(value = "beta", method = RequestMethod.POST)
    public String processAddBetaForm(Model model, Errors errors, @Valid Beta newBeta, String route, String crag, boolean shared, @ModelAttribute User user) {

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

        return "redirect:/beta/view/";
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

}
