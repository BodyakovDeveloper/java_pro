package com.koval.servlets_jsp.servlets;

import com.koval.servlets_jsp.entities.User;
import com.koval.servlets_jsp.services.RoleDaoService;
import com.koval.servlets_jsp.services.UserDaoService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

@WebServlet(name = "AddUserServlet", value = "/admin/add_user")
public class AddUserServlet extends HttpServlet {

    private UserDaoService userDaoService;
    private RoleDaoService roleDaoService;

    @Override
    public void init() {
        userDaoService = (UserDaoService) getServletContext().getAttribute("UserDaoService");
        roleDaoService = (RoleDaoService) getServletContext().getAttribute("RoleDaoService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("roles", roleDaoService.findAll());

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/add_user.jsp");
        requestDispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        User user = new User();

        user.setLogin(request.getParameter("login"));
        user.setPassword(request.getParameter("password"));
        user.setEmail(request.getParameter("email"));
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setBirthday(Date.valueOf(request.getParameter("birthday")));
        user.setRole(User.Role.valueOf(request.getParameter("roleName")));

        userDaoService.create(user);

        response.sendRedirect("/admin/main_page");
    }
}