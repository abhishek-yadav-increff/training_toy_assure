// GLOBAL VARIABLES

// UTILITY CODE
function getOrderId() {
    var tempArr = document.getElementById('orderIdSelect').innerHTML.split(" ");

    return tempArr[tempArr.length - 1];
}
// API CALLS
function getOrder() {
    var orderId = getOrderId();
    var url = getOrderUrl() + "/" + orderId;
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayOrder(data);
        },
        error: handleAjaxError
    });
}
function getOrderItemList() {
    var orderId = getOrderId();
    var url = getOrderItemUrl() + "/" + orderId;
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayOrderItemList(data);
        },
        error: handleAjaxError
    });
}
function allocate(orderId) {
    var url = getOrderUrl() + "/allocate/" + orderId;
    $.ajax({
        url: url,
        type: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            getOrder();
            getOrderItemList();
            toast(true, "Order was succesfully allocated!!");
        },
        error: handleAjaxError
    });
}
// DISPLAY CODE
function displayOrder(data) {
    var $tbody = $('#order-preview-table').find('tbody');
    $tbody.empty();
    console.log(data);
    var buttonHtml;
    if (data.status == "CREATED")
        buttonHtml = ' <button type="button" class="btn btn-secondary btn-sm" onclick="allocate(' + data.id + ')">Allocate</button>'
    else if (data.status == "ALLOCATED")
        buttonHtml = ' <button type="button" class="btn btn-secondary btn-sm" onclick="generateInvoice(' + data.id + ')">Generate Invoice</button>'
    else if (data.status == "FULFILLED")
        buttonHtml = ' <button type="button" class="btn btn-secondary btn-sm" onclick="downloadInvoice(' + data.id + ')">Download Invoice</button>'

    var row = '<tr>'
        + '<td>' + data.id + '</td>'
        + '<td>' + data.clientId + '</td>'
        + '<td>' + data.customerId + '</td>'
        + '<td>' + data.channelId + '</td>'
        + '<td>' + data.channelOrderId + '</td>'
        + '<td>' + data.status + '</td>'
        + '<td>' + buttonHtml + '</td>'
        + '</tr>';
    $tbody.append(row);
}
function displayOrderItemList(data) {
    var $tbody = $('#order-item-preview-table').find('tbody');
    $tbody.empty();
    data.sort(function (a, b) { return a.id - b.id; });
    data.reverse();
    var index = 1;
    for (var i in data) {
        var e = data[i];

        var row = '<tr>'
            + '<td>' + index + '</td>'
            + '<td>' + e.globalSkuId + '</td>'
            + '<td>' + e.orderedQuantity + '</td>'
            + '<td>' + e.allocatedQuantity + '</td>'
            + '<td>' + e.fulfilledQuantity + '</td>'
            + '<td>' + e.sellingPricePerUnit + '</td>'
            + '</tr>';
        index++;
        $tbody.append(row);
    }
}

// INITIALIZATION CODE
$(document).ready(getOrder);
$(document).ready(getOrderItemList);

