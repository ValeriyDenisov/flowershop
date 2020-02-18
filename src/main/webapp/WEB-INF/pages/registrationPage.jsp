<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form:form action="/registration" method="post" id="regist" modelAttribute="registration">
    <table class="table">
            <tr>
                <td>
                    <h3>${registration.error}</h3>
                </td>
            </tr>
        <tr>
            <td><form:label path="name"/>Name</td>
            <td><form:input path="name" id="name"/></td>
            <td><span style="color: red"><form:errors path="name"/></span></td>
        </tr>
        <tr>
            <td><form:label path="secondName"/>Second name</td>
            <td><form:input path="secondName" id="secondName"/></td>
            <td><span style="color: red"><form:errors path="secondName"/></span></td>
        </tr>
        <tr>
            <td><form:label path="fatherName"/>Father name</td>
            <td><form:input path="fatherName" id="fatherName"/></td>
            <td><span style="color: red"><form:errors path="fatherName"/></span></td>
        </tr>
        <tr>
            <td><form:label path="city"/>City</td>
            <td><form:input path="city" id="city"/></td>
            <td><span style="color: red"><form:errors path="city"/></span></td>
        </tr>
        <tr>
            <td><form:label path="street"/>Street</td>
            <td><form:input path="street" id="street"/></td>
            <td><span style="color: red"><form:errors path="street"/></span></td>
        </tr>
        <tr>
            <td><form:label path="code"/>Code</td>
            <td><form:input path="code" id="code"/></td>
            <td><span style="color: red"><form:errors path="code"/></span></td>
        </tr>
        <tr>
            <td><form:label path="building"/>Building</td>
            <td><form:input path="building" id="building"/></td>
            <td><span style="color: red"><form:errors path="building"/></span></td>
        </tr>
        <tr>
            <td><form:label path="phone"/>Phone</td>
            <td>
                 <span style="text-align: right; margin-left: -23px">
                    +7
                </span>
                <form:input path="phone" id="phone" maxlength="10"/></td>
            <td><span style="color: red"><form:errors path="phone"/></span></td>
        </tr>
        <tr>
            <td><form:label path="email"/>Email/Login</td>
            <td><form:input path="email" id="email"/></td>
            <td><span style="color: red"><form:errors path="email"/></span></td>
        </tr>
        <tr>
            <td><form:label path="password"/>Password</td>
            <td><form:password path="password" id="password"/></td>
            <td><span style="color: red"><form:errors path="password"/></span></td>
        </tr>
        <tr>
            <td><form:label path="confirmPassword"/>Confirm password</td>
            <td><form:password path="confirmPassword" id="confirmPassword"/></td>
            <td><span style="color: red"><form:errors path="confirmPassword"/></span></td>
        </tr>

        <tr>
            <td colspan="2">
                <button type="submit" class="btn btn-success btn-lg btn-block"
                        style="width: 250px; float: left; margin-left: 120px">
                    Create
                </button>
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>
