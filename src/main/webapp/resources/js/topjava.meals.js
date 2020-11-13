$(function () {
    makeEditable({
            ajaxUrl: "profile/meals/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
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
                        "desc"
                    ]
                ]
            })
        }
    );
});

function updateTable() {
    $.ajax({
        url: context.ajaxUrl + "filter",
        type: "GET",
        data: $("#filter").serialize()
    }).done(function (data) {
        fillTable(data);
    });
}
function clearFilter() {
    $("#filter").trigger('reset');
    $.get(context.ajaxUrl, function (data) {
        fillTable(data);
    });
}