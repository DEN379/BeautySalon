<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="navbar" tagdir="/WEB-INF/tags" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <c:if test="${sessionScope.locale == null}">
        <fmt:setLocale value="ua"/>
    </c:if>
    <c:if test="${sessionScope.locale != null}">
        <fmt:setLocale value="${sessionScope.locale}"/>
    </c:if>

    <fmt:setBundle basename="localization" var="bundle"/>
    <title>My Orders</title>
</head>
<body>
    <navbar:navbar/>
    <div class="container">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th scope="col"><fmt:message key="service" bundle="${bundle}"/></th>
                <th scope="col"><fmt:message key="date" bundle="${bundle}"/></th>
                <th scope="col"><fmt:message key="master" bundle="${bundle}"/></th>
                <th scope="col"><fmt:message key="price" bundle="${bundle}"/></th>
                <th scope="col"><fmt:message key="status" bundle="${bundle}"/></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${records}" var="record">
                <tr>
                    <th><c:out value="${record.service.name}" /></th>
                    <td><c:out value="${record.time}" /></td>
                    <td><c:out value="${record.userMaster.firstName}" /> <c:out value="${record.userMaster.lastName}" /></td>
                    <td><c:out value="${record.serviceMaster.price}" /></td>
                    <td class="status-id"><c:out value="${record.status.value()}" /></td>
                    <td>
                        <c:if test="${record.status_id == 1}">
                        <form action="${pageContext.request.contextPath}/myOrders/paid?id=${record.id}" method="post" >
                            <input type="submit" value="<fmt:message key="pay" bundle="${bundle}"/>" class="btn btn-warning">
                        </form>
                        </c:if>

                        <c:if test="${record.status_id == 4}">
                            <a href="${pageContext.request.contextPath}/order/comment?id=${record.id}" class="btn btn-info">
                                <fmt:message key="leaveComment" bundle="${bundle}"/></a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
