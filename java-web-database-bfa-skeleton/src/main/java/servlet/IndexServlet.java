package servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by david_szilagyi on 2017.05.15..
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String redirect = "/";
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        if (req.getParameter("user").equals("admin") && req.getParameter("pass").equals("admin")) {
            req.getSession().setAttribute("user", req.getParameter("user"));
            redirect = "/todo";
        }
        resp.getWriter().write(redirect);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("user");
    }
}
