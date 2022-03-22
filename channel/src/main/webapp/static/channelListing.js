// Utility
function refreshChannelListingList() {
    getChannelListingList();
    toast(true, "Refreshed!");
    resetInputChannelListing();
    document.getElementById("download-error-channel-listing").disabled = true;
}

function resetInputChannelListing() {
    document.getElementById("channel-listing-form").reset();
    $("#inputClientId").empty().trigger('change');
    $("#inputChannelId").empty().trigger('change');
    resetUploadDialog();
}
function addChannelListing(event) {
    //Set the values to update
    var clientId = document.getElementById("inputClientId").value;
    var channelId = document.getElementById("inputChannelId").value;
    processData(clientId, channelId);
    return false;
}

//VALIDATION

// API CALLS

function updateChannelListing(event) {

    //Get the ID
    var id = $("#channel-listing-edit-form input[name=globalSkuId]").val();
    var url = getChannelListingUrl() + "/" + id;

    //Set the values to update
    var $form = $("#channel-listing-edit-form");
    var json = toJson($form);
    $.ajax({
        url: url,
        type: 'PUT',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            $('#edit-channel-listing-modal').modal('toggle');
            getChannelListingList();
            toast(true, 'Successfully updated Channel Listing!!');
        },
        error: handleAjaxError
    });
    return false;
}

function getChannelListingList() {
    var url = getChannelListingUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayChannelListingList(data);
        },
        error: handleAjaxError
    });
}


// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(clientId, channelId) {
    var file = $('#channelListingFile')[0].files[0];
    if (file.name.split('.').pop() == "csv") {
        console.log(clientId, channelId);
        readFileDataChannelListing(file, readFileDataCallback, clientId, channelId);
    } else
        toast(false, "CSV file is required!!");
    return false;
}

function readFileDataCallback(results, clientId, channelId) {
    fileData = results.data;
    if (fileData.length > 5000) {
        toast(false, "Limit on number of rows is 5000!!");
    }
    console.log(clientId, channelId);

    uploadRows(clientId, channelId);
}

function uploadRows(clientId, channelId) {
    //Update progress
    updateUploadDialog();

    //Process next row
    var allRows = []
    for (let index = 0; index < fileData.length; index++) {
        var row = fileData[index];
        var row2 = {
            clientSkuId: row["ClientSkuId"],
            channelSkuId: row["ChannelSkuId"],
            clientId: clientId,
            channelId: channelId
        };
        allRows.push(row2);
    }

    var json = JSON.stringify(allRows);
    var url = getChannelListingUrl();
    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            getChannelListingList();
            resetInputChannelListing();
            toast(true, "Channel Listings were succesfully added!!");
            // uploadRows();
        },
        error: function (response) {
            var response = JSON.parse(response.responseText);
            errorData = response.errorDatas;
            document.getElementById("download-error-channel-listing").disabled = false;
            toast(false, 'No data was added!');
        }
    });

}

function readFileDataChannelListing(file, callback, clientId, channelId) {
    var config = {
        header: true,
        delimiter: "\t",
        skipEmptyLines: "greedy",
        complete: function (results) {
            callback(results, clientId, channelId);
        }
    }
    Papa.parse(file, config);
}

function downloadErrors() {
    console.log(errorData);
    writeFileData(errorData, "channel_listing_error.tsv");
}


function resetUploadDialog() {
    //Reset various counts
    processCount = 0;
    fileData = [];
    errorData = [];
    //Update counts	
    updateUploadDialog();
    document.getElementById("add-channel-listing").disabled = true;

}
//UI DISPLAY METHODS

function displayChannelListingList(data) {
    var $tbody = $('#channel-listing-table').find('tbody');
    $tbody.empty();
    data.sort(function (a, b) { return a.id - b.id; });
    data.reverse();
    for (var i in data) {
        var e = data[i];

        var buttonHtml = ' <button type="button" class="btn btn-secondary btn-sm" onclick="displayEditChannelListing(' + e.id + ')">Edit</button>'

        var row = '<tr>'
            + '<td>' + e.id + '</td>'
            + '<td>' + e.channelId + '</td>'
            + '<td>' + e.channelSkuId + '</td>'
            + '<td>' + e.clientId + '</td>'
            + '<td>' + e.globalSkuId + '</td>'
            + '<td>' + buttonHtml + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
}

function displayEditChannelListing(id) {
    var url = getChannelListingUrl() + "/" + id;
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayChannelListingEditModal(data);
        },
        error: handleAjaxError
    });
}

function updateUploadDialog() {
    $('#rowCount').html("" + fileData.length);
    $('#processCount').html("" + processCount);
    $('#errorCount').html("" + errorData.length);
}

// function updateFileName() {
//     var $file = $('#channel-listingFile');
//     var fileName = $file.val();
//     fileName = fileName.substring(fileName.lastIndexOf('\\') + 1)
//     $('#channel-listingFileName').html(fileName);
// }

function displayUploadData() {
    resetUploadDialog();
    $('#upload-channel-listing-modal').modal('toggle');
}

function displayChannelListingEditModal(data) {
    $("#channel-listing-edit-form input[name=globalSkuId]").val(data.globalSkuId);
    $("#channel-listing-edit-form input[name=name]").val(data.name);
    $("#channel-listing-edit-form input[name=brandId]").val(data.brandId);
    $("#channel-listing-edit-form input[name=mrp]").val(data.mrp);
    $("#channel-listing-edit-form input[name=description]").val(data.description);
    document.getElementById('inputEditGlobalSkuId').innerHTML = data.globalSkuId;
    $('#edit-channel-listing-modal').modal('toggle');
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

    $('#channel-listing-form').submit(addChannelListing);
    $('#download-error-channel-listing').click(downloadErrors);
    $('#channel-listing-edit-form').submit(updateChannelListing);
    $('#refresh-channel-listing-data').click(refreshChannelListingList);

    document.getElementById('channelListingFile').addEventListener('input', function (evt) {
        var file = $('#channelListingFile')[0].files[0];
        if (file.name != null) {
            document.getElementById("add-channel-listing").disabled = false;
        }
    });
    document.addEventListener('channelListingFile').addEventListener('change', function () {
        document.getElementById("download-error-channel-listing").disabled = true;
    });
}

$(document).ready(init);

$(document).ready(getChannelListingList);
