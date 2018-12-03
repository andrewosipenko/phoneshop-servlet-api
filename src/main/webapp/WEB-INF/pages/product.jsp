<%--
  Created by IntelliJ IDEA.
  User: Liza Kurilo
  Date: 18.11.2018
  Time: 18:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<html>
<head>
    <title>Product List</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="product-list">
<header>
    <c:url var="contextLink" context="${pageContext.servletContext.contextPath}" value="/products" />
    <a href="${contextLink}">
        <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
        PhoneShop
    </a>
</header>
<main>
    <p>Welcome to Expert-Soft training!</p>
    cart: ${cart}
    <c:if test="${not empty param.message}">
        <p class="success">${param.message}</p>
    </c:if>
    <table>
        <tr>
            <td>
                <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}"  >
            </td>
            <td>
                <h1>${product.description}</h1>
                <p>Code: ${product.code}</p>
                <p>Stock: ${product.stock}</p>
                <p>Price:  <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/> </p>
                <form method="post" action="${contextLink}/${product.id}">
                    Quantity: <input name="quantity" value="${not empty param.quantity ? param.quantity : 1}" class="number">
                    <button>Add to cart</button>
                    <c:if test="${not empty quantityError}">
                        <p class="error">${quantityError}</p>
                    </c:if>
                </form>
            </td>
        </tr>
    </table>
</main>
<div>
    <jsp:include page="/WEB-INF/pages/footer.jsp"/>
</div>
</body>
</html>
