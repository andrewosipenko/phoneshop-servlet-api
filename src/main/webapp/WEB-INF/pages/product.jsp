<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="product" type="com.es.phoneshop.model.Product" scope="request"/>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/common/header.jsp"/>

<html>
<head>
    <title>${product.description}</title>
</head>
<body>
<form method="post">
    <c:if test = "${not empty addQuantity}">
        Added ${addQuantity} succtsfully
    </c:if>
    <table>
        <thead>
        <tr class="first">
            <td>ID</td>
            <td>Code</td>
            <td>Description</td>
            <td>Price</td>
            <td>Currency</td>
            <td>Stock</td>
        </tr>
        </thead>
        <tr>
            <td>${product.id}</td>
            <td>${product.code}</td>
            <td>${product.description}</td>
            <td>${product.price}</td>
            <td>${product.currency}</td>
            <td>${product.stock}</td>
        </tr>
        <tr>
            <td>
                <input type="text" name="quantity" id="quantity"
                       value="${empty param.quantity ? 1 : param.quantity}"
                       style="text-align: right">
                <c:if test="${error}">
                    <label for = "quantity">
                           <%-- <fmy:message key="error.number.format" bundle="i18n/messages"/>--%>
                        error
                    </label>
                </c:if>
            </td>
            <td>
                <input type="submit" value="Add to cart">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
