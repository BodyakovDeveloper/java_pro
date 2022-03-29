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

@WebServlet(name = "EditPageServlet", value = "/admin/edit_page")
public class EditPageServlet extends HttpServlet {

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

        UserEntity userEntity = userDaoService.findByLogin(request.getParameter("login"));
        request.setAttribute("userForEdit", userEntity);

        request.getRequestDispatcher("/WEB-INF/pages/edit_user.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        UserEntity userEntity = userDaoService.findByLogin(request.getParameter("login"));
        RoleEntity roleEntity = roleDaoService.getRoleByName(request.getParameter("roleName"));

        String email = request.getParameter("email");

        if (!userEntity.getEmail().equals(email)
                && userDaoService.findByEmail(email) != null) {
            response.sendRedirect("/admin/main_page");
        } else {

            String password = request.getParameter("password");
            if (!password.equals("")) {
                userEntity.setPassword(password);
            }

            userEntity.setEmail(request.getParameter("email"));
            userEntity.setFirstName(request.getParameter("firstName"));
            userEntity.setLastName(request.getParameter("lastName"));
            userEntity.setBirthday(Date.valueOf(request.getParameter("birthday")));
            userEntity.setRoleEntity(roleEntity);

            userDaoService.update(userEntity);

            response.sendRedirect("/admin/main_page");
        }
    }
}