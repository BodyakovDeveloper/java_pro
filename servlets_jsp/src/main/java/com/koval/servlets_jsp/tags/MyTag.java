package com.koval.servlets_jsp.tags;

import com.koval.servlets_jsp.entities.User;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class MyTag extends SimpleTagSupport {

    private List<User> users = new ArrayList<>();
    private JspWriter out;
    private String adminName;

    void init() {
        final PageContext pageContext = (PageContext) getJspContext();
        final HttpSession session = pageContext.getSession();
        User currentUser = (User) session.getAttribute("currentUser");
        adminName = currentUser.getFirstName();

        out = pageContext.getOut();
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public void doTag() throws IOException {

        init();
        out.write("<h2 align=\"right\">Admin " + adminName + " (<a href=\"/logout\">Logout</a>) </h2>" +
                "<h4 style=\"margin-left: 480px; margin-top: 19%;\"><a href=\"/admin/add_user\">Add new user</a> </h4>" +
                "<table border=\"1\" style=\"margin: 0px auto; margin-top: 1%;\" width=\"900\" cellpadding=\"10\"><thead>" +
                "<tr><td>Login</td>" +
                "<td>First name</td>" +
                "<td>Last name</td>" +
                "<td>age</td>" +
                "<td>Role</td>" +
                "<td>Action</td>" +
                "</tr></thead><tbody>");

        for (User user : users) {

            out.write("<tr>");
            out.write("<td>" + user.getLogin() + "</td>");
            out.write("<td>" + user.getFirstName() + "</td>");
            out.write("<td>" + user.getLastName() + "</td>");
            out.write("<td>" + calculateAge(user.getBirthday().toLocalDate()) + "</td>");
            out.write("<td>" + user.getRole() + "</td>");

            out.write("<td>" +
                "<form action=\"/admin/edit_page\" method=\"get\">" +
                "<button type=\"submit\" class=\"btn-link\">edit</button>" +
                "<input type=\"hidden\" name=\"login\" value=\"" + user.getLogin() + "\"/>" +
                "</form>"
                +
                "<form action=\"/admin/delete_user?login=" + user.getLogin() + "\" method=\"post\">" +
                "<button type=\"submit\" class=\"btn-link\" " +
                        "onclick=\"return confirm('Are you sure?')\"");

                if (user.getRole().equals(User.Role.ADMIN)) {
                    out.write(" disabled ");
                }

                out.write(">delete</button></form>" +
                        "</td>");
                out.write("</tr>");
        }

        out.write("</tbody></table>");
        out.close();
    }

    public int calculateAge(LocalDate birthDate) {
        if (birthDate != null) {
            return Period.between(birthDate, LocalDate.now()).getYears();
        } else {
            return 0;
        }
    }
}