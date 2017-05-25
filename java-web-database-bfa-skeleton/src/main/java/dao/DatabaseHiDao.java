package dao;

import com.google.gson.Gson;
import objects.Task;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseHiDao {

    private final Connection conn;
    Gson gson = new Gson();

    public DatabaseHiDao(Connection conn) {
        this.conn = conn;
    }

    public void addNewTask(Task task) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            String sql = String.format("INSERT INTO tasks(title, description, state)" +
                "VALUES(\"%s\", \"%s\", \"%d\");", task.getTitle(), task.getDescription(), task.getState());
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getTasks() {
        ArrayList<Task> tempTasksList = new ArrayList<>();
        Statement statement = null;
        try {
            statement = conn.createStatement();
            String sql = "SELECT * FROM tasks;";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Task tempTask = new Task(resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getInt("state"));
                tempTask.setId(resultSet.getString("id"));
                tempTasksList.add(tempTask);
            }
            return gson.toJson(tempTasksList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void reorder(String id, String state) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            String sql = String.format("UPDATE tasks SET state=%d WHERE id=%d;", Integer.valueOf(state), Integer.valueOf(id.substring(4)));
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String title, String desc, int id) {
        Statement statement = null;
        try {
            statement = conn.createStatement();
            String sql = String.format("UPDATE tasks SET title=\"%s\", description=\"%s\" WHERE id=\"%d\";",
                title, desc, id);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(String id) {
        try {
            Statement statement = conn.createStatement();
            String sql = String.format("DELETE FROM tasks WHERE id=%s;", id);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
