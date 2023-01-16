<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="priceHistoryList" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="productDescription" type="java.lang.String" scope="request"/>
<tags:master pageTitle="Price History">

  <p>
      ${productDescription}
  </p>
  <table>
    <thead>
      <tr>
        <td>
          Start date
        </td>
        <td>
          Price
        </td>
      </tr>
    </thead>
    <c:forEach var="priceHistory" items="${priceHistoryList}">
      <tr>
        <td>
          ${priceHistory.startDate}
        </td>
        <td class="price">
          <fmt:formatNumber value="${priceHistory.price}" type="currency" currencySymbol="${priceHistory.currency.symbol}"/>
        </td>
      </tr>
    </c:forEach>
  </table>
</tags:master>