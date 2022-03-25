// GLOBAL VARIABLES FOR STATE
var currentZone = null;

//HELPER METHOD
function toJson($form) {
    var serialized = $form.serializeArray();

    var s = '';
    var data = {};
    for (s in serialized) {
        data[serialized[s]['name']] = serialized[s]['value']
    }
    var json = JSON.stringify(data);
    return json;
}

function handleAjaxError(response) {
    var response = JSON.parse(response.responseText);
    if (response != null) {
        toast(false, response.message);
    }
}

function readFileData(file, callback) {
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


function writeFileData(arr, filename) {
    var config = {
        quoteChar: '',
        escapeChar: '',
        delimiter: "\t"
    };

    var data = Papa.unparse(arr, config);
    var blob = new Blob([data], { type: 'text/tsv;charset=utf-8;' });
    var fileUrl = null;

    if (navigator.msSaveBlob) {
        fileUrl = navigator.msSaveBlob(blob, filename);
    } else {
        fileUrl = window.URL.createObjectURL(blob);
    }
    var tempLink = document.createElement('a');
    tempLink.href = fileUrl;
    tempLink.setAttribute('download', filename);
    tempLink.click();
}
//INITIALIZATION CODE
function init() {
    $('#inputZonedDateTime').on('change', function (e) {
        // Do something
        currentZone = document.getElementById("inputZonedDateTime").value;
        var url = getZonedDateTimeUrl() + "/" + encodeURIComponent(currentZone);
        $.ajax({
            url: url,
            type: 'GET',
            success: function (data) {
                console.log(data);
            },
            error: handleAjaxError
        });
    });
    $('#inputZonedDateTime').select2({
        allowClear: true,
        ajax: {
            url: getZonedDateTimeUrl() + "/search/",
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
                    obj.id = obj.offset;
                    obj.text = "Zone: " + obj.zoneDateTime + " Offset: " + obj.offset; // replace name with the property used for the text
                    return obj;
                });
                return { results: data };
            }
        }
    });
}
// UTILITY CODE
function toast(successState, message) {
    if (successState == true) {
        $.toast({
            heading: 'Success',
            text: message,
            showHideTransition: 'slide',
            hideAfter: 3000,
            allowToastClose: true,
            position: 'top-right',
            icon: 'success'
        });
    } else {
        $.toast({
            heading: 'Failure',
            text: message,
            hideAfter: false,
            allowToastClose: true,
            position: 'top-right',
            icon: 'error'
        });
    }
}
function base64ToArrayBuffer(base64) {
    var binaryString = window.atob(base64);
    var binaryLen = binaryString.length;
    var bytes = new Uint8Array(binaryLen);
    for (var i = 0; i < binaryLen; i++) {
        var ascii = binaryString.charCodeAt(i);
        bytes[i] = ascii;
    }
    return bytes;
}
function saveByteArray(reportName, byte) {
    var blob = new Blob([byte], { type: "application/pdf" });
    var link = document.createElement('a');
    link.href = window.URL.createObjectURL(blob);
    var fileName = reportName;
    link.download = fileName;
    link.click();
};
function downloadInvoice(orderId) {
    var url = getOrderUrl() + "/download/" + orderId;
    $.ajax({
        url: url,
        type: 'GET',
        success: function (data) {
            var sampleArr = base64ToArrayBuffer(data);
            saveByteArray("Invoice_" + orderId, sampleArr);
        },
        error: handleAjaxError
    });
}
// API ENDPOINTS
function getBrandUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/brand";
}
function getClientUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/client";
}
function getProductUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/product";
}
function getBinUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/bin";
}
function getChannelUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/channel";
}
function getChannelListingUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/channellisting";
}
function getBinSkuUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/binSku";
}
function getOrderUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/order";
}
function getOrderItemUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/orderitem";
}
function getZonedDateTimeUrl() {
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    return baseUrl + "/api/zoneddatetime";
}

$(document).ready(init);