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
    <header>
       <c:url var="contextLinkProducts" context="${pageContext.servletContext.contextPath}" value="/products" />
      <a href="${contextLinkProducts}">
       <%-- <img src="${contextLink}/images/logo.svg"/>--%>
        PhoneShop
      </a>
    </header>
    <main>
      <p>
        Welcome to Expert-Soft training!
      </p>
        <form>
          <input type="hidden" name="sort" value="${param.sort}">
          <input type="hidden" name="order" value="${param.order}">
          <input type="search" name="query" value="${param.query}" >
          <button type="submit">Search</button>
        </form>
      <form>
        <input type="hidden" name="query" value="${param.query}">
        <button type="submit">Skip Sort Parametrs</button>
      </form>
      <table>
        <thead>
          <tr>
            <td>Image</td>
            <td>Description
             <a href="${contextLinkProducts}?sort=description&order=asc&query=${param.query}">asc</a>
              <a href="${contextLinkProducts}?sort=description&order=desc&query=${param.query}">desc</a>
            </td>
            <td class="price">Price
              <a href="${contextLinkProducts}?sort=price&order=asc&query=${param.query}">asc</a>
              <a href="${contextLinkProducts}?sort=price&order=desc&query=${param.query}">desc</a>
            </td>
          </tr>
        </thead>
        <c:forEach var="product" items="${products}">
          <tr>
            <td>
              <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </td>
            <td>
              <a href="${contextLinkProducts}/${product.id}">${product.description}</a>
            </td>
            <td class="price">
              <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </td>
          </tr>
        </c:forEach>
      </table>
    </main>
  </body>
</html>