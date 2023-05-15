<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request" />
<tags:master pageTitle="Product List">
  <p style="font-size: larger; font-weight: bold;">
    Price history
  </p>
  <p style="font-size: larger; font-weight: bold;">
      ${product.description}
    </p>
  <table>
    <thead>
      <tr>
        <th>Start Date</th>
        <th>Price</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="priceData" items="${product.priceHistory}">
        <tr>
          <td>${priceData.startDate}</td>
          <td>${priceData.price}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <%@ include file="/WEB-INF/pages/recentlyViewedProducts.jsp"%>
</tags:master>