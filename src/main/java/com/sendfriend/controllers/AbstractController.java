<<<<<<< HEAD
package com.sendfriend.controllers;

import com.sendfriend.domain.User;
import com.sendfriend.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AbstractController {

    @Autowired
    protected UserDao userDao;

    public static final String USER_SESSION_KEY = "user_id";

    protected User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(USER_SESSION_KEY);
        return userId == null ? null : userDao.findUserById(userId);
    }

    protected void setUserInSession(HttpSession session, User user) {
        session.setAttribute(USER_SESSION_KEY, user.getId());
    }

    @ModelAttribute("user")
    public User getUserForModel(HttpServletRequest request) {
        return getUserFromSession(request.getSession());
    }

}
=======
package com.sendfriend.controllers;

import com.sendfriend.models.User;
import com.sendfriend.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AbstractController {

    @Autowired
    protected UserDao userDao;

    public static final String userSessionKey = "user_id";

    protected User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        return userId == null ? null : userDao.findUserById(userId);
    }

    protected void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());
    }

    @ModelAttribute("user")
    public User getUserForModel(HttpServletRequest request) {
        return getUserFromSession(request.getSession());
    }

}
>>>>>>> develop
