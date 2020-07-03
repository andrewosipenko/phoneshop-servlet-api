<%--
  Created by IntelliJ IDEA.
  User: Арсений Камадей
  Date: 24.06.2020
  Time: 23:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" class="com.es.phoneshop.model.Product" scope="request"/>
<tags:master pageTitle="Product Details">
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <h2>${product.description}</h2>
    <img src="${product.imageUrl}">
    <br>
    <h3>Current price : ${product.currentPrice.cost}</h3>
    <table>
        <thead>
        <td>Date</td>
        <td>Cost</td>
        </thead>
        <c:forEach var="price" items="${product.prices}">
            <tr>
                <td>${price.date}</td>
                <td>${price.cost}</td>
            </tr>
        </c:forEach>
    </table>

    <form method="post" action="${pageContext.request.contextPath}/products/${product.id}">
        <input name="quantity" id="quantity" value="${param.quantity}">
        <button>Add to my Cart</button>
    </form>
    <label class="congratulation-message">${congratulation}</label>
    <label class="error-message">${error}</label>

    <form action="${pageContext.request.contextPath}/products">
        <button>Go to List</button>
    </form>
    <jsp:include page="/cart/minicart"/>
    <tags:viewed-product/>
</tags:master>