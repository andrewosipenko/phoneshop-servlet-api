    <%@ page contentType="text/html;charset=UTF-8" %>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

        <jsp:useBean id="cart" type="com.es.phoneshop.cart.Cart" scope="request"/>
        <html>
        <head>
        </head>
        <body>
        <jsp:include page="header.jsp"/>
        <p>
        Hello from cart!
        </p>
        <table bgcolor="#32cd32" border="1px">
        <thead>
        <tr>
        <td>Code</td>
        <td>Quantity</td>
        <td>Price</td>
        </tr>
        </thead>
        <c:forEach var="cartItem" items="${cart.getCartItems()}">
            <tr>
            <td>
            <a href="${pageContext.servletContext.contextPath}/products/${cartItem.product.id}">${cartItem.product.code}
            </a>
            </td>
            <td><fmt:formatNumber value = "${cartItem.quantity}" ></fmt:formatNumber></td>
            <td><fmt:formatNumber value = "${cartItem.product.price * cartItem.quantity}"></fmt:formatNumber></td>
            </tr>
        </c:forEach>
        </table>
        <jsp:include page="footer.jsp"/>
        </body>
        </html>