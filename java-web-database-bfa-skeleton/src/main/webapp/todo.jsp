<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ToDos</title>
    <script src="webjars/jquery/3.2.0/jquery.min.js"></script>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script src="todo.js"></script>
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <link href="todo.css" rel="stylesheet"/>
</head>
<body>

<div id="dialog-form" title="Add new task">
    <p class="validateTips">All fields are required.</p>

    <form>
        <fieldset>
            <label for="task_title">Title</label>
            <input type="text" name="task_title" id="task_title" class="text ui-widget-content ui-corner-all">
            <label for="task_desc">Description</label>
            <input type="text" name="task_desc" id="task_desc" class="text ui-widget-content ui-corner-all">
            <label for="task_state">Status</label>
            <select id="task_state" class="text ui-widget-content ui-corner-all">
                <option value="0">New</option>
                <option value="1">In progress</option>
                <option value="2">Completed</option>
            </select>
            <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
        </fieldset>
    </form>
</div>
<div id="toDoList">
    <div id="0" class="box column">
        <h2>New</h2>
    </div>
    <div id="1" class="box column">
        <h2>In Progress</h2>
    </div>
    <div id="2" class="box column">
        <h2>Completed</h2>
    </div>
</div>
<button id="add-task">Add new task</button>
<button id="logout">Logout</button>
</body>
</html>
