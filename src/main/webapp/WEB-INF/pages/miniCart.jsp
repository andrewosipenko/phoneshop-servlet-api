<%--
  Created by IntelliJ IDEA.
  User: Арсений Камадей
  Date: 03.07.2020
  Time: 10:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.Cart" scope="request"/>
<div style="margin: 20px">
    <h2 style="margin: 3px">MiniCart</h2>
    <h4 style="margin: 3px; font-weight: normal">Total Price ${cart.price}</h4>
    <a href="${pageContext.request.contextPath}/cart">go to Cart</a>
</div>
