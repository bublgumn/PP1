package org.exampleProjectOne.servlets;

import org.exampleProjectOne.model.User;
import org.exampleProjectOne.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/ServletOne")
public class ServletOne extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");

        if (name != null) {
            try {
                List<User> userList = UserService.getUserService().getUserByName(name);
                if (userList.size() > 0) {
                    UserService.getUserService().deleteUser(userList.get(0));
                }
                List<User> userLists = null;
                try {
                    userLists = UserService.getUserService().getAllUser();
                } catch (Exception e) {
                    ServletCreate.outPrint(resp);
                }
                req.setAttribute("users", userLists);
                req.getRequestDispatcher("usersList.jsp").forward(req, resp);
            } catch (Exception e) {
                ServletCreate.outPrint(resp);
            }
        } else {
            ServletCreate.outPrint(resp);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<User> userList = null;
        try {
            userList = UserService.getUserService().getAllUser();
        } catch (Exception e) {
            ServletCreate.outPrint(response);
        }

        request.setAttribute("users", userList);
        request.getRequestDispatcher("usersList.jsp").forward(request, response);

    }
}
