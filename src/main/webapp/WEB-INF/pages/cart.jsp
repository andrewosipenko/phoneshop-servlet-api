<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="java.util.ArrayList" scope="request"/>

<head>
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<tags:master pageTitle="Cart">
  <p>
    Welcome to Expert-Soft training!
  </p>
<form method="post" action="${pageContext.servletContext.contextPath}/cart">
<c:if test = "${not empty param.message and empty errors}">
      <div class = "success">
          ${param.message}
      </div>
    </c:if>
  <table>
    <thead>
      <tr>
        <td>Image</td>
        <td>Description</td>
        <td class="price">Price</td>
        <td>Quantity</td>
        <td></td>
      </tr>
    </thead>
    <c:forEach var="cartItem" items="${cart}">

      <tr>
        <td>
          <img class="product-tile" src="${cartItem.product.imageUrl}">
        </td>
        <td>
           <a href="${pageContext.servletContext.contextPath}/products/${cartItem.product.id}">
              ${cartItem.product.description}
           </a>
        </td>
        <td class="price">


            <fmt:formatNumber value="${cartItem.product.price}" type="currency" currencySymbol="${cartItem.product.currency.symbol}"/>

        </td>
        <td>
                 <c:set var="error" value="${errors[cartItem.product.id]}"/>

             <fmt:formatNumber value="${cartItem.quantity}" var="quantity"/>
            <input name="quantity" value="${quantity}" class="quantity">
            <input name="productId" type="hidden" value=${cartItem.product.id}>
            <c:if test="${not empty error}">
                <div class="error">
                    ${errors[cartItem.product.id]}
                </div>
            </c:if>
        </td>
         <td>
                        <button form="deleteCartItem" formAction="${pageContext.servletContext.contextPath}/cart/deleteCartItem/${cartItem.product.id}">
                            Delete
                        </button>
                    </td>
      </tr>
    </c:forEach>
  </table>
  <button>
    Update
  </button>
 </form>

 <form id="deleteCartItem" method="post"/>

   <tags:footer />

</tags:master>