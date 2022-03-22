// UTILITY CODE

function resetChannelInput() {
    document.getElementById('inputName').value = '';
    document.getElementById('inputInvoiceType').value = 'channel';
}

function refreshChannelList() {
    getChannelList();
    toast(true, 'Refreshed!');
    resetChannelInput();
}

// API CALL CODE

function addChannel() {

    var $form = $("#channel-form");
    var json = toJson($form);
    var url = getChannelUrl();

    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            getChannelList();
            toast(true, "Successfully added channel!!");
            resetChannelInput();
        },
        error: handleAjaxError
    });

    return false;
}

function getChannelList() {
    var url = getChannelUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayChannelList(data);
        },
        error: handleAjaxError
    });
}

// DISPLAY METHODS

function displayChannelList(data) {
    var $tbody = $('#channel-table').find('tbody');
    $tbody.empty();
    data.sort(function (a, b) { return a.id - b.id; });
    data.reverse();
    var index = 1;
    for (var i in data) {
        var e = data[i];
        // var buttonHtml = ' <button type="button" class="btn btn-secondary btn-sm" onclick="displayEditBrand(' + e.id + ')">Edit</button>'
        var row = '<tr>'
            + '<td>' + e.id + '</td>'
            + '<td>' + e.name + '</td>'
            + '<td>' + e.invoiceType + '</td>'
            // + '<td>' + buttonHtml + '</td>'
            + '</tr>';
        index++;
        $tbody.append(row);
    }
}

// INITIALIZATION CODE
function init() {

    $('#channel-form').submit(addChannel);
    $('#refresh-data').click(refreshChannelList);
}

$(document).ready(init);
$(document).ready(getChannelList);
