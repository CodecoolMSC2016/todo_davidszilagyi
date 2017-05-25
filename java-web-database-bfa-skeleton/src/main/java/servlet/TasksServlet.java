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
@WebServlet("/tasks")
public class TasksServlet extends HttpServlet {
    DatabaseHiDao dao = new DatabaseHiDao(ConnectionUtil.getConnection(ConnectionUtil.DatabaseName.todo));

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        addTask(request.getParameter("title"),
            request.getParameter("desc"),
            Integer.valueOf(request.getParameter("state")));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write(dao.getTasks());
        out.flush();
    }

    private void addTask(String title, String desc, int state) {
        Task task = new Task(title, desc, state);
        dao.addNewTask(task);
    }
}
