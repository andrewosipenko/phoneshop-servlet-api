<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<jsp:useBean id="recentViewedList" type="java.util.concurrent.CopyOnWriteArrayList" scope="request"/>

<tags:master pageTitle="${product.description}">

<p>
	Cart: ${cart}
</p>

<p>
	Recent: ${recentViewedList}
</p>

<c:if test="${not empty param.message}">
<div class="success">
	${param.message}
</div>
<br>
</c:if>

<p>
	<a href="http://localhost:8080/phoneshop-servlet-api/products">
		Go to main page
	</a>
</p>

<p>
	${product.description}
</p>

<form method="post" action="${pageContext.servletContext.contextPath}/products/${product.id}">
	<table>
		<tr>
			<td>Image</td>
			<td>
				<img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
			</td>
		</tr>
		<tr>
			<td>code</td>
			<td>
				${product.code}
			</td>
		</tr>
		<tr>
			<td>price</td>
			<td>
				<fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
			</td>
		</tr>
		<tr>
			<td>stock</td>
			<td>
				${product.stock}
			</td>
		</tr>
		<tr>
			<td>quantity</td>
			<td>
				<input name="quantity" value="${not empty param.quantity ? param.quantity : 1}" class="quantity"/>
				<c:if test="${not empty error}">
				<span class="error">
					${error}
				</span>
			</c:if>
		</td>
	</tr>
</table>
<p>
	<button>Add to cart</button>
</p>
</form>

<p>
	<a href="${pageContext.servletContext.contextPath}/products/priceHistory/${product.id}">
		Price history
	</a>
</p>

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