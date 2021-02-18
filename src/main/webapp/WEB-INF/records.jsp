<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <title>Records</title>
</head>
<body>
<div class="container">
<table class="table table-hover">
    <thead>
    <tr>
        <th scope="col">Record id#</th>
        <th scope="col">User name</th>
        <th scope="col">Master name</th>
        <th scope="col">Service</th>
        <th scope="col">Status</th>
        <th scope="col">Date and time</th>
        <th scope="col"></th>
    </tr>
    </thead>
    <tbody>
        <c:forEach items="${records}" var="record">
            <tr>
                <th scope="row"><c:out value="${record.id}" /></th>
                <td><c:out value="${record.user.firstName}" />
                    <c:out value="${record.user.lastName}" /></td>
                <td><c:out value="${record.userMaster.firstName}" />
                    <c:out value="${record.userMaster.lastName}" /></td>
                <td><c:out value="${record.service.name}" /></td>
                <td><c:out value="${record.status.value()}" /></td>
                <td><c:out value="${record.time}" /></td>
                <td><a href="${pageContext.request.contextPath}/records/edit?id=${record.id}">Details</a></td>
            </tr>
        </c:forEach>
    </tbody>
</table>

    <div id="pages"><c:out value="${pages}" /></div>
</div>
<script defer>

    document.addEventListener("DOMContentLoaded", load);
    function load() {

        document.getElementById("pages").innerHTML = `${pages}`;
        // var xhttp = new XMLHttpRequest();
        // xhttp.onreadystatechange = function() {
        //     if (this.readyState == 4 && this.status == 200) {
        //         document.getElementById("pages").innerHTML = this.responseText;
        //     }
        // };
        // xhttp.open("GET", "/salon/records?page=1" , true);
        // xhttp.send();
    }


<%--    var table = document.querySelector(".record-table");--%>
<%--    var acceptButton = table.querySelectorAll(".accept-button");--%>

<%--    for (let i = 0; i < acceptButton.length; i++) {--%>
<%--        console.log(acceptButton[i]);--%>
<%--        acceptButton[i].addEventListener('click', myfunc);--%>
<%--    }--%>
<%--    //acceptButton.onclick = myfunc;--%>
<%--    function myfunc(e) {--%>
<%--        console.log(e.target);--%>
<%--        var status = e.target.parentNode.querySelector(".status-id");--%>
<%--        var recordId = e.target.parentNode.querySelector(".record-id");--%>
<%--        console.log(status.innerHTML)--%>
<%--        console.log(recordId.innerHTML)--%>


<%--        // var httpRequest;--%>
<%--        // if (window.XMLHttpRequest) {--%>
<%--        //     httpRequest = new XMLHttpRequest();--%>
<%--        // } else if (window.ActiveXObject) { // IE--%>
<%--        //     httpRequest = new ActiveXObject("Microsoft.XMLHTTP");--%>
<%--        // }--%>
<%--        // var url = 'http://localhost:8080/salon/records/set?id='+recordId.innerHTML;--%>
<%--        // console.log(url);--%>
<%--        //--%>
<%--        // httpRequest.open('GET', url, true);--%>
<%--        // httpRequest.onreadystatechange = function() {--%>
<%--        //     console.log(this);--%>
<%--        //     var statusNew = this.querySelector(".status-id");--%>
<%--        //     if(httpRequest.readyState === XMLHttpRequest.DONE && httpRequest.status === 200) {--%>
<%--        //         statusNew.innerHTML = "2";--%>
<%--        //         console.log("YA POMINYAV");--%>
<%--        //         acceptButton2.style.display = "none";--%>
<%--        //     } else {--%>
<%--        //         console.log(httpRequest.readyState);--%>
<%--        //         console.log(httpRequest.status);--%>
<%--        //     }--%>
<%--        // };--%>
<%--        // httpRequest.send(null);--%>

<%--        //status.innerHTML = "???";--%>

<%--    }--%>
<%--    console.log("ne zaiwlo(((((");--%>
</script>
</body>
</html>
