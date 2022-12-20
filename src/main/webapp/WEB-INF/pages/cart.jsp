<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<tags:master pageTitle="Cart">
  <c:if test="${empty cart.items}">
    <h1>
        <strong>There is no items in the cart</strong>
    </h1>
  </c:if>

  <c:if test="${not empty param.message}">
    <span class="success">${param.message}</span>
  </c:if>
  <c:if test="${not empty errors}">
    <span class="error">There were errors updating cart</span>
  </c:if>
  <form method="post" action="${pageContext.servletContext.contextPath}/cart">
      <table>
        <c:if test="${not empty cart.items}">
            <thead>
              <tr>
                <td>Image</td>
                <td>Description</td>
                <td class="quantity">Quantity</td>
                <td class="price">Price</td>
                <td></td>
              </tr>
            </thead>
        </c:if>
        <c:forEach var="item" items="${cart.items}" varStatus="status">
          <tr>
            <td>
              <img class="product-tile" src="${item.product.imageUrl}">
            </td>
            <td>
                <a href="${pageContext.servletContext.contextPath}/products/${item.product.id}">
                    ${item.product.description}
                </a>
            </td>
            <td class="quantity">
                <fmt:formatNumber value="${item.quantity}" var="quantity"/>
                <c:set var="error" value="${errors[item.product.id]}"/>
                <input name="quantity" value="${not empty error ? paramValues['quantity'][status.index]: item.quantity}" class="quantity">
                <c:if test="${not empty error}">
                    <div class="error">
                        ${errors[item.product.id]}
                    </div>
                </c:if>
                <input type="hidden" name="productId" value="${item.product.id}"/>
            </td>
            <td class="price">
                <a href="${pageContext.servletContext.contextPath}/products/price/${item.product.id}">
                    <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="${item.product.currency.symbol}"/>
                </a>
            </td>
            <td>
                <button form="deleteCartItem"
                formaction="${pageContext.servletContext.contextPath}/cart/deleteCartItem/${item.product.id}">Delete</button>
            </td>
          </tr>
        </c:forEach>

        <c:if test="${not empty cart.items}">
            <tr>
                <td></td>
                <td></td>
                <td>Total cost</td>
                <td>${cart.totalCost}</td>
            </tr>
        </c:if>
      </table>
      <p>
        <button>Update</button>
      </p>
  </form>
  <form action="${pageContext.servletContext.contextPath}/checkout">
    <button>Checkout</button>
  </form>
  <form id="deleteCartItem" method="post"></form>
</tags:master>