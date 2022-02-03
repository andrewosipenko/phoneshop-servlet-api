<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Price ${product.description}">
<p>
    <a href="http://localhost:8080/phoneshop-servlet-api/products">
    Go to main page
    </a>
</p>

<p>
    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
    ${product.description}
     </a>
<p>


  <table>
      <tr>
        <td>Image</td>
        <td>
          <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
        </td>
      </tr>

      <tr>
        <td>today price</td>
        <td>
          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
      </tr>
<c:forEach var="priceHistory" items="${product.priceHistoryList}">
    <tr>
        <td>${priceHistory.date}</td>
        <td>
           <fmt:formatNumber value="${priceHistory.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
    </tr>
</c:forEach>
  </table>
</tags:master>