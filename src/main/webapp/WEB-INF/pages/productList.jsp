<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<html>
<head>
    <title>telephones</title>
</head>
    <body>
        <table>
            <thead>
                <tr>
                    <td>Id</td>
                    <td>Code</td>
                    <td>Description</td>
                    <td>Price</td>
                    <td>Currency</td>
                    <td>Stock</td>
                </tr>
            </thead>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>${product.id}</td>
                    <td>${product.code}</td>
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                                ${product.description}
                        </a>
                    </td>
                    <td>${product.price}</td>
                    <td>${product.currency}</td>
                    <td>${product.stock}</td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>