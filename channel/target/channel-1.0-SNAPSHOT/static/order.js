// UTILITY CODE
function refreshOrderList() {
    resetOrderInput();
    getOrderList();
    document.getElementById("download-error-order").disabled = true;

}
function resetOrderInput() {
    document.getElementById("order-form").reset();
    $("#inputClientId").empty().trigger('change');
    $("#inputChannelId").empty().trigger('change');
    $("#inputCustomerId").empty().trigger('change');
    document.getElementById("add-order").disabled = true;
}
function uploadOrder(event) {
    //Set the values to update
    processData();
    return false;
}

// FILE HANDLING CODE
function processData() {
    var file = $('#orderFile')[0].files[0];
    if (file.name.split('.').pop() == "csv") {
        console.log();
        readFileDataOrder(file, readFileDataCallback);
    } else
        toast(false, "CSV file is required!!");
    return false;
}
function readFileDataOrder(file, callback) {
    var config = {
        header: true,
        delimiter: "\t",
        skipEmptyLines: "greedy",
        complete: function (results) {
            callback(results);
        }
    }
    Papa.parse(file, config);
}
function readFileDataCallback(results) {
    fileData = results.data;
    if (fileData.length > 5000) {
        toast(false, "Limit on number of rows is 5000!!");
    }
    uploadRows();
}
// API CODE
function allocate(orderId) {
    var url = getOrderUrl() + "/allocate/" + orderId;
    $.ajax({
        url: url,
        type: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            getOrderList();
            resetOrderInput();
            toast(true, "Order was succesfully allocated!!");
        },
        error: handleAjaxError
    });
}
function generateInvoice(orderId) {
    var url = getOrderUrl() + "/generateinvoice/" + orderId;
    $.ajax({
        url: url,
        type: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            getOrderList();
            resetOrderInput();
            toast(true, "Order was succesfully allocated!!");
        },
        error: handleAjaxError
    });
}

function uploadRows() {
    //Update progress
    var $form = $("#order-form");

    // updateUploadDialog();

    //Process next row
    var allRows = []
    for (let index = 0; index < fileData.length; index++) {
        var row = fileData[index];
        var row2 = {
            clientSkuId: row["ClientSkuId"],
            orderedQuantity: row["OrderedQuantity"],
            sellingPricePerUnit: row["SellingPricePerUnit"],
        };
        allRows.push(row2);
    }
    var sendJson = toJson($form);
    const obj = JSON.parse(sendJson);
    obj["orderItemForms"] = allRows;
    data = JSON.stringify(obj);
    // console.log(data);
    // return false;
    var url = getOrderUrl();

    //Make ajax call
    $.ajax({
        url: url,
        type: 'POST',
        data: data,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            getOrderList();
            resetOrderInput();
            getOrderList();
            toast(true, "Order was succesfully added!!");
            // uploadRows();
        },
        error: function (response) {
            document.getElementById("download-error-order").disabled = false;
            handleAjaxError(response);
        }
    });

}

function getOrderList() {
    var url = getOrderUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            // console.log(data);
            displayOrderList(data);
        },
        error: handleAjaxError
    });
}
// DISPLAY CODE
function displayOrderList(data) {
    var $tbody = $('#order-table').find('tbody');
    $tbody.empty();
    data.sort(function (a, b) { return a.id - b.id; });
    data.reverse();
    var index = 1;
    for (var i in data) {
        var e = data[i];
        var buttonHtml;
        if (e.status == "CREATED")
            buttonHtml = ` <button type="button" class="btn btn-secondary btn-sm" onclick="downloadInvoice('` + e.channelOrderId + `')" disabled>Download Invoice</button>`
        else if (e.status == "ALLOCATED")
            buttonHtml = ` <button type="button" class="btn btn-secondary btn-sm" onclick="downloadInvoice('` + e.channelOrderId + `')" disabled>Download Invoice</button>`
        else if (e.status == "FULFILLED")
            buttonHtml = ` <button type="button" class="btn btn-secondary btn-sm" onclick="downloadInvoice('` + e.channelOrderId + `')">Download Invoice</button>`

        var row = '<tr>'
            // + '<td><a href="http://localhost:9001/channel/ui/orderpreview/' + e.id + '">' + e.id + '</a></td>'
            + '<td>' + index++ + '</a></td>'
            + '<td>' + e.clientName + '</td>'
            + '<td>' + e.customerName + '</td>'
            + '<td>' + e.channelName + '</td>'
            + '<td>' + e.channelOrderId + '</td>'
            + '<td>' + e.status + '</td>'
            + '<td>' + buttonHtml + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
}
//INITIALIZATION CODE
function init() {
    $('#inputClientId').select2({
        allowClear: true,
        ajax: {
            url: getClientUrl() + "/client/",
            dataType: 'json',

            data: function (params) {
                var query = {
                    term: params.term,
                    type: 'query'
                }
                return query;
            },
            processResults: function (data) {
                var data = $.map(data, function (obj) {
                    obj.text = "ID: " + obj.id + " Name: " + obj.name; // replace name with the property used for the text
                    return obj;
                });
                return { results: data };
            }
        }
    });
    $('#inputCustomerId').select2({
        allowClear: true,
        ajax: {
            url: getClientUrl() + "/customer/",
            dataType: 'json',

            data: function (params) {
                var query = {
                    term: params.term,
                    type: 'query'
                }
                return query;
            },
            processResults: function (data) {
                var data = $.map(data, function (obj) {
                    obj.text = "ID: " + obj.id + " Name: " + obj.name; // replace name with the property used for the text
                    return obj;
                });
                return { results: data };
            }
        }
    });
    $('#inputChannelId').select2({
        allowClear: true,
        ajax: {
            url: getChannelUrl() + "/search/",
            dataType: 'json',

            data: function (params) {
                var query = {
                    term: params.term,
                    type: 'query'
                }
                return query;
            },
            processResults: function (data) {
                var data = $.map(data, function (obj) {
                    obj.text = "ID: " + obj.id + " Name: " + obj.name; // replace name with the property used for the text
                    return obj;
                });
                return { results: data };
            }
        }
    });

    $('#order-form').submit(uploadOrder);
    $('#refresh-order-data').click(refreshOrderList);

    document.getElementById('orderFile').addEventListener('input', function (evt) {
        var file = $('#orderFile')[0].files[0];
        if (file.name != null) {
            document.getElementById("add-order").disabled = false;
        }
    });
}
$(document).ready(init);
$(document).ready(getOrderList);

