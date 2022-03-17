// UTILITY CODE
function toggleAddBinForm() {
    $('#add-bin-modal').modal('toggle');

}
// DISPLAY CODE

// API CALL CODE
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
            console.log(response);
            $.toast({
                heading: 'Success',
                text: "Successfully created bins! From: " + response.smIndex + " To: " + response.bgIndex,
                allowToastClose: true,
                position: 'top-right',
                icon: 'success'
            });
        },
        error: handleAjaxError
    });

    return false;
}

function getBinList() {

}
// FILE HANDLING CODE


//INITIALIZATION CODE
function init() {
    $('#display-add-bin').click(toggleAddBinForm);
    $('#bin-add-form').submit(addBins);
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
