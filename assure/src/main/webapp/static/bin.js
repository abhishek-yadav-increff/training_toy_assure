// UTILITY CODE
function toggleAddBinForm() {
    $('#add-bin-modal').modal('toggle');
}
function resetOnSuccess() {
    // Reset Form
    document.getElementById("bin-sku-form").reset();
    $("#inputClientId").empty().trigger('change');
    // Reset File Data
    processCount = 0;
    fileData = [];
    errorData = [];
    //Reset various counts
    //Update counts	
    // updateUploadDialog();
    document.getElementById("download-error-bin").disabled = true;
    document.getElementById("add-bin-sku").disabled = true;
}
function downloadErrors() {
    writeFileData(errorData, "binsku_error.tsv");
}
// DISPLAY CODE
function displayBinSkuList(data) {
    var $tbody = $('#bin-sku-table').find('tbody');
    $tbody.empty();
    data.sort(function (a, b) { return a.id - b.id; });
    data.reverse();
    for (var i in data) {
        var e = data[i];

        // var buttonHtml = ' <button type="button" class="btn btn-secondary btn-sm" onclick="displayEditBinSku(' + e.id + ')">Edit</button>'

        var row = '<tr>'
            + '<td>' + e.id + '</td>'
            + '<td>' + e.binId + '</td>'
            + '<td>' + e.globalSkuId + '</td>'
            + '<td>' + e.quantity + '</td>'
            // + '<td>' + buttonHtml + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
}
function displayBin(data) {
    if (data.smIndex == 10000 || data.smIndex == "10000")
        document.getElementById("currentBins").innerHTML = "No Available Bins";
    if (data.smIndex == data.bgIndex)
        document.getElementById("currentBins").innerHTML = "Available Bins: [" + data.smIndex + "]";
    else
        document.getElementById("currentBins").innerHTML = "Available Bins: [" + data.smIndex + "," + data.bgIndex + "]";

}
// API CALL CODE
function getBin() {
    var url = getBinUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayBin(data);
        },
    });
}
function getBinSkuList() {
    var url = getBinSkuUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayBinSkuList(data);
        },
        error: handleAjaxError
    });
}
function addBinSku() {
    document.getElementById("download-error-bin").disabled = true;
    var clientId = document.getElementById("inputClientId").value;
    processData(clientId);
    return false;
}
function addBins() {
    var $form = $("#bin-add-form");
    var json = toJson($form);
    var url = getBinUrl();

    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            toggleAddBinForm();
            getBinList();
            getBin();
            console.log(response);
            if (response.smIndex != response.bgIndex)
                toast(true, "Successfully created bins! From: " + response.smIndex + " To: " + response.bgIndex);
            else
                toast(true, "Successfully created bin! Bin ID: " + response.smIndex)
        },
        error: handleAjaxError
    });

    return false;
}

// FILE HANDLING CODE

function processData(clientId) {
    // console.log("in process data, clientId: ", clientId);
    // return false;
    var file = $('#binSkuFile')[0].files[0];
    if (file.name.split('.').pop() == "csv") {
        console.log(clientId);
        readFileDataBinSku(file, readFileDataCallback, clientId);
    } else
        toast(false, "CSV file is required!!");
    return false;
}
function readFileDataCallback(results, clientId) {
    fileData = results.data;
    if (fileData.length > 5000) {
        toast(false, "Limit on number of rows is 5000!!");
    }
    console.log(clientId);

    uploadRows(clientId);
}
function readFileDataBinSku(file, callback, clientId) {
    var config = {
        header: true,
        delimiter: "\t",
        skipEmptyLines: "greedy",
        complete: function (results) {
            callback(results, clientId);
        }
    }
    Papa.parse(file, config);
}
function uploadRows(clientId) {
    console.log("inside uploadRows");
    // return false;
    //Process next row
    var allRows = []
    for (let index = 0; index < fileData.length; index++) {
        var row = fileData[index];
        var row2 = {
            clientSkuId: row["ClientSkuId"],
            binId: row["BinId"],
            quantity: row["Quantity"],
            clientId: clientId,
        };
        if (row2["clientSkuId"] && row2["quantity"] && row2["binId"])
            allRows.push(row2);
        else {
            toast(false, "Please make sure that the desired fields are present!!");
            return false;
        }
    }
    var json = JSON.stringify(allRows);
    var url = getBinSkuUrl();
    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            getBinSkuList();
            resetOnSuccess();
            toast(true, "BinSku were succesfully added!!");
        },
        error: function (response) {
            errorData = handleAjaxError(response);
            console.log(errorData);
            document.getElementById("download-error-bin").disabled = false;

        }
    });

}

//INITIALIZATION CODE
function init() {
    $('#inputClientId').select2({
        allowClear: true,
        ajax: {
            url: getClientUrl() + "/",
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
    $('#display-add-bin').click(toggleAddBinForm);
    $('#bin-add-form').submit(addBins);
    $('#refresh-bin-sku-data').click(resetOnSuccess);
    $('#bin-sku-form').submit(addBinSku);
    $('#download-error-bin').click(downloadErrors);

    // $('#process-data').click(processData);
    // $('#download-errors').click(downloadErrors);
    // $('#productFile').on('change', updateFileName);


    document.getElementById('binSkuFile').addEventListener('change', function (evt) {
        var file = $('#binSkuFile')[0].files[0];
        if (file.name != null)
            document.getElementById("add-bin-sku").disabled = false;
        else
            document.getElementById("add-bin-sku").disabled = true;

    });
}

$(document).ready(init);
$(document).ready(getBin);
$(document).ready(getBinSkuList);


