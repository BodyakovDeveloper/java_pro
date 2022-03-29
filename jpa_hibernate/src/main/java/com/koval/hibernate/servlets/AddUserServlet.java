package com.koval.hibernate.servlets;

import com.koval.hibernate.entities.RoleEntity;
import com.koval.hibernate.entities.UserEntity;
import com.koval.hibernate.services.RoleDaoService;
import com.koval.hibernate.services.UserDaoService;

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

        request.getRequestDispatcher("/WEB-INF/pages/add_user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String email = request.getParameter("email");
        String login = request.getParameter("login");

        if (userDaoService.findByLogin(login) != null
                || userDaoService.findByEmail(email) != null) {

            response.sendRedirect("/admin/main_page");
        } else {
            UserEntity userEntity = new UserEntity();
            RoleEntity roleEntity = roleDaoService.getRoleByName(request.getParameter("roleName"));

            userEntity.setLogin(login);
            userEntity.setEmail(email);
            userEntity.setPassword(request.getParameter("password"));
            userEntity.setFirstName(request.getParameter("firstName"));
            userEntity.setLastName(request.getParameter("lastName"));
            userEntity.setBirthday(Date.valueOf(request.getParameter("birthday")));
            userEntity.setRoleEntity(roleEntity);

            userDaoService.create(userEntity);

            response.sendRedirect("/admin/main_page");
        }
    }
}