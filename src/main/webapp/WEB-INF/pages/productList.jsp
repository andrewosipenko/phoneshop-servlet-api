<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<header>
  <jsp:include page="/WEB-INF/pages/header.jsp"/>
</header>

<p align="center">
  Hello from product list!
</p>
<table>
  <thead>
    <tr>
      <td>Id</td>
      <td>Code</td>
      <td>Description</td>
      <td>Price </td>
      <td>Currency</td>
      <td>Stock </td>
    </tr>
  </thead>
  <c:forEach var="product" items="${products}">
    <tr>
      <td>${product.id}</td>
      <td>
        <a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.code}</a>
      </td>
      <td>${product.description}</td>
      <td>${product.price}</td>
      <td>${product.currency}</td>
      <td>${product.stock}</td>
    </tr>
  </c:forEach>
</table>

<footer>
  <jsp:include page="/WEB-INF/pages/footer.jsp"/>
</footer>