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
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    var startTime = $("#startTime").val();
    var endTime = $("#endTime").val();
    $.ajax({
        url: context.ajaxUrl + "filter?startDate=" + startDate + "&endDate=" + endDate + "&startTime=" + startTime + "&endTime=" + endTime,
        type: "GET"
    }).done(function (data) {
        context.datatableApi.clear().rows.add(data).draw();
    });
}
function clearFilter() {
    $("#startDate").val("");
    $("#endDate").val("");
    $("#startTime").val("");
    $("#endTime").val("");
}