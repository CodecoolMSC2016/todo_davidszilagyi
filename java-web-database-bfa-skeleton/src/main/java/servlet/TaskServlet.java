package servlet;

import dao.DatabaseHiDao;
import objects.Task;
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
@WebServlet("/task")
public class TaskServlet extends HttpServlet {
    DatabaseHiDao dao = new DatabaseHiDao(ConnectionUtil.getConnection(ConnectionUtil.DatabaseName.todo));

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String method = request.getParameter("method");
        switch (method) {
            case "UPDATE":
                dao.update(request.getParameter("title"),
                    request.getParameter("desc"),
                    Integer.valueOf(request.getParameter("tID")));
                break;
            case "DELETE":
                String id = request.getParameter("taskID");
                dao.deleteTask(id);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
