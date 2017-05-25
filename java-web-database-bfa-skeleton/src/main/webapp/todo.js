function init() {
    $('.portlet').remove();
    $.getJSON('tasks', function (data) {
        $.each(data, function (index, task) {
            createElement(task['id'], task['title'], task['description'], task['state'])
        });
        createCard();
    });
}

function createElement(id, title, desc, state) {
    var portlet = document.createElement('div');
    portlet.setAttribute('class', 'portlet');
    portlet.setAttribute('id', 'task' + id);
    var header = document.createElement('div');
    header.setAttribute('class', 'portlet-header');
    header.innerHTML = title;
    var content = document.createElement('div');
    content.setAttribute('class', 'portlet-content');
    content.setAttribute('style', 'display: none;');
    content.innerHTML = desc;
    var icons = document.createElement('div');
    icons.setAttribute('class', 'icons');
    var update = document.createElement('img');
    update.setAttribute('id', 'update-task' + id);
    update.setAttribute('src', 'https://cdn1.iconfinder.com/data/icons/e-mail-2/512/YPS__email_mail_refresh_update_sendreceive-512.png');
    update.setAttribute('onclick', 'updateTask(' + id + ')');
    var dbtn = document.createElement('img');
    dbtn.setAttribute('id', 'delete-task' + id);
    dbtn.setAttribute('src', 'http://www.iconsdb.com/icons/preview/dim-gray/trash-2-xxl.png')
    dbtn.setAttribute('onclick', 'deleteTask(' + id +')');
    icons.appendChild(update);
    icons.appendChild(dbtn);
    content.appendChild(icons);
    portlet.appendChild(header);
    portlet.appendChild(content);
    $('#' + state).append(portlet);
}

function deleteTask(id) {
    $.post('/task', {method: "DELETE", taskID: id}, function () {
        init();
    });
}

function updateTask(id) {
    var headerText = $('#task' + id).find('.portlet-header').first().contents().eq(1).text();
    var contentText = $('#task' + id).find('.portlet-content').html();
    var content = contentText.substr(0, contentText.indexOf("<"));
    var icons = $('#task' + id).find('.icons').html();
    $('#task' + id).find('.portlet-header').html('<input id="title' + id + '" type="text" class="text ui-widget-content ui-corner-all" style="width: 90%" value="' + headerText + '">');
    $('#task' + id).find('.portlet-content').html('<input id="content' + id + '" type="text" class="text ui-widget-content ui-corner-all" style="width: 90%" value="' + content + '">' + icons);
    $('#task' + id).find('#update-task' + id).attr('onclick', 'realUpdate(' + id + ')');
    $('#title' + id).click(function () {
        $(this).focus().val($('#title' + id).val());
    });
}

function realUpdate(id) {
    $.post('/task', {method: "UPDATE", title: $('#title' + id).val(), desc: $('#content' + id).val(), tID: id}, function () {
        init();
    })
}

function createCard() {
    $(function () {
        $(".column").sortable({
            connectWith: ".column",
            handle: ".portlet-header",
            cancel: ".portlet-toggle",
            placeholder: "portlet-placeholder ui-corner-all",
            receive: function (event, ui) {
                var progress = $(this).attr('id');
                var item = ui.item.attr('id');
                $.post('/todo', {state: progress, task: item});
            }
        });

        $(".portlet")
            .addClass("ui-widget ui-widget-content ui-helper-clearfix ui-corner-all")
            .find(".portlet-header")
            .addClass("ui-widget-header ui-corner-all")
            .prepend("<span class='ui-icon ui-icon-plusthick portlet-toggle'></span>");

        $(".portlet-toggle").on("click", function () {
            var icon = $(this);
            icon.toggleClass("ui-icon-plusthick ui-icon-minusthick");
            icon.closest(".portlet").find(".portlet-content").toggle();
        });
    });
}

$(function () {
    var dialog, form,

        title = $("#task_title"),
        desc = $("#task_desc"),
        state = $("#task_state"),
        allFields = $([]).add(title).add(desc).add(state),
        tips = $(".validateTips");

    function updateTips(t) {
        tips
            .text(t)
            .addClass("ui-state-highlight");
        setTimeout(function () {
            tips.removeClass("ui-state-highlight", 1500);
        }, 500);
    }

    function checkLength(o, n, min, max) {
        if (o.val().length > max || o.val().length < min) {
            o.addClass("ui-state-error");
            updateTips("Length of " + n + " must be between " +
                min + " and " + max + ".");
            return false;
        } else {
            return true;
        }
    }

    function checkRegexp(o, regexp, n) {
        if (!( regexp.test(o.val()) )) {
            o.addClass("ui-state-error");
            updateTips(n);
            return false;
        } else {
            return true;
        }
    }

    function addTask() {
        var valid = true;
        allFields.removeClass("ui-state-error");

        valid = valid && checkLength(title, "title", 3, 10);
        valid = valid && checkLength(desc, "description", 6, 80);

        valid = valid && checkRegexp(title, /^[a-z]([0-9a-z_\s])+$/i, "Title may consist of a-z, 0-9, underscores, spaces and must begin with a letter.");
        valid = valid && checkRegexp(desc, /^[a-z]([0-9a-z_\s])+$/i, "Description may consist of a-z, 0-9, underscores, spaces and must begin with a letter.");

        if (valid) {
            $.post('tasks', {title: title.val(), desc: desc.val(), state: state.val()});
            dialog.dialog("close");
            init();
        }
        return valid;
    }

    dialog = $("#dialog-form").dialog({
        autoOpen: false,
        height: 400,
        width: 350,
        modal: true,
        buttons: {
            "Add": addTask,
            Cancel: function () {
                dialog.dialog("close");
            }
        },
        close: function () {
            form[0].reset();
            allFields.removeClass("ui-state-error");
        }
    });

    form = dialog.find("form").on("submit", function (event) {
        event.preventDefault();
        addTask();
    });

    $('#add-task').button().on("click", function () {
        dialog.dialog("open");
    });

    $('#logout').button().on("click", function () {
        $.post('index', function () {
            $(location).attr('href', '/');
        });
    });
});

onload = init();
