package objects;

/**
 * Created by david_szilagyi on 2017.05.15..
 */
public class Task {
    String id;
    String title;
    String description;
    int state;

    public Task(String title, String description, int state) {
        this.title = title;
        this.description = description;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getState() {
        return state;
    }
}
