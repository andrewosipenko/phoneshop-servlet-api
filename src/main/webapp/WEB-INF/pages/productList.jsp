<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="products" type="java.util.ArrayList<com.es.phoneshop.web.Product>" scope="request"/>
<p>
<h2>Product List Page</h2>

<table>
  <thead>
  <tr>
    <td>Id</td>
    <td>Code</td>
    <td>Description</td>
    <td>Price</td>
    <td>Currency</td>
    <td>Stock</td>
  </tr>
  </thead>
  <c:forEach var="product" items="${products}">
    <tr>

        <%--<td>--%>
        <%--<img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">--%>
        <%--</td>--%>

      <td>${product.id}</td>
      <td>${product.code}</td>
      <td>${product.description}</td>
      <td>${product.price}</td>
      <td>${product.currency}</td>
      <td>${product.stock}</td>
    </tr>
  </c:forEach>
</table>
