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

@WebServlet("/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        if (name != null) {
            try {
                List<User> userList = UserServiceMysql.getUserServiceMysql().getUserByName(name);
                if (userList.size() > 0) {
                    UserServiceMysql.getUserServiceMysql().deleteUser(userList.get(0));
                }
                List<User> userLists = null;
                try {
                    userLists = UserServiceMysql.getUserServiceMysql().getAllUser();
                } catch (Exception e) {
                    resp.setContentType("text/html;charset=utf-8");
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
                req.setAttribute("users", userLists);
                req.getRequestDispatcher("usersList.jsp").forward(req, resp);
            } catch (Exception e) {
                resp.setContentType("text/html;charset=utf-8");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }
}
