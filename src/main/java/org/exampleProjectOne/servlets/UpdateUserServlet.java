package org.exampleProjectOne.servlets;

import org.exampleProjectOne.model.User;
import org.exampleProjectOne.service.UserServiceMysql;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(updateUser(req)){
            List<User> userList = null;
            try {
                userList = UserServiceMysql.getUserServiceMysql().getAllUser();
            } catch (Exception e) {
                resp.setContentType("text/html;charset=utf-8");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
            req.setAttribute("users", userList);
            req.getRequestDispatcher("usersList.jsp").forward(req, resp);
        } else {
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> userList = createPageList(req);
        req.setAttribute("users", userList);
        req.getRequestDispatcher("editUser.jsp").forward(req, resp);
    }

    private List<User> createPageList(HttpServletRequest request) {
        List<User> userList = null;
        Long id = Long.parseLong(request.getParameter("id"));
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        Long age = Long.parseLong(request.getParameter("age"));
        if (id != null && name != null && password != null && age != null) {
            userList = new ArrayList<>();
            userList.add(new User(id, name, password, age));
            return userList;
        }
        return userList;
    }

    private boolean updateUser (HttpServletRequest req){
        Long id = Long.parseLong(req.getParameter("id"));
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        Long age = Long.parseLong(req.getParameter("age"));
        if (id != null && name != null && password != null && age != null) {
            try {
                UserServiceMysql.getUserServiceMysql().updateClient(id, name, password, age);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

}
