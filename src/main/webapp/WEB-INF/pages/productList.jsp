<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
  <form style="margin:20px 0 20px 0 ;">
    <input name="query" value="${param.query}">
    <button>Search</button>
  </form>
  <table>
    <thead>
      <tr>
        <td>Image</td>
        <td>
        Description
        <tags:sortLink sort="DESCRIPTION" order="ASC"/>
        <tags:sortLink sort="DESCRIPTION" order="DESC"/>
        </td>
        <td class="price">
        Price
        <tags:sortLink sort="PRICE" order="ASC"/>
        <tags:sortLink sort="PRICE" order="DESC"/>
        </td>
      </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <img class="product-tile" src="${product.imageUrl}">
        </td>
        <td>
        <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
          ${product.description}
        </a>
        </td>
        <td class="price">
        <a href="${pageContext.servletContext.contextPath}/products/price-history/${product.id}">
          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </a>
        </td>
      </tr>
    </c:forEach>
  </table>
</tags:master>