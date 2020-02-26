<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Main Page</title>
</head>
<body>
<table style="float:left;">
    <c:forEach items="${flowers}" var="flower">
        <form action="/addFlower" method="post">
            <tr>
                <td>${flower.name}, price: ${flower.price}, in stock: ${flower.quantityInStock}
                    <input type="text" name="count"/>
                    <input type="submit" value="order"/>
                    <input type="hidden" value="${flower.name}" name="flowerName"/>
                </td>
            </tr>
        </form>
    </c:forEach>
</table>
<table style="float:right;">
    <c:forEach items="${sessionScope.cart.flowers}" var="flower">
        <tr>
            <td>
                    ${flower.key.name}, ordered: ${flower.value.countToOrder}, price: ${flower.value.price}
            </td>
        </tr>
    </c:forEach>
    <c:if test="${not empty sessionScope.cart}">
        <tr>
            <td>
                Total price: ${sessionScope.cart.totalPrice}
                <form action="/createOrder" method="post">
                    <input type="submit" value="order">
                    <input type="hidden" value="${sessionScope.cart.totalPrice}" name="price"/>
                </form>
            </td>
        </tr>
    </c:if>
</table>
<table>
    <c:forEach items="${orders}" var="order">
        <form action="/closeOrder" method="post">
            <tr>
                <td>${order.price}, open date: ${order.openDate}, active: ${order.isActive}
                    <c:if test="${not empty order.closeDate}">
                    close date:${order.closeDate}
                    </c:if>
                    <input type="submit" value="order"/>
                    <input type="hidden" value="${order.id}" name="id"/>
                </td>
            </tr>
        </form>
    </c:forEach>
</table>
</body>
</html>
