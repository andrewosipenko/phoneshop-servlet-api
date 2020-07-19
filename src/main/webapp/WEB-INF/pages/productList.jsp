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
        <td class="description">
          Description
          <tags:sortFields sort="description" order="asc"/>
          <tags:sortFields sort="description" order="desc"/>
        </td>
        <td class="price">
          Price
          <tags:sortFields sort="price" order="asc"/>
          <tags:sortFields sort="price" order="desc"/>
        </td>
      </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <img class="product-tile" src=${product.imageUrl}>
        </td>
        <td>
          <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
              ${product.description}
          </a>
        </td>
        <td class="price">
          <a href="${pageContext.servletContext.contextPath}/products/${product.id}/priceHistory">
            <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
          </a>
        </td>
      </tr>
    </c:forEach>
  </table>
</tags:master>