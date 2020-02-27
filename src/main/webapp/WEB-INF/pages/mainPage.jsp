<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Main Page</title>
</head>
<body>
<style>
    table, th, td {
        border: 1px solid black;
    }
</style>
<sec:authorize access="hasAuthority('USER')">
    <h4>Hello ${customer.email} Balance: ${customer.balance} discount: ${customer.discount}%
        <button type="button" onclick="location.href='/logout'">Exit</button>
    </h4>
    <table style="float:left;">
        <tr>
            <th>Name</th>
            <th>Price</th>
            <th>In Stock</th>
            <th>Count</th>
            <th></th>
        </tr>
        <c:forEach items="${flowers}" var="flower">
            <form action="/addFlower" method="post">
                <tr>
                    <td>${flower.name}</td>
                    <td>${flower.price}</td>
                    <td>${flower.quantityInStock}</td>
                    <td>
                        <input type="text" name="count"/>
                        <input type="hidden" value="${flower.name}" name="flowerName"/>
                    </td>
                    <td>
                        <input type="submit" value="Add to cart"/>
                    </td>
                </tr>
            </form>
        </c:forEach>
    </table>

    <c:if test="${not empty sessionScope.cart}">
        <table style="float:right;">
            <tr>
                <th>Name</th>
                <th>Ordered</th>
                <th>Price</th>
                <th></th>
            </tr>
            <c:forEach items="${sessionScope.cart.flowers}" var="flower">
                <tr>
                    <td>${flower.key.name}</td>
                    <td>${flower.value.countToOrder}</td>
                    <td>${flower.value.price}</td>
                </tr>
            </c:forEach>
            <c:if test="${not empty sessionScope.cart}">
                <tr>
                    <th>Total price</th>
                </tr>
                <tr>
                    <td>${sessionScope.cart.totalPrice}</td>
                    <td>
                        <form action="/createOrder" method="post">
                            <input type="submit" value="Create order">
                            <input type="hidden" value="${sessionScope.cart.totalPrice}" name="price"/>
                        </form>
                    </td>
                </tr>
            </c:if>
        </table>
    </c:if>

    <c:if test="${not empty orders}">
        <table>
            <tr>
                <th>Price</th>
                <th>Open date</th>
                <th>Active</th>
                <th>Close date</th>
                <th></th>
            </tr>
            <c:forEach items="${orders}" var="order">
                <form action="/closeOrder" method="post">
                    <tr>
                        <td>${order.price}</td>
                        <td>${order.openDate}</td>
                        <td>${order.isActive}</td>
                        <td>${order.closeDate}</td>
                        <td><input type="submit" value="order"/>
                            <input type="hidden" value="${order.id}" name="id"/>
                        </td>
                    </tr>
                </form>
            </c:forEach>
        </table>
    </c:if>

</sec:authorize>
<sec:authorize access="hasAuthority('ADMIN')">
    <c:if test="${not empty orders}">
        <table style="float:left;">
            <tr>
                <th>Price</th>
                <th>Active</th>
                <th>Open date</th>
                <th>Close date</th>
                <th>Customer</th>
            </tr>
            <c:forEach items="${orders}" var="order">
                <form action="/closeOrder" method="post">
                    <tr>
                        <td>${order.price}</td>
                        <td>${order.isActive}</td>
                        <td>${order.openDate}</td>
                        <td>${order.closeDate}</td>
                        <td>${order.customer.email}</td>
                        <td>
                            <input type="submit" value="Close order"/>
                            <input type="hidden" name="id" value="${order.id}"/>
                        </td>
                    </tr>
                </form>
            </c:forEach>
        </table>
    </c:if>
</sec:authorize>
</body>
</html>
