<<<<<<< HEAD
package com.sendfriend.controllers;

import com.sendfriend.domain.*;
import com.sendfriend.repository.*;
import com.sendfriend.domain.forms.LoginForm;
import com.sendfriend.domain.forms.RegisterForm;
import com.sendfriend.util.AppConstants;
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
public class UserController extends AbstractController {

    private static final String USER_REGISTER = "user/register";
    private static final String USER_LOGIN = "user/login";
    private  static final String BETAS = "betas";

    private RouteDao routeDao;
    private BetaDao betaDao;

    public UserController(RouteDao routeDao,
                          BetaDao betaDao) {
        this.routeDao = routeDao;
        this.betaDao = betaDao;
    }

    @GetMapping(value = "")
    public String index(Model model, HttpServletRequest request) {

        model.addAttribute(AppConstants.TITLE, "Sendfriend! | Index");
        User loggedinUser = getUserForModel(request);
        model.addAttribute(AppConstants.USER, loggedinUser);

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
            } while (!model.containsAttribute("featured") || routes.isEmpty());
        }

        return "user/index";
    }

    @GetMapping(value = "register")
    public String displayRegisterForm(Model model) {

        model.addAttribute(AppConstants.TITLE, "Sendfriend | Register New User");
        model.addAttribute(new RegisterForm());

        return USER_REGISTER;
    }

    @PostMapping(value = "register")
    public String processRegisterForm(@ModelAttribute @Valid RegisterForm form, Errors errors,
                                      HttpServletRequest request) {

        if (errors.hasErrors()) {
            return USER_REGISTER;
        }

        User foundName = userDao.findByUsername(form.getUsername());

        if (foundName != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists.");
            return USER_REGISTER;
        }

        User newUser = new User(form.getUsername(), form.getPassword(), form.getEmail());
        userDao.save(newUser);
        setUserInSession(request.getSession(), newUser);

        return "redirect:";
    }

    @GetMapping(value = "login")
    public String displayLogin(Model model) {
        model.addAttribute(AppConstants.TITLE, "Sendfriend | Login!");
        model.addAttribute(new LoginForm());

        return USER_LOGIN;
    }

    @PostMapping(value = "login")
    public String processLogin(@ModelAttribute @Valid LoginForm form, Errors errors, HttpServletRequest request) {

        if (errors.hasErrors()) {
            return USER_LOGIN;
        }

        User theUser = userDao.findByUsername(form.getUsername());
        String password = form.getPassword();

        if (theUser == null) {
            errors.rejectValue("username", "user.invalid", "The given username does not exist.");
            return USER_LOGIN;
        }

        if (!theUser.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password.");
            return USER_LOGIN;
        }

        setUserInSession(request.getSession(), theUser);

        return "redirect:";
    }

    @GetMapping(value = "logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();

        return "redirect:/login";
    }

    @GetMapping(value = AppConstants.USER)
    public String userIndex(Model model) {

       model.addAttribute(AppConstants.TITLE, "Sendfriend! | Index");
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
        model.addAttribute(AppConstants.TITLE, user.getUsername() + " | Profile");
        model.addAttribute(BETAS, user.getBetas());
        model.addAttribute("friends", user.getFriends());

        return "user/profile/view";
    }

    @GetMapping(value = "profile/betas")
    public String displayUserBetas(Model model, HttpServletRequest request) {

        User loggedInUser = getUserForModel(request);
        User user = userDao.findById(loggedInUser.getId());
        model.addAttribute(AppConstants.TITLE, user.getUsername() + " | Betas");
        model.addAttribute(BETAS, betaDao.findByUserId(user.getId()));

        return "user/profile/betas";
    }

    @GetMapping(value = "profile/share-beta")
    public String displayShareBetaForm(Model model, HttpServletRequest request) {

        User loggedInUser = getUserForModel(request);
        User user = userDao.findById(loggedInUser.getId());
        List<Beta> userBetas = (List<Beta>) userDao.getUserBetaByUserId(user.getId());

        model.addAttribute(BETAS, userBetas);
        model.addAttribute(AppConstants.TITLE, "Share Beta");

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
       model.addAttribute(BETAS, userPublicBetas);
       model.addAttribute(AppConstants.USER, userToView);
       model.addAttribute(AppConstants.TITLE, userToView.getUsername() + " | Profile");

        return "/user/view-profile";
   }

   @GetMapping(value = "/user/add-friend/{userId}")
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
=======
package com.sendfriend.controllers;

import com.sendfriend.models.*;
import com.sendfriend.models.data.*;
import com.sendfriend.models.forms.LoginForm;
import com.sendfriend.models.forms.RegisterForm;
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
public class UserController extends AbstractController {

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

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String displayRegisterForm(Model model) {

        model.addAttribute("title", "Sendfriend | Register New User");
        model.addAttribute(new RegisterForm());

        return "user/register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
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

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String displayLogin(Model model) {
        model.addAttribute("title", "Sendfriend | Login!");
        model.addAttribute(new LoginForm());

        return "user/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
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

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();

        return "redirect:/login";
    }

    @RequestMapping(value = "user")
    public String userIndex(Model model) {

       model.addAttribute("title", "Sendfriend! | Index");
       model.addAttribute("users", userDao.findAll());

       return "user/users";
    }

    @RequestMapping(value = "profile")
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

    @RequestMapping(value = "profile/betas")
    public String displayUserBetas(Model model, HttpServletRequest request) {

//        User user = (User) request.getSession().getAttribute("user");
        User loggedInUser = getUserForModel(request);
        User user = userDao.findById(loggedInUser.getId());
        model.addAttribute("title", user.getUsername() + " | Betas");
        model.addAttribute("betas", betaDao.findByUserId(user.getId()));

        return "user/profile/betas";
    }

    @RequestMapping(value = "profile/share-beta")
    public String displayShareBetaForm(Model model, HttpServletRequest request) {

//        User user = (User) request.getSession().getAttribute("user");
        User loggedInUser = getUserForModel(request);
        User user = userDao.findById(loggedInUser.getId());
        List<Beta> userBetas = new ArrayList<>();
        userBetas = (List<Beta>) userDao.getUserBetaByUserId(user.getId());

        model.addAttribute("betas", userBetas);
        model.addAttribute("title", "Share Beta");

        return "user/profile/share-beta";
    }

    @RequestMapping(value = "/user/view/{userId}")
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

   @RequestMapping(value = "user", method = RequestMethod.GET)
   public String displayUserIndex(Model model) {

       model.addAttribute("title", "Sendfriend! | Users");
       model.addAttribute("users", userDao.findAll());

       return "user/users";
   }

   @RequestMapping(value = "user/add-friend/{userId}")
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
>>>>>>> develop
