package com.koval.hibernate.tags;

import com.koval.hibernate.entities.UserEntity;

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

    private List<UserEntity> users = new ArrayList<>();
    private JspWriter out;
    private String adminName;

    void init() {
        final PageContext pageContext = (PageContext) getJspContext();
        final HttpSession session = pageContext.getSession();

        UserEntity currentUser = (UserEntity) session.getAttribute("currentUser");
        adminName = currentUser.getFirstName();

        out = pageContext.getOut();
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }

    @Override
    public void doTag() throws IOException {

        init();
        out.write("<h2>Admin " + adminName + " (<a href=\"/logout\" onclick=\"return confirm('Are you sure?')\">Logout</a>) </h2>" +
                "<div class=\"add_new_user\"><a href=\"/admin/add_user\">Add new user</a> </div>" +
                "<table class=\"container\"><thead>" +
                "<tr><td>Login</td>" +
                "<td>First name</td>" +
                "<td>Last name</td>" +
                "<td>Email</td>" +
                "<td>age</td>" +
                "<td>Role</td>" +
                "<td>Action</td>" +
                "</tr></thead><tbody>");

        for (UserEntity userEntity : users) {

            out.write("<tr>");
            out.write("<td>" + userEntity.getLogin() + "</td>");
            out.write("<td>" + userEntity.getFirstName() + "</td>");
            out.write("<td>" + userEntity.getLastName() + "</td>");
            out.write("<td>" + userEntity.getEmail() + "</td>");
            out.write("<td>" + calculateAge(userEntity.getBirthday().toLocalDate()) + "</td>");
            out.write("<td>" + userEntity.getRoleEntity().getName() + "</td>");

            out.write("<td>" +
                    "<form action=\"/admin/edit_page\" method=\"get\">" +
                    "<button type=\"submit\" class=\"btn-edit\">edit</button>" +
                    "<input type=\"hidden\" name=\"login\" value=\"" + userEntity.getLogin() + "\"/>" +
                    "</form>"
                    +
                    "<form action=\"/admin/delete_user?login=" + userEntity.getLogin() + "\" method=\"post\">" +
                    "<button type=\"submit\" class=\"btn-delete\" " +
                    "onclick=\"return confirm('Are you sure?')\"");

            if (userEntity.getRoleEntity().getName().equals("ADMIN")) {
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