<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<html>
<head>
  <title>Product List</title>
  <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="product-list">
<jsp:include page="header.jsp"/>
<main>
  <p>
    Welcome to Expert-Soft training!
  </p>
  <form method="get">
    <p><input type="search" name="searchLine" placeholder="Site search" value="${searchLineAttrib}">
      <input type="submit" value="Search"></p>
  </form>
  <table>
    <thead>
    <tr>
      <td>Image</td>
      <td>Description <a href="<%=request.getContextPath() + "/products?searchLine=" + request.getAttribute("searchLineAttrib") + "&sortingParameter=upDescription" %>">up</a> <a href="<%=request.getContextPath() + "/products?searchLine=" + request.getAttribute("searchLineAttrib") + "&sortingParameter=downDescription" %>">down</a></td>
      <td class="price">Price <a href="<%=request.getContextPath() + "/products?searchLine=" + request.getAttribute("searchLineAttrib") + "&sortingParameter=upPrice" %>">up</a> <a href="<%=request.getContextPath() + "/products?searchLine=" + request.getAttribute("searchLineAttrib") + "&sortingParameter=downPrice" %>">down</a> </td>
    </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
        </td>
        <td><a href="<%=request.getContextPath() + "/products/" %>${product.id}">${product.description}</a></td>
        <td class="price">
          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
      </tr>
    </c:forEach>
  </table>
  <h2>Recently viewed</h2>
  <table>
    <c:forEach var="product" items="${sessionScope.viewedProducts.VIEWED_PRODUCTS}">
      <tr>
        <td>
          <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
        </td>
        <td><a href="<c:url value="/products/${product.id}"/>">${product.description}</a></td>
        <td class="price">
          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
      </tr>
    </c:forEach>
  </table>
  <h2> Most popular</h2>
  <table>
    <c:forEach var="product" items="${sessionScope.mostViewed.mostViewedProducts}">
      <tr>
        <td>
          <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
        </td>
        <td><a href="<c:url value="/products/${product.id}"/>">${product.description}</a></td>
        <td class="price">
          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
        <td><h4> Views : ${product.ammountOfViews}</h4></td>
      </tr>
    </c:forEach>
  </table>
</main>
</body>
</html>