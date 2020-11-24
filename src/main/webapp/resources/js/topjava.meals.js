var mealAjaxUrl = "profile/meals/";

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get("profile/meals/", updateTableByData);
}

$(function () {
    makeEditable({
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (data, type, row) {
                        if (type === "display") {
                            return data.substring(0, 16).replace("T"," ");
                        }
                        return data;
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                    $(row).attr("data-mealExcess", data.excess);
            }
        }),
        updateTable: updateFilteredTable
    });
    $.datetimepicker.setLocale(navigator.language.substring(0,2));
    $("#dateTime").datetimepicker({
        format:"Y-m-d H:i"
    });
    jQuery(function(){
        jQuery('#startDate').datetimepicker({
            format:"Y-m-d",
            onShow:function( ct ){
                this.setOptions({
                    maxDate:jQuery('#endDate').val()?jQuery('#endDate').val():false
                })
            },
            timepicker:false
        });
        jQuery('#endDate').datetimepicker({
            format:"Y-m-d",
            onShow:function( ct ){
                this.setOptions({
                    minDate:jQuery('#startDate').val()?jQuery('#startDate').val():false
                })
            },
            timepicker:false
        });
    });
    jQuery(function(){
        jQuery('#startTime').datetimepicker({
            format:'H:i',
            onShow:function( ct ){
                this.setOptions({
                    maxTime:jQuery('#endTime').val()?jQuery('#endTime').val():false
                })
            },
            datepicker:false
        });
        jQuery('#endTime').datetimepicker({
            format:'H:i',
            onShow:function( ct ){
                this.setOptions({
                    minTime:jQuery('#startTime').val()?jQuery('#startTime').val():false
                })
            },
            datepicker:false
        });
    });
});