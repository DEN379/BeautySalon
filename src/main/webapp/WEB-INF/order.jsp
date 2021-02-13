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
    <title>Order</title>
</head>
<body>
<select>
    <option>Select service to choose master</option>
    <c:forEach items="${services}" var="service">
        <option><c:out value="${service.name}" /></option>
    </c:forEach>
</select>
<select>
    <option>Select master for service</option>
    <c:forEach items="${masters}" var="master">
        <option><c:out value="${master.user.firstName}" /> <c:out value="${master.user.lastName}" /></option>
    </c:forEach>
</select>

</body>
</html>
