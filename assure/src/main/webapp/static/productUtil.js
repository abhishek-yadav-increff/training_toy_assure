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