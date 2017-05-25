package servlet;

import dao.DatabaseHiDao;
import util.ConnectionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by david_szilagyi on 2017.05.15..
 */
@WebServlet("/todo")
public class ToDoServlet extends HttpServlet {
    DatabaseHiDao dao = new DatabaseHiDao(ConnectionUtil.getConnection(ConnectionUtil.DatabaseName.todo));

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") != null) {
            req.getRequestDispatcher("todo.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String progress = req.getParameter("state");
        String item = req.getParameter("task");
        dao.reorder(item, progress);
    }
}
