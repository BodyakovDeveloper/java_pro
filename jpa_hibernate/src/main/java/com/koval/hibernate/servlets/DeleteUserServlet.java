package com.koval.hibernate.servlets;

import com.koval.hibernate.entities.UserEntity;
import com.koval.hibernate.services.UserDaoService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteUserServlet", value = "/admin/delete_user")
public class DeleteUserServlet extends HttpServlet {

    private UserDaoService userDaoService;

    @Override
    public void init() {
        userDaoService = (UserDaoService) getServletContext().getAttribute("UserDaoService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        UserEntity userEntity = userDaoService.findByLogin(request.getParameter("login"));
        userDaoService.remove(userEntity);
        response.sendRedirect("/admin/main_page");
    }
}