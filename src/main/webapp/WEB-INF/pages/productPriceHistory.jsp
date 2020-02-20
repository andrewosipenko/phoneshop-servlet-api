<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" class="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product List">
  <h2>Price History</h2>
  <h3>${product.description}</h3>
  <table>
    <tr><td><b>Start date</b></td><td><b>Price</b></td></tr>
    <c:forEach var="history" items="${product.priceHistoryList}">
      <tr><td>${history.date}</td>
        <td> <fmt:formatNumber value="${history.price}" type="currency" currencySymbol="${history.currency.symbol}"/></td>
      </tr>
    </c:forEach>
  </table>
</tags:master>