function loadGetMsg() {
    let msg = document.getElementById("msg").value;
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("prestressing").innerHTML = this.responseText;
        }
    };
    xhttp.open("GET", "http://localhost:8080/logs?msg=" + msg, true);
    xhttp.send();
}