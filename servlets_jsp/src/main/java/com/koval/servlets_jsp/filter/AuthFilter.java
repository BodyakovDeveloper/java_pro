
package com.koval.servlets_jsp.filter;

import com.koval.servlets_jsp.entities.User;
import com.koval.servlets_jsp.services.UserDaoService;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;

@WebFilter(urlPatterns = {"/user_page", "/admin/*"}, description = "AuthFilter")
public class AuthFilter implements Filter {
    final Map<User.Role, List<String>> accessRule = Map.of(
            User.Role.ADMIN, List.of("/admin/main_page", "/admin/add_user", "/admin/edit_page", "/admin/delete_user"),
            User.Role.USER, List.of("/user_page")
    );

    private static final String defaultUserUri = "/user_page";
    private static final String defaultAdminUri = "/admin/main_page";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain) throws IOException, ServletException {

        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        final HttpSession session = httpServletRequest.getSession();
        UserDaoService userDaoService = (UserDaoService) session.getServletContext().getAttribute("UserDaoService");

        String uri = httpServletRequest.getRequestURI();
        String contextPath = httpServletRequest.getContextPath();
        uri = uri.substring(contextPath.length());

        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser != null) {

            final String currentUserLogin = currentUser.getLogin();
            final String currentUserPassword = currentUser.getPassword();

            if (nonNull(session.getAttribute("currentUser"))) {
                moveToMenu(httpServletRequest, httpServletResponse, currentUser, uri);

            } else if (userDaoService.isUserExists(currentUserLogin, currentUserPassword)) { // user exists in db and need to auth

                session.setAttribute("currentUser", userDaoService.findByLogin(currentUserLogin));

                moveToMenu(httpServletRequest, httpServletResponse, currentUser, uri);
            }
        } else {
            ((HttpServletResponse) response).sendRedirect("/login");
        }
    }

    private void moveToMenu(final HttpServletRequest req,
                            final HttpServletResponse res,
                            final User currentUser, final String uri) throws ServletException, IOException {

        if (isUserHasRightsToVisitUri(currentUser, uri)) {
            req.getRequestDispatcher(uri).forward(req, res);
        } else {
            req.getRequestDispatcher(getDefaultUriByRole(currentUser.getRole())).forward(req, res);
        }
    }

    private String getDefaultUriByRole(User.Role role) {
        String uriToRedirect = null;

        if (role.equals(User.Role.ADMIN)) {
            uriToRedirect = defaultAdminUri;

        } else if (role.equals(User.Role.USER)) {
            uriToRedirect = defaultUserUri;
        }

        return uriToRedirect;
    }

    private boolean isUserHasRightsToVisitUri(User user, String uri) {
        return accessRule.entrySet().stream()
                .filter(el -> el.getKey().equals(user.getRole()))
                .anyMatch(el -> el.getValue().contains(uri));
    }

    @Override
    public void destroy() {

    }
}