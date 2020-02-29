package org.exampleProjectOne.servlets;

import org.exampleProjectOne.dao.UserDao;
import org.exampleProjectOne.model.User;
import org.exampleProjectOne.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/ServletCreate")
public class ServletCreate extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        Long age = Long.parseLong(req.getParameter("age"));
        User addUser = null;
        if (name != null && password != null && age != null) {
            addUser = new User(name, password, age);
        }
        try {
            if (UserService.getUserService().addUser(addUser)) {
                List<User> userList = UserService.getUserService().getUserByName(addUser.getName());
                req.setAttribute("users", userList);
                req.getRequestDispatcher("showUser.jsp").forward(req, response);
            } else {
                outPrint(response);
            }
        } catch (Exception e) {
            outPrint(response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("createUser.jsp").forward(request, response);
    }

    public static void outPrint(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body><h2>");
        out.println("All badly.");
        out.println("</h2></body></html>");
    }

}
