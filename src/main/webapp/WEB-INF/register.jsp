<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

</head>
<body>
<div >
    <form class="pure-form" accept-charset="UTF-8" role="form" method="post" action="${pageContext.request.contextPath}/register">
        <fieldset>
            <div class="form-group">
                <input class="form-control" name="fname" type="text" required>
            </div>

            <div class="form-group">
                <input class="form-control" name="lname" type="text" required>
            </div>

            <div class="form-group">
                <input class="form-control" name="email" type="email" required>
            </div>

            <div class="form-group">
                <input class="form-control" name="password"
                       id="password" type="password" required>
            </div>

<%--            <div class="form-group">--%>
<%--                <input class="form-control" name="confirm_password"--%>
<%--                       id="confirm_password" type="password" required>--%>
<%--            </div>--%>

            <input class="btn btn-lg btn-success btn-block" type="submit" >
        </fieldset>
    </form>
</div>
</body>
</html>