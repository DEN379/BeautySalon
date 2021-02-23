<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="navbar" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
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
<style>
    fieldset *{
        margin-bottom: 20px;
        margin-left: 41px;
    }
    .form-group input{
        width: 500px;
    }
</style>
<navbar:navbar/>
<div class="container">
    <form accept-charset="UTF-8" role="form" method="post" action="${pageContext.request.contextPath}/login">
        <fieldset>
            <div class="form-group">
                <label for="email">Email</label>
                <input class="form-control" name="email" type="email" id="email" required>
            </div>

            <div class="form-group">
                <label for="password"><fmt:message key="password" bundle="${bundle}"/></label>
                <input class="form-control" name="password" type="password" id="password" required>
            </div>

            <input class="btn btn-lg btn-success btn-block" type="submit" />
        </fieldset>
    </form>
</div>
</body>
</html>