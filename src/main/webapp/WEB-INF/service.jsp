<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="navbar" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create service</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
    <c:if test="${sessionScope.locale == null}">
        <fmt:setLocale value="ua"/>
    </c:if>
    <c:if test="${sessionScope.locale != null}">
        <fmt:setLocale value="${sessionScope.locale}"/>
    </c:if>
    <fmt:setBundle basename="localization" var="bundle"/>

</head>
<body>
<navbar:navbar/>
<div class="container">
    <form class="pure-form" accept-charset="UTF-8" role="form" method="post" action="${pageContext.request.contextPath}/admin/createService">
        <fieldset>
            <div class="form-group">
                <label for="name"><fmt:message key="serviceName" bundle="${bundle}"/></label>
                <input class="form-control" name="name" id="name" type="text" required>
            </div>

            <div class="form-group">
                <label for="desc"><fmt:message key="description" bundle="${bundle}"/></label>
                <textarea class="form-control" name="desc" id="desc" required></textarea>
            </div>

            <input class="btn btn-lg btn-success btn-block" type="submit" value="<fmt:message key="createService" bundle="${bundle}"/>">
        </fieldset>
    </form>
</div>
</body>
</html>