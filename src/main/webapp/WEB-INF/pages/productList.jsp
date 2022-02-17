<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="recentViewedList" type="java.util.concurrent.CopyOnWriteArrayList" scope="request"/>
<jsp:useBean id="cart" type="com.es.phoneshop.cart.Cart" scope="request"/>

<tags:master pageTitle="Product List">

<p>
	Cart: ${cart}
</p>

<c:if test="${not empty param.message}">
<div class="success">
	${param.message}
</div>
<br>
</c:if>

<c:if test="${not empty param.errorMessage}">
<div class="error">
  There were errors updating cart
</div>
<br>
</c:if>

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

  <c:forEach var="product" items="${products}" varStatus="status">
  <form id="add${status.index}" method="post" >
    <tr>
      <td>
        <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
      </td>
      <td>
       <a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.description}</a>
     </td>

     <td class="quantity">
       <c:set var="error" value="${param.errorMessage}"/>
       <input
       name="quantity" value="${not empty param.quantity ? param.quantity : 1}" class="quantity"/>
       <c:if test="${param.prId.toString() eq product.id.toString()}">
       <div class="error">
         ${param.errorMessage}
       </div>
     </c:if>

     <input type="hidden" name="productId" value="${product.id}"/>

     <input type="hidden" name="query2" value="${param.query}"/>

     <input type="hidden" name="sortParam2" value="${param.sortParam}"/>
    </td>

    <td class="price">
      <a href="${pageContext.servletContext.contextPath}/products/priceHistory/${product.id}">
        <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
      </a>
    </td>

    <td>
      <button form="add${status.index}" formAction="${pageContext.servletContext.contextPath}/products/addToCart/${product.id}">
      Add to cart</button>
    </td>

    </tr>
  </form>
  </c:forEach>
</table>



<p>
  Recently viewed:
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