package com.sendfriend.controllers;

import com.sendfriend.data.*;
import com.sendfriend.models.Beta;
import com.sendfriend.models.Route;
import com.sendfriend.models.User;
import com.sendfriend.models.forms.LoginForm;
import com.sendfriend.models.forms.RegisterForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequestMapping(value = "")
public class UserController extends AbstractController {

    private UserDao userDao;
    private RouteDao routeDao;
    private CragDao cragDao;
    private AreaDao areaDao;
    private BetaDao betaDao;

    public UserController(UserDao userDao,
                          RouteDao routeDao,
                          CragDao cragDao,
                          AreaDao areaDao,
                          BetaDao betaDao) {
        this.userDao = userDao;
        this.routeDao = routeDao;
        this.cragDao = cragDao;
        this.areaDao = areaDao;
        this.betaDao = betaDao;
    }

    @GetMapping(value = "")
    public String index(Model model, HttpServletRequest request) {

        model.addAttribute("title", "Sendfriend! | Index");
        User loggedinUser = getUserForModel(request);
        model.addAttribute("user", loggedinUser);

        ArrayList<Route> routes = (ArrayList<Route>) routeDao.findAll();
        if (routes.size() > 1) {
            do {
                int max = routes.size();
                int random = ThreadLocalRandom.current().nextInt(max);
                Route featured = routes.get(random);
                if (featured.getCrag() != null && featured.getCrag().getArea() != null) {
                    model.addAttribute("featuredArea", featured.getCrag().getArea());
                }
                model.addAttribute("featured", featured);
            } while (!model.containsAttribute("featured") || routes.size() == 0);
        }

        return "user/index";
    }

    @GetMapping(value = "register")
    public String displayRegisterForm(Model model) {

        model.addAttribute("title", "Sendfriend | Register New User");
        model.addAttribute(new RegisterForm());

        return "user/register";
    }

    @PostMapping(value = "register")
    public String processRegisterForm(@ModelAttribute @Valid RegisterForm form, Errors errors,
                                      HttpServletRequest request) {

        if (errors.hasErrors()) {
            return "user/register";
        }

        User foundName = userDao.findByUsername(form.getUsername());

        if (foundName != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists.");
            return "user/register";
        }

        User newUser = new User(form.getUsername(), form.getPassword(), form.getEmail());
        userDao.save(newUser);
        setUserInSession(request.getSession(), newUser);

        return "redirect:";
    }

    @GetMapping(value = "login")
    public String displayLogin(Model model) {
        model.addAttribute("title", "Sendfriend | Login!");
        model.addAttribute(new LoginForm());

        return "user/login";
    }

    @PostMapping(value = "login")
    public String processLogin(@ModelAttribute @Valid LoginForm form, Errors errors, HttpServletRequest request) {

        if (errors.hasErrors()) {
            return "user/login";
        }

        User theUser = userDao.findByUsername(form.getUsername());
        String password = form.getPassword();

        if (theUser == null) {
            errors.rejectValue("username", "user.invalid", "The given username does not exist.");
            return "user/login";
        }

        if (!theUser.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password.");
            return "user/login";
        }

        setUserInSession(request.getSession(), theUser);

        return "redirect:";
    }

    @GetMapping(value = "logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();

        return "redirect:/login";
    }

    @GetMapping(value = "user")
    public String userIndex(Model model) {

       model.addAttribute("title", "Sendfriend! | Index");
       model.addAttribute("users", userDao.findAll());

       return "user/users";
    }

    @GetMapping(value = "profile")
    public String displayCurrentUserProfile(Model model, HttpServletRequest request) {

        User loggedInUser = getUserForModel(request);
        if (loggedInUser == null) {
            return "redirect:login";
        }
        User user = userDao.findById(loggedInUser.getId());
        model.addAttribute("title", user.getUsername() + " | Profile");
        model.addAttribute("betas", user.getBetas());
        model.addAttribute("friends", user.getFriends());

        return "user/profile/view";
    }

    @GetMapping(value = "profile/betas")
    public String displayUserBetas(Model model, HttpServletRequest request) {

        User loggedInUser = getUserForModel(request);
        User user = userDao.findById(loggedInUser.getId());
        model.addAttribute("title", user.getUsername() + " | Betas");
        model.addAttribute("betas", betaDao.findByUserId(user.getId()));

        return "user/profile/betas";
    }

    @GetMapping(value = "profile/share-beta")
    public String displayShareBetaForm(Model model, HttpServletRequest request) {

        User loggedInUser = getUserForModel(request);
        User user = userDao.findById(loggedInUser.getId());
        List<Beta> userBetas = (List<Beta>) userDao.getUserBetaByUserId(user.getId());

        model.addAttribute("betas", userBetas);
        model.addAttribute("title", "Share Beta");

        return "user/profile/share-beta";
    }

    @GetMapping(value = "/user/view/{userId}")
    public String viewOtherUserProfile(Model model, @PathVariable int userId) {

       List<Beta> allUserBetas = betaDao.findByUserId(userId);
       List<Beta> userPublicBetas = new ArrayList<>();

       for(Beta beta : allUserBetas) {
           if (beta.getIsPublic()) {
               userPublicBetas.add(beta);
           }
       }

       User userToView = userDao.findById(userId);
       model.addAttribute("betas", userPublicBetas);
       model.addAttribute("user", userToView);
       model.addAttribute("title", userToView.getUsername() + " | Profile");

        return "/user/view-profile";
   }

   @GetMapping(value = "user/add-friend/{userId}")
    public String addFriend(Model model, HttpServletRequest request, @PathVariable int userId) {

       User loggedInUser = getUserForModel(request);
       User userFromDAO = userDao.findById(loggedInUser.getId());
       Set<User> currentUserFriends = userFromDAO.getFriends();
       User userToFriend = userDao.findById(userId);

       if (currentUserFriends.contains(userToFriend)) {
           model.addAttribute("error", "You're already friends!");
       } else {
           userFromDAO.addFriend(userToFriend);
           userDao.save(userFromDAO);
           userToFriend.addFriend(loggedInUser);
           userDao.save(userToFriend);
       }

       return "user/profile/view";
   }
}
