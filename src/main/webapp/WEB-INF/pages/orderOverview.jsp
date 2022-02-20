<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.order.Order" scope="request"/>
<tags:master pageTitle="Order overview">

<h1>Order overview<h1>

<form method="post" action="${pageContext.servletContext.contextPath}/checkout">
  <table>
    <thead>
      <tr>
        <td>Image</td>
        <td>
          Description
        </td>
        <td class="quantity">
          Quantity
        </td>
        <td class="price">
          Price
        </td>
      </tr>
    </thead>
    <c:forEach var="cartItem" items="${order.items}" varStatus="status">
    <tr>
      <td>
        <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${cartItem.product.imageUrl}">
      </td>
      <td>
       <a href="${pageContext.servletContext.contextPath}/products/${cartItem.product.id}">${cartItem.product.description}</a>
     </td>
     <td class="quantity">
      <fmt:formatNumber value="${cartItem.quantity}" var="quantity"/>
      ${cartItem.quantity.get()}
    </td>
    <td class="price">
      <a href="${pageContext.servletContext.contextPath}/products/priceHistory/${cartItem.product.id}">
        <fmt:formatNumber value="${cartItem.product.price}" type="currency" currencySymbol="${cartItem.product.currency.symbol}"/>
      </a>
    </td>
  </tr>
</c:forEach>
<tr class="quantity">
  <td>Subtotal:</td>
  <td>
    <fmt:formatNumber value="${order.subtotal}" type="currency" currencySymbol="$"/>
  </td>
</tr>
<tr class="quantity">
  <td>Delivery cost:</td>
  <td>
    <fmt:formatNumber value="${order.deliveryCost}" type="currency" currencySymbol="$"/>
  </td>
</tr>
<tr class="quantity">
  <td>Total cost:</td>
  <td>
    <fmt:formatNumber value="${order.totalCost}" type="currency" currencySymbol="$"/>
  </td>
</tr>
</table>


<h2>Your details</h2>
<table>
  <tags:orderOverviewRow name="firstName" label="First name" order="${order}"></tags:orderOverviewRow>
  <tags:orderOverviewRow name="lastName" label="Last name" order="${order}"></tags:orderOverviewRow>
  <tags:orderOverviewRow name="phone" label="Phone" order="${order}"></tags:orderOverviewRow>
  <tags:orderOverviewRow name="deliveryDate" label="Delivery date" order="${order}"></tags:orderOverviewRow>
  <tags:orderOverviewRow name="deliveryAddress" label="Delivery address" order="${order}"></tags:orderOverviewRow>

  <tr>
    <td>Payment method</td>
        <td>
            ${order.paymentMethod}
        </td>
    </tr>
</table>

</tags:master>