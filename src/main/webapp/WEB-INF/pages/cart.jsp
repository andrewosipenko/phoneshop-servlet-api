<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.Cart" scope="request"/>
<html>
    <head>
        <title>my cart</title>
        <style type="text/css">
            <%@include file="/WEB-INF/styles/common.css" %>
        </style>
    </head>
    <body>
        <%@include file="/WEB-INF/common/header.jsp"%>
        <table>
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Code</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Currency</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="cartItem" items="${cart.cartItems}">
                    <tr>
                        <td>${cartItem.product.id}</td>
                        <td>${cartItem.product.code}</td>
                        <td>
                            <a href="${pageContext.servletContext.contextPath}/products/${cartItem.product.id}">
                                    ${cartItem.product.description}
                            </a>
                        </td>
                        <td>${cartItem.product.price}</td>
                        <td>${cartItem.product.currency}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <%@include file="/WEB-INF/common/footer.jsp"%>
    </body>
</html>