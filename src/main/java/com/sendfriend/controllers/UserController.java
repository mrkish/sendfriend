package com.sendfriend.controllers;

import com.sendfriend.models.*;
import com.sendfriend.models.data.*;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

            return "redirect:/";
        }

        return "redirect:/";
    }

    @RequestMapping(value = "logout")
    public String logout(HttpSession session) {
        session.invalidate();

        return "redirect:/login";
    }

    @RequestMapping(value = "user")
    public String userIndex(Model model, HttpSession session) {

       model.addAttribute("title", "Sendfriend! | Index");
       model.addAttribute("users", userDao.findAll());
       if (session.getAttribute("user") != null) {
           model.addAttribute("user", session.getAttribute("user"));
       }

       return "user/users";
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
        model.addAttribute("friends", user.getFriends());

        return "user/profile/view";
    }

    @RequestMapping(value = "profile/betas")
    public String displayUserBetas(Model model, HttpSession session) {

        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        User user = (User) session.getAttribute("user");
        model.addAttribute("title", user.getUsername() + " | Betas");
        model.addAttribute("betas", betaDao.findByUserId(user.getId()));

        return "user/profile/betas";
    }

    @RequestMapping(value = "profile/share-beta")
    public String displayShareBetaForm(Model model, HttpSession session) {

        if (session.getAttribute("user") != null) {
            model.addAttribute("user", session.getAttribute("user"));
        } else if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        User user = (User) session.getAttribute("user");
        List<Beta> userBetas = new ArrayList<>();
        userBetas = (List<Beta>) userDao.getUserBetaByUsername(user.getUsername());

        model.addAttribute("betas", userBetas);
        model.addAttribute("title", "Share Beta");

        return "user/profile/share-beta";
    }

    @RequestMapping(value = "profile/{userId}")
    public String viewOtherUserProfile(Model model, HttpSession session, @RequestParam int userId) {

       if (session.getAttribute("user") != null) {
           model.addAttribute("user", session.getAttribute("user"));
       }

       List<Beta> allUserBetas = betaDao.findByUserId(userId);
       List<Beta> userPublicBetas = new ArrayList<>();
       for(Beta beta : allUserBetas) {
           if (beta.getIsPublic()) {
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

   @RequestMapping(value = "user", method = RequestMethod.GET)
   public String displayUserIndex(Model model, HttpSession session) {

       model.addAttribute("title", "Sendfriend! | Users");
       model.addAttribute("users", userDao.findAll());
       if (session.getAttribute("user") != null) {
           model.addAttribute("user", session.getAttribute("user"));
       }

        return "user/users";
   }

   @RequestMapping(value = "user/add-friend/{userId}")
    public String addFriend(Model model, HttpSession session, @PathVariable int userId) {

       if (session.getAttribute("user") == null) {
           model.addAttribute("error", "You're not logged in or registered!");
           return "redirect:user/users";
       }

       User userToFriend = userDao.findById(userId);

       String currentUsername = ((User) session.getAttribute("user")).getUsername();
       User currentUser = userDao.findByUsername(currentUsername);
       Set<User> currentUserFriends = currentUser.getFriends();

       if (currentUserFriends.contains(userToFriend)) {
           model.addAttribute("error", "You're already friends!");
       } else {
//           currentUserFriends.add(userToFriend);
           currentUser.addFriend(userToFriend);
           userDao.save(currentUser);
       }

       return "user/profile/view";
   }
}
