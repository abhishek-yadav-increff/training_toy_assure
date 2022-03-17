// Utility
function refreshProductList() {
    getProductList();
    toast(true, "Refreshed!");
    resetInputProduct();
}

function resetInputProduct() {
    document.getElementById("product-form").reset();
    $("#inputClientId").empty().trigger('change');
    resetUploadDialog();
}
function addProduct(event) {
    //Set the values to update
    var clientId = document.getElementById("inputClientId").value;
    processData(clientId);
    return false;
}
//VALIDATION

// API CALLS

function updateProduct(event) {

    //Get the ID
    var id = $("#product-edit-form input[name=globalSkuId]").val();
    var url = getProductUrl() + "/" + id;

    //Set the values to update
    var $form = $("#product-edit-form");
    var json = toJson($form);
    $.ajax({
        url: url,
        type: 'PUT',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            $('#edit-product-modal').modal('toggle');
            getProductList();
            toast(true, 'Successfully updated product!');
        },
        error: handleAjaxError
    });
    return false;
}

function getProductList() {
    var url = getProductUrl();
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayProductList(data);
        },
        error: handleAjaxError
    });
}


// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(clientId) {
    var file = $('#productFile')[0].files[0];
    if (file.name.split('.').pop() == "csv")
        readFileDataProduct(file, readFileDataCallback, clientId);
    else
        toast(false, "CSV file is required!!");
    return false;
}

function readFileDataCallback(results, clientId) {
    fileData = results.data;
    if (fileData.length > 5000) {
        toast(false, "Limit on number of rows is 5000!!");
    }
    uploadRows(clientId);
}

function uploadRows(clientId) {
    //Update progress

    updateUploadDialog();
    if (processCount == fileData.length) {
        if (errorData.length == 0) {
            // displayUploadData();
            getProductList();
            resetInputProduct();
            // resetUploadDialog();
            toast(true, 'All files successfully added!');
        } else if (errorData.length == processCount) {
            getProductList();
            // document.getElementById("download-errors").disabled = false;
            toast(false, 'No data was added!');

        } else {
            // document.getElementById("download-errors").disabled = false;
            toast(false, 'Only some rows were added!');
        }
        return;
    }
    //If everything processed then return
    if (processCount == fileData.length) {
        return;
    }
    // console.log(processCount);

    //Process next row
    var row = fileData[processCount];
    var row2 = {
        clientSkuId: row["ClientSkuId"],
        brandId: row["BrandId"],
        mrp: row["MRP"],
        name: row["Name"],
        description: row["Description"],
        clientId: clientId
    }
    processCount++;

    var json = JSON.stringify(row2);
    var url = getProductUrl();

    //Make ajax call
    $.ajax({
        url: url,
        type: 'POST',
        data: json,
        headers: {
            'Content-Type': 'application/json'
        },
        success: function (response) {
            uploadRows();
        },
        error: function (response) {
            var jsonError = JSON.parse(response.responseText)
            row.Error = jsonError.message
            row.Row = processCount

            errorData.push(row);
            uploadRows();
        }
    });

}

function readFileDataProduct(file, callback, clientId) {
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

function downloadErrors() {
    writeFileData(errorData, "product_error.tsv");
}


function resetUploadDialog() {
    //Reset various counts
    processCount = 0;
    fileData = [];
    errorData = [];
    //Update counts	
    updateUploadDialog();
    document.getElementById("add-product").disabled = true;

}
//UI DISPLAY METHODS

function displayProductList(data) {
    var $tbody = $('#product-table').find('tbody');
    $tbody.empty();
    data.sort(function (a, b) { return a.id - b.id; });
    data.reverse();
    for (var i in data) {
        var e = data[i];

        var buttonHtml = ' <button type="button" class="btn btn-secondary btn-sm" onclick="displayEditProduct(' + e.globalSkuId + ')">Edit</button>'

        var row = '<tr>'
            + '<td>' + e.globalSkuId + '</td>'
            + '<td>' + e.clientSkuId + '</td>'
            + '<td>' + e.clientId + '</td>'
            + '<td>' + e.name + '</td>'
            + '<td>' + e.brandId + '</td>'
            + '<td>' + e.mrp.toFixed(2) + '</td>'
            + '<td>' + e.description + '</td>'
            + '<td>' + buttonHtml + '</td>'
            + '</tr>';
        $tbody.append(row);
    }
}

function displayEditProduct(id) {
    var url = getProductUrl() + "/" + id;
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            displayProductEditModal(data);
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
//     var $file = $('#productFile');
//     var fileName = $file.val();
//     fileName = fileName.substring(fileName.lastIndexOf('\\') + 1)
//     $('#productFileName').html(fileName);
// }

function displayUploadData() {
    resetUploadDialog();
    $('#upload-product-modal').modal('toggle');
}

function displayProductEditModal(data) {
    $("#product-edit-form input[name=globalSkuId]").val(data.globalSkuId);
    $("#product-edit-form input[name=name]").val(data.name);
    $("#product-edit-form input[name=brandId]").val(data.brandId);
    $("#product-edit-form input[name=mrp]").val(data.mrp);
    $("#product-edit-form input[name=description]").val(data.description);
    document.getElementById('inputEditGlobalSkuId').innerHTML = data.globalSkuId;
    $('#edit-product-modal').modal('toggle');
}

//INITIALIZATION CODE
function init() {
    $('.js-data-example-ajax').select2({
        allowClear: true,
        ajax: {
            url: getClientUrl() + "/search/",
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

    $('#product-form').submit(addProduct);
    $('#product-edit-form').submit(updateProduct);
    $('#refresh-product-data').click(refreshProductList);
    // $('#upload-data').click(displayUploadData);
    // $('#process-data').click(processData);
    // $('#download-errors').click(downloadErrors);
    // $('#productFile').on('change', updateFileName);

    document.getElementById('productFile').addEventListener('input', function (evt) {
        var file = $('#productFile')[0].files[0];
        if (file.name != null) {
            document.getElementById("add-product").disabled = false;
        }
    });
}

$(document).ready(init);

$(document).ready(getProductList);
