<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="recentViewedList" type="java.util.concurrent.CopyOnWriteArrayList" scope="request"/>

<tags:master pageTitle="Product List">
<p>
  Welcome to Expert-Soft training!
</p>
<form>
  <input name="query" value="${param.query}">
  <button>Search</button>
</form>
<table>
  <thead>
    <tr>
      <td>Image</td>
      <td>
        Description
        <tags:sortLinkAsc sortParam="descriptionAsc"/>
        <tags:sortLinkDesc sortParam="descriptionDesc"/>
      </td>
      <td class"quantity">
        Quantity
        </td>
      <td class="price">
        Price
        <tags:sortLinkAsc sortParam="priceAsc"/>
        <tags:sortLinkDesc sortParam="priceDesc"/>
      </td>
    </tr>
  </thead>
  <c:forEach var="product" items="${products}">
  <tr>
    <td>
      <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
    </td>
    <td>
     <a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a>
   </td>
   <td class="quantity">
         <fmt:formatNumber value="${cartItem.quantity}" var="quantity"/>
         <c:set var="error" value="${errors[cartItem.product.id]}"/>
         <input name="quantity" value="${not empty error ? paramValues['quantity'][status.index] : cartItem.quantity}" class="quantity"/>
         <c:if test="${not empty error}">
         <div class="error">
           ${errors[cartItem.product.id]}
         </div>
       </c:if>
       <input type="hidden" name="productId" value="${cartItem.product.id}"/>
     </td>
   <td class="price">
    <a href="${pageContext.servletContext.contextPath}/products/priceHistory/${product.id}">
      <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
    </a>
  </td>
      <td>
        <button form="addToCart"
        formaction="${pageContext.servletContext.contextPath}/products/addToCart/${cartItem.product.id}">
        Add to cart</button>
        </td>
</tr>
</c:forEach>
</table>

<p>
  Recenlty viewed:
</p>

<table>
	<tr>
		<c:forEach var="product" items="${recentViewedList}">
		<td>
			<div>
				<img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
			</div>
			<div>
				<a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a>
			</div>
			<div>
				<fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
			</div>
		</td>
	</c:forEach>
</tr>
</table>

</tags:master>