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
    <title>Order comment</title>
</head>
<body>
<navbar:navbar/>
<h2>Evaluate a service: ${record.service.name}, made by ${record.userMaster.firstName} ${record.userMaster.lastName} </h2>
<form action="${pageContext.request.contextPath}/order/comment?id=${record.id}&master=${record.serviceMaster.master_id}"
      method="post" id="mark-form">
    <input type="radio" class="btn-check" name="mark" value="1" id="option1" autocomplete="off" form="mark-form">
    <label class="btn btn-secondary" for="option1">1</label>

    <input type="radio" class="btn-check" name="mark" value="2" id="option2" autocomplete="off" form="mark-form">
    <label class="btn btn-secondary" for="option2">2</label>

    <input type="radio" class="btn-check" name="mark" value="3" id="option3" autocomplete="off" form="mark-form">
    <label class="btn btn-secondary" for="option3">3</label>

    <input type="radio" class="btn-check" name="mark" value="4" id="option4" autocomplete="off" form="mark-form">
    <label class="btn btn-secondary" for="option4">4</label>

    <input type="radio" class="btn-check" name="mark" value="5" id="option5" autocomplete="off" form="mark-form">
    <label class="btn btn-secondary" for="option5">5</label>

    <input type="submit" value="Leave a comment">
</form>

</body>
</html>
