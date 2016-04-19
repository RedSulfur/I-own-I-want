package com.iowniwant.controller.servlet;

import com.iowniwant.dao.implementation.GoalDao;
import com.iowniwant.dao.implementation.UserDao;
import com.iowniwant.model.Goal;
import com.iowniwant.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

/**
 * Removes goal from DataBase.
 */
@WebServlet(name = "RemoveGoalsServlet", urlPatterns = "/removeGoalsServlet")
public class RemoveGoalsServlet extends HttpServlet{

    private static Logger log = LoggerFactory.getLogger(AddGoalsServlet.class);
    private GoalDao goalDao = GoalDao.getInstance();
    private UserDao userDao = UserDao.getInstance();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userID = (Integer) request.getServletContext().getAttribute("user_id");
        log.debug("user_id obtained from the servletContext: {}", userID);

        // user associated with goal
        User user = userDao.getById(userID);

        String title = request.getParameter("title");
        Double cost = Double.valueOf(request.getParameter("cost"));
        String shorten = request.getParameter("shorten");
        String description = request.getParameter("description");
        Date pubdate = new Date(new java.util.Date().getTime());

        log.debug("Title was obtained due to the ajax function: {}", title);
        log.debug("Cost was obtained due to the ajax function: {}", cost);
        log.debug("Brief notes were obtained due to the ajax function: {}", shorten);
        log.debug("Description was obtained due to the ajax function: {}", description);

        if (title != null && shorten != null && description != null) {
            Goal goal = new Goal(title,cost,shorten,pubdate,description,user);
            goalDao.create(goal);
        }
    }
}
