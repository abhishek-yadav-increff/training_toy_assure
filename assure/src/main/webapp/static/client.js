// UTILITY CODE

function resetClientInput() {
    document.getElementById('inputClient').value = '';
    document.getElementById('inputClientType').value = 'client';
}

function refreshClientList() {
    getClientList();
    toast(true, 'Refreshed!');
    resetClientInput();
}

// API CALL CODE

function addClient() {
    var $form = $("#client-form");
    var json = toJson($form);
    var url = getClientUrl();

    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            getClientList();
            toast(true, "Successfully added client!!");
            resetClientInput();
        },
        error: handleAjaxError
    });

    return false;
}

function getClientList() {
    var url = getClientUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayClientList(data);
        },
        error: handleAjaxError
    });
}

// DISPLAY METHODS

function displayClientList(data) {
    var $tbody = $('#client-table').find('tbody');
    $tbody.empty();
    data.sort(function (a, b) { return a.id - b.id; });
    data.reverse();
    var index = 1;
    for (var i in data) {
        var e = data[i];
        var row = '<tr>'
            + '<td>' + e.id + '</td>'
            + '<td>' + e.name + '</td>'
            + '<td>' + e.userEnum + '</td>'
            + '</tr>';
        index++;
        $tbody.append(row);
    }
}

// INITIALIZATION CODE
function init() {
    $('#client-form').submit(addClient);
    $('#refresh-data').click(refreshClientList);
}

$(document).ready(init);
$(document).ready(getClientList);
