<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>

<tags:master pageTitle="Product List">
  <script>
    function popUpFunction() {
      let popup = document.getElementById("myPopup");
      popup.classList.toggle("show");
    }
  </script>
  <p>
    Welcome to Expert-Soft training!
  </p>
  <div>
    <form method="get">
      <input type="text" name="query" value="${param.query}">
      <button type="submit">Search</button>
    </form>
  </div>
  <table>
    <thead>
      <tr>
        <td>Image</td>
        <td class="description">Description</td>
        <td class="price">Price</td>
      </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
        </td>
        <td>${product.description}</td>
        <td class="price">
          <a href="#">
            <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
          </a>
        </td>
      </tr>
    </c:forEach>
  </table>
</tags:master>