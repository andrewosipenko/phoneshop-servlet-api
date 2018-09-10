<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.Product" scope="request"/>

<html>
<head>
    <title>Product</title>
    <meta charset="utf-8">
</head>
<body class="bodyStile">
<div>
    <jsp:include page="/WEB-INF/pages/header.jsp"/>
</div>
<div>
    <form method="post" action="<c:url value="/products"/>/${product.id}">
        <c:if test = "${not empty addQuantity}">
            Added ${addQuantity} successfully.
        </c:if>
        <table>
            <col width=40%/>
            <thead>
            <tr>
                <td>Id</td>
                <td>${product.id}</td>
            </tr>
            <tr>
                <td>Code</td>
                <td>${product.code}</td>
            </tr>
            <tr>
                <td>Description</td>
                <td>${product.description}</td>
            </tr>

            <tr>
                <td>Price</td>
                <td>${product.price} ${product.currency}</td>
            </tr>
            <tr>
                <td>Stock</td>
                <td>${product.stock}</td>
            </tr>
            <tr>

                <td>
                    <input type="text" name="quantity" id="quantity" value="${empty param.quantity ? 1 : param.quantity}"><br>
                    <c:if test = "${error}">
                        <label for="quantity">${errorText}</label>
                    </c:if>
                </td>
                <td>
                    <input type="submit" value="Add to cart">
                </td>
            </tr>
            </thead>
        </table>
    </form>
</div>
<div class="footer">
    <jsp:include page="/WEB-INF/pages/footer.jsp"/>
</div>
</body>
</html>