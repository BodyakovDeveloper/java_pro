package com.koval.servlets_jsp.servlets;

import com.koval.servlets_jsp.entities.User;
import com.koval.servlets_jsp.services.UserDaoService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDaoService userDaoService;

    @Override
    public void init() {
        userDaoService = (UserDaoService) getServletContext().getAttribute("UserDaoService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final String login = request.getParameter("login");
        final String password = request.getParameter("password");

        if (userDaoService.isUserExists(login, password)) { // user exists in db but need to auth

            User user = userDaoService.findByLogin(login);
            request.getSession().setAttribute("currentUser", user);

            moveToMenu(response, user.getRole());
        } else {
            String message = "Login of password is wrong";
            response.sendRedirect("index.jsp?message=" + URLEncoder.encode(message, StandardCharsets.UTF_8));
        }
    }

    private void moveToMenu(HttpServletResponse res,
                            User.Role role) throws IOException {

        if (role.equals(User.Role.ADMIN)) {
            res.sendRedirect("/admin/main_page");

        } else if (role.equals(User.Role.USER)) {
            res.sendRedirect("/user_page");
        }
    }
}