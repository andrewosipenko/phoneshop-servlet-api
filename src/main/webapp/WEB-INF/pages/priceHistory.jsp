<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="history" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Price History">
  <p>
    Price History
  </p>
  <table>
    <thead>
    <tr>
      <td>Date</td>
      <td>Price</td>
    </tr>
    </thead>
    <c:forEach var="history" items="${history}">
      <tr>
        <td>
          ${history.startDate}
        </td>
        <td class="price">
            <fmt:formatNumber value="${history.price}" type="currency"
                              currencySymbol="${history.currency.symbol}"/>
        </td>
      </tr>
    </c:forEach>
  </table>
</tags:master>
