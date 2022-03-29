package com.koval.hibernate.listener;

import com.koval.hibernate.services.RoleDaoService;
import com.koval.hibernate.services.UserDaoService;
import com.koval.hibernate.dao.HibernateRoleDao;
import com.koval.hibernate.dao.HibernateUserDao;
import com.koval.hibernate.dao.RoleDao;
import com.koval.hibernate.dao.UserDao;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {

        ServletContext context = contextEvent.getServletContext();

        UserDao userDao = new HibernateUserDao();
        RoleDao roleDao = new HibernateRoleDao();
        UserDaoService userDaoService = new UserDaoService(userDao);
        RoleDaoService roleDaoService = new RoleDaoService(roleDao);

        context.setAttribute("UserDaoService", userDaoService);
        context.setAttribute("RoleDaoService", roleDaoService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {

    }
}