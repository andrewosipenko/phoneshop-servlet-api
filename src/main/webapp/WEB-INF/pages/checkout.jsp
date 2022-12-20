<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<tags:master pageTitle="Checkout">
  <p>
    Cart:${cart}, Total quantity: ${cart.totalQuantity}
  </p>
  <c:if test="${not empty param.message}">
    <span class="success">${param.message}</span>
  </c:if>
  <c:if test="${not empty errors}">
    <span class="error">Error occurred while placing order</span>
  </c:if>
  <form method="post" action="${pageContext.servletContext.contextPath}/checkout">
      <table>
        <thead>
          <tr>
            <td>Image</td>
            <td>Description</td>
            <td class="quantity">Quantity</td>
            <td class="price">Price</td>
          </tr>
        </thead>
        <c:forEach var="item" items="${order.items}" varStatus="status">
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
                ${item.quantity}
            </td>
            <td class="price">
                <a href="${pageContext.servletContext.contextPath}/products/price/${item.product.id}">
                    <fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="${item.product.currency.symbol}"/>
                </a>
            </td>
          </tr>
        </c:forEach>
        <tr>
            <td></td>
            <td></td>
            <td>Subtotal:</td>
            <td class="price">
                <p>
                    <fmt:formatNumber value="${order.subtotal}" type="currency" currencySymbol="${cart.currency}"/>
                </p>
            </td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td>Delivery cost:</td>
            <td class="price">
            <p>
                <fmt:formatNumber value="${order.deliveryCost}" type="currency" currencySymbol="${cart.currency}"/>
            </p>
            </td>
        </tr>
        <tr>
            <td>Total quantity:</td>
            <td class="quantity">${cart.totalQuantity}</td>
            <td class>Total cost:</td>
            <td class="price">
                <p>
                    <fmt:formatNumber value="${order.totalCost}" type="currency" currencySymbol="${cart.currency}"/>
                </p>
            </td>
        </tr>
      </table>
      <h2>Your details:</h2>
      <table>
        <tags:orderFormRow name="firstName" label="First name" order="${order}" errors="${errors}"></tags:orderFormRow>
        <tags:orderFormRow name="lastName" label="Last name" order="${order}" errors="${errors}"></tags:orderFormRow>
        <tags:orderFormRow name="phone" label="Phone" order="${order}" errors="${errors}"></tags:orderFormRow>
        <tags:orderFormRow name="deliveryDate" label="Delivery date" order="${order}" errors="${errors}"></tags:orderFormRow>
        <tags:orderFormRow name="deliveryAddress" label="Delivery address name" order="${order}" errors="${errors}"></tags:orderFormRow>
        <tr>
            <td>Payment method:<span style="color:red">*</span></td>
            <td>
                <select name="paymentMethod">
                    <c:forEach var="paymentMethod" items="${paymentMethods}">
                        <option>${paymentMethod}</option>
                    </c:forEach>
                </select>
                <c:set var="error" value="${errors['paymentMethod']}"/>
                <c:if test="${not empty error}">
                    <div class="error">
                        ${error}
                    </div>
                </c:if>
            </td>
        </tr>
      </table>
      <p>
        <button>Place order</button>
      </p>
  </form>
  <form id="deleteCartItem" method="post">
  </form>
</tags:master>