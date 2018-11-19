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
      <a href="${pageContext.servletContext.contextPath}">
        <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
        PhoneShop
      </a>
    </header>
    <main>
      <p>
        Welcome to Expert-Soft training!
      </p>
      <form>
        <input type = "search" name = "query" value="${param.query}" class="input">
        <button type = "submit">Search</button>
      </form>
      <table>
        <thead>
          <tr>
            <td>Image</td>
            <td>Description
              <a href="${pageContext.servletContext.contextPath}/products?sort=description&order=asc&query=${param.query}">asc</a>
              <a href="${pageContext.servletContext.contextPath}/products?sort=description&order=desc&query=${param.query}">desc</a>
            </td>
            <td class="price">Price
              <a href="${pageContext.servletContext.contextPath}/products?sort=price&order=asc&query=${param.query}">asc</a>
              <a href="${pageContext.servletContext.contextPath}/products?sort=price&order=desc&query=${param.query}">desc</a>
            </td>
          </tr>
        </thead>
        <c:forEach var="product" items="${products}">
          <tr>
            <td>
              <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
            </td>
            <td>${product.description}</td>
            <td class="price">
              <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </td>
          </tr>
        </c:forEach>
      </table>
    </main>
  </body>
</html>