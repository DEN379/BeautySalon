
var recordTable = document.querySelector(".record-table");
var status = recordTable.querySelector(".status-id");
//var acceptButton = recordTable.querySelector(".accept-button");
var recordId = recordTable.querySelector(".record-id");

var acceptButton2 = document.querySelector(".accept-button");
// acceptButton.onsubmit = function (e) {
//
//     e.preventDefault();
acceptButton2.onclick = myfunc;
function myfunc() {
    //var what = document.querySelector(that);
    console.log("zaiwlo ez");
    console.log(this);

    // var httpRequest;
    // if (window.XMLHttpRequest) {
    //     httpRequest = new XMLHttpRequest();
    // } else if (window.ActiveXObject) { // IE
    //     httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
    // }
    //
    // httpRequest.open('GET', 'http://lacalhost:8080/salon/records/set?id='+recordId, true);
    // httpRequest.send(null);
    //
    // if (httpRequest.readyState == 4) {
    //     status.innerHTML = "2";
    // } else {
    //     status.innerHTML = "???";
    // }
    console.log(what);
}
console.log("ne zaiwlo(((((");
