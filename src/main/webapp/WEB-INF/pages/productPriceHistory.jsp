<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.Product" scope="request"/>
<tags:master pageTitle="Product Price History">
  <h1>Price history</h>
  <h2>
    ${product.description}
  </h2>
  <table>
  <thead>
    <tr>
      <td style="font-weight: bold">Date</td>
      <td style="font-weight: bold">Price</td>
  </thead>
  <c:forEach var="pricePoint" items="${product.priceHistory}">
        <tr>
          <td>
            <fmt:formatDate type="date" value="${pricePoint.date}" />
          </td>
          <td>
            <fmt:formatNumber value="${pricePoint.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
          </a>
        </tr>
      </c:forEach>
  </table>
</tags:master>