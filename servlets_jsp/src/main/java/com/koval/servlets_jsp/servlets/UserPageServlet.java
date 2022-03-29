package com.koval.servlets_jsp.servlets;

import com.koval.servlets_jsp.entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserPageServlet", value = "/user_page")
public class UserPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        request.setAttribute("userName", currentUser.getFirstName());

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/pages/user_page.jsp");
        requestDispatcher.forward(request, response);
    }
}