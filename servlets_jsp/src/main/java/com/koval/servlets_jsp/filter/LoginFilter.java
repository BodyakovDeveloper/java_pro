package com.koval.servlets_jsp.filter;

import com.koval.servlets_jsp.entities.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/login", "/"}, filterName = "LoginFilter")
public class LoginFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final HttpSession session = httpServletRequest.getSession();

        User user = (User) session.getAttribute("currentUser");

        if (user != null) {
            if (user.getRole() == User.Role.USER) {
                request.getRequestDispatcher("user_page").forward(request, response);
            } else if (user.getRole() == User.Role.ADMIN) {
                request.getRequestDispatcher("admin/main_page").forward(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
    }
}