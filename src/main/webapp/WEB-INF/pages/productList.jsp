<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
  <form>
    <input name="searchQuery" value="${param.searchQuery}"/>
    <button type="submit">Search</button>
  </form>
  <table>
    <thead>
    <tr>
      <td>Image</td>
      <td>
        Description
        <tags:sortLink sortingCriteria="description" sortingOrder="asc"/>
        <tags:sortLink sortingCriteria="description" sortingOrder="desc"/>
      </td>
      <td class="price">
        Price
        <tags:sortLink sortingCriteria="price" sortingOrder="asc"/>
        <tags:sortLink sortingCriteria="price" sortingOrder="desc"/>
      </td>
    </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <img class="product-tile" src="${product.imageUrl}" alt="product image">
        </td>
        <td>
          <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
              ${product.description}
          </a>
        </td>
        <td class="price">
          <a href="${pageContext.servletContext.contextPath}/product-prices-history/${product.id}">
            <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
          </a>
        </td>
      </tr>
    </c:forEach>
  </table>
</tags:master>