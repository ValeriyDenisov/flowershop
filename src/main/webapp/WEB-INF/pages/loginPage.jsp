<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="/login" method="post">
    <label for="login">Login:</label>
    <input type="text" id="login" name="j_username"><br><br>
    <label for="password">Password:</label>
    <input type="text" id="password" name="j_password"><br><br>
    <input type="submit" value="Enter">
    <input type="button" value="Registration" onclick="location.href='/registration'">
</form>
</body>
</html>
