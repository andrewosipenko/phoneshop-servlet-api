<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Product Price History">
  <h1>Price History</h1>
  <h2>${product.description}<h2>
  <table>
     <thead>
       <tr>
         <td>Start Date</td>
         <td>Price</td>
       </tr>
     </thead>
     <c:forEach var="productPriceTag" items="${product.priceHistory}">
       <tr>
         <td>
           ${productPriceTag.priceChangeDate}
         </td>
         <td>
           <fmt:formatNumber value="${productPriceTag.price}" type="currency" currencySymbol="${productPriceTag.currency.symbol}"/>
         </td>
       </tr>
     </c:forEach>
  </table>
</tags:master>