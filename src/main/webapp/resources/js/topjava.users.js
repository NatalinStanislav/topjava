// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "admin/users/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "name"
                    },
                    {
                        "data": "email"
                    },
                    {
                        "data": "roles"
                    },
                    {
                        "data": "enabled"
                    },
                    {
                        "data": "registered"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "asc"
                    ]
                ]
            })
        }
    );
});

function updateTable() {
    $.get(context.ajaxUrl, function (data) {
        fillTable(data);
    });
}

function updateUserState(obj, id) {
    var state = $(obj).prop("checked");
    $.ajax({
        url: context.ajaxUrl + id + "?enabled=" + state,
        type: "PATCH"
    }).done(function () {
        var element = $(obj).parents("tr");
        if (state) {
            element.removeClass("translucent");
            successNoty("Enabled");
        } else {
            element.addClass("translucent");
            successNoty("Disabled");
        }
    }).fail(function () {
        $(obj).prop("checked", !state);
    });
}