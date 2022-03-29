package com.koval.servlets_jsp.listener;

import com.koval.servlets_jsp.dao.JdbcRoleDao;
import com.koval.servlets_jsp.dao.JdbcUserDao;
import com.koval.servlets_jsp.dao.RoleDao;
import com.koval.servlets_jsp.dao.UserDao;
import com.koval.servlets_jsp.services.RoleDaoService;
import com.koval.servlets_jsp.services.UserDaoService;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {

        ServletContext context = contextEvent.getServletContext();

        UserDao userDao = new JdbcUserDao();
        RoleDao roleDao = new JdbcRoleDao();
        UserDaoService userDaoService = new UserDaoService(userDao);
        RoleDaoService roleDaoService = new RoleDaoService(roleDao);

        context.setAttribute("UserDaoService", userDaoService);
        context.setAttribute("RoleDaoService", roleDaoService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {

    }
}