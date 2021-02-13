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
    <title>Records</title>
<%--    <script defer src="script.js"></script>--%>
</head>
<body>

<c:forEach items="${records}" var="record">
    <div class="record-table">
        <p class="record-id"><c:out value="${record.id}" /></p>
        <p><c:out value="${record.user.firstName}" /></p>
        <p><c:out value="${record.user.lastName}" /></p>
        <p><c:out value="${record.userMaster.firstName}" /></p>
        <p><c:out value="${record.userMaster.lastName}" /></p>
        <p><c:out value="${record.service.name}" /></p>
        <p class="status-id"><c:out value="${record.status_id}" /></p>
        <p><c:out value="${record.time}" /></p>
        <a href="${pageContext.request.contextPath}/records/edit?id=${record.id}">Details</a>
        <hr>
    </div>
</c:forEach>
<%--<script defer>--%>

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
<%--</script>--%>
</body>
</html>
