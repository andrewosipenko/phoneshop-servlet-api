<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.Cart" scope="request"/>
<html>
<head>
    <title>Cart</title>
</head>
<body>
<div>
    <jsp:include page="/WEB-INF/pages/header.jsp"/>
</div>
<div>
    <form method="post">
        <c:if test = "${param.successUpdate != null}">
            <label>
                Update items successfully
            </label>
        </c:if>
        <c:if test = "${param.successDelete != null}">
            <label>
                Delete successfully
            </label>
        </c:if>
        <table>
            <thead>
            <tr>
                <td>Id</td>
                <td>Code</td>
                <td>Description</td>
                <td>Price</td>
                <td>Quantity</td>
            </tr>
            </thead>
            <c:forEach var="cartItems" items="${cart.cartItems}" varStatus="status">
                <tr>
                    <td>${cartItems.product.id}</td>
                    <td>
                        <a href="<c:url value = "/products"/>/${cartItems.product.id}">${cartItems.product.code}</a>
                    </td>
                    <td>${cartItems.product.description}</td>
                    <td><fmt:formatNumber value="${cartItems.product.price}"/> ${cartItems.product.currency}</td>
                    <td>
                        <input type="hidden" name="productId" value="${cartItems.product.id}">
                        <input type="text" id="quantity${status.index}" name="quantity"
                               value="${quantities[status.index] != null ? quantities[status.index] : cartItems.quantity}">
                        <c:if test="${errors[status.index] != null}">
                            <label for="quantity${status.index}">${errors[status.index]}</label>
                        </c:if>
                    </td>
                    <td>
                        <button type="submit" name="delete" value="${status.index}">Delete</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <input type="submit" value="Update">
    </form>
</div>
<div class="footer">
    <jsp:include page="/WEB-INF/pages/footer.jsp"/>
</div>
</body>
</html>