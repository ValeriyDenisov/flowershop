<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error page</title>
</head>
<body>
<h1>ERROR!</h1>
<c:if test="${not empty msg}">
    <h3>${msg}</h3>
</c:if>
<c:if test="${empty msg}">
    <h3>Something wrong, please contact with administrator</h3>
</c:if>
</body>
</html>
