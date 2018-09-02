<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<html>
<head>
    <title>telephones</title>
    <style type="text/css">
        <%@include file="/WEB-INF/styles/common.css" %>
    </style>
</head>
    <body>
        <%@include file="/WEB-INF/common/header.jsp"%>
        <table>
            <thead>
                <tr>
                    <td>Code</td>
                    <td>Description</td>
                    <td>Price</td>
                    <td>Currency</td>
                </tr>
            </thead>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>${product.code}</td>
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                                ${product.description}
                        </a>
                    </td>
                    <td>${product.price}</td>
                    <td>${product.currency}</td>
                </tr>
            </c:forEach>
        </table>
        <%@include file="/WEB-INF/common/footer.jsp"%>
    </body>
</html>