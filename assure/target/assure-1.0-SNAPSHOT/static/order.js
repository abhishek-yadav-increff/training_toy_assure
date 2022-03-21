// UTILITY CODE
function uploadOrder(event) {
    //Set the values to update
    var clientId = document.getElementById("uploadClientId").value;
    var customerId = document.getElementById("uploadCustomerId").value;
    var channelOrderId = document.getElementById("uploadChannelOrderId").value;
    processData(clientId, customerId, channelOrderId);
    return false;
}
function processData(clientId, customerId, channelOrderId) {
    var file = $('#orderFile')[0].files[0];
    if (file.name.split('.').pop() == "csv") {
        console.log(clientId, customerId, channelOrderId);
        readFileDataOrder(file, readFileDataCallback, clientId, customerId, channelOrderId);
    } else
        toast(false, "CSV file is required!!");
    return false;
}
function readFileDataOrder(file, callback, clientId, customerId, channelOrderId) {
    var config = {
        header: true,
        delimiter: "\t",
        skipEmptyLines: "greedy",
        complete: function (results) {
            callback(results, clientId, customerId, channelOrderId);
        }
    }
    Papa.parse(file, config);
}
function readFileDataCallback(results, clientId, customerId, channelOrderId) {
    fileData = results.data;
    if (fileData.length > 5000) {
        toast(false, "Limit on number of rows is 5000!!");
    }
    console.log(clientId, customerId, channelOrderId);

    uploadRows(clientId, customerId, channelOrderId);
}
function uploadRows(clientId, customerId, channelOrderId) {
    //Update progress

    // updateUploadDialog();

    //Process next row
    var allRows = []
    for (let index = 0; index < fileData.length; index++) {
        var row = fileData[index];
        var row2 = {
            clientSkuId: row["ClientSkuId"],
            orderQuantity: row["OrderQuantity"],
            sellingPricePerUnit: row["SellingPricePerUnit"],
            clientId: clientId,
            customerId: customerId,
            channelOrderId: channelOrderId
        };
        allRows.push(row2);
    }

    var json = JSON.stringify(allRows);
    // console.log(json);
    // return false;
    var url = getOrderUrl() + "/batch";

    //Make ajax call
    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            getOrderList();
            // resetInputChannelListing();
            toast(true, "Channel Listings were succesfully added!!");
            // uploadRows();
        },
        error: function (response) {
            // getChannelListingList();
            toast(false, 'No data was added!');
            // var jsonError = JSON.parse(response.responseText)
            // row.Error = jsonError.message
            // row.Row = processCount

            // errorData.push(row);
            // uploadRows();
        }
    });

}
// API CODE
function addOrder() {

    var $form = $("#order-form");
    // console.log("before toJson");
    var json = toJson($form);
    // console.log("after toJson");
    // return false;
    var url = getOrderUrl();
    console.log(json);
    // return false;

    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            getOrderList();
            toast(true, "Successfully added order!!");
            resetOrderInput();
        },
        error: handleAjaxError
    });

    return false;
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
        var buttonHtml = ' <button type="button" class="btn btn-secondary btn-sm" onclick="displayEditOrder(' + e.id + ')">Edit</button>'
        var row = '<tr>'
            + '<td>' + e.id + '</td>'
            + '<td>' + e.clientId + '</td>'
            + '<td>' + e.customerId + '</td>'
            + '<td>' + e.channelId + '</td>'
            + '<td>' + e.channelOrderId + '</td>'
            + '<td>' + e.status + '</td>'
            + '<td>' + buttonHtml + '</td>'
            + '</tr>';
        index++;
        $tbody.append(row);
    }
}
//INITIALIZATION CODE
function init() {
    $('#uploadClientId').select2({
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
    $('#uploadCustomerId').select2({
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

    $('#order-form').submit(addOrder);
    $('#order-upload-form').submit(uploadOrder);
    // $('#refresh-order-data').click(refreshOrderList);

    document.getElementById('orderFile').addEventListener('input', function (evt) {
        var file = $('#orderFile')[0].files[0];
        if (file.name != null) {
            document.getElementById("upload-order").disabled = false;
        }
    });
}
$(document).ready(init);
$(document).ready(getOrderList);

