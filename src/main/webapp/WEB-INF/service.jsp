<%@ taglib prefix="navbar" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

</head>
<body>
<navbar:navbar/>
<div class="container">
    <form class="pure-form" accept-charset="UTF-8" role="form" method="post" action="${pageContext.request.contextPath}/admin/createService">
        <fieldset>
            <div class="form-group">
                <label for="name">Service name</label>
                <input class="form-control" name="name" id="name" type="text" required>
            </div>

            <div class="form-group">
                <label for="desc">Description</label>
                <textarea class="form-control" name="desc" id="desc" required></textarea>
<%--                <input class="form-control" name="desc" id="desc" type="text" required>--%>
            </div>

            <input class="btn btn-lg btn-success btn-block" type="submit" value="Create service">
        </fieldset>
    </form>
</div>
</body>
</html>