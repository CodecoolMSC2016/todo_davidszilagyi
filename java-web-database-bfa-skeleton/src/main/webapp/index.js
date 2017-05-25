/**
 * Created by David Szilagyi on 2017. 05. 22..
 */
$(function () {
    var dialog, form,

        name = $("#name"),
        password = $("#password"),
        allFields = $([]).add(name).add(password),
        tips = $(".validateTips");

    function updateTips(t) {
        tips
            .text(t)
            .addClass("ui-state-highlight");
        setTimeout(function () {
            tips.removeClass("ui-state-highlight", 1500);
        }, 500);
    }

    function letMeIn() {
        var valid = true;
        allFields.removeClass("ui-state-error");

        if (valid) {
            $.get('index', {user: name.val(), pass: password.val()}, function (data) {
                if (data != '/') {
                    $(location).attr('href', data);
                    dialog.dialog("close");
                } else {
                    name.addClass('ui-state-error');
                    password.addClass('ui-state-error');
                    updateTips('Username or password incorrect!');
                }
            });
        }
        return valid;
    }

    dialog = $("#dialog-form").dialog({
        autoOpen: false,
        height: 400,
        width: 350,
        modal: true,
        buttons: {
            "Login": letMeIn,
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
        letMeIn();
    });

    $("#login").button().on("click", function () {
        dialog.dialog("open");
    });
});

