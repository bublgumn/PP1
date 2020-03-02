package org.exampleProjectOne.servlets;

import org.exampleProjectOne.model.User;
import org.exampleProjectOne.service.UserServiceMysql;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/ListUsersServlet")
public class ListUsersServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

        List<User> userList = null;
        try {
            userList = UserServiceMysql.getUserServiceMysql().getAllUser();
        } catch (Exception e) {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        request.setAttribute("users", userList);
        request.getRequestDispatcher("usersList.jsp").forward(request, resp);

    }
}
