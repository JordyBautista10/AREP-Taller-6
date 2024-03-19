function loadGetMsg() {
    let msg = document.getElementById("msg").value;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            createTable(JSON.parse(this.responseText));
            //document.getElementById("prestressing").innerHTML = JSON.parse(this.responseText);
        }
    };
    xhttp.open("GET", "http://localhost:8080/logs?msg=" + msg, true);
    xhttp.send();
}

function createTable(data) {
    let tableHtml = "<table><tr><th>#</th><th>Log</th><th>Date</th></tr>";
    for (let i = 0; i < data.length; i++) {
        let date = new Date(data[i].Hour);
        tableHtml += "<tr><td>" + (i+1) + "</td><td>" + data[i].Log + "</td><td>" + date.toLocaleString() + "</td></tr>";
    }
    tableHtml += "</table>";
    document.getElementById("prestressing").innerHTML = tableHtml;
}