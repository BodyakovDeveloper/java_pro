package com.koval.hibernate.servlets;

import com.koval.hibernate.entities.UserEntity;

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
        UserEntity currentUser = (UserEntity) request.getSession().getAttribute("currentUser");
        request.setAttribute("userName", currentUser.getFirstName());

        request.getRequestDispatcher("/WEB-INF/pages/user_page.jsp").forward(request, response);
    }
}