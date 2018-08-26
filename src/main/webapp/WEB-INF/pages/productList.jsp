<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="products" type="java.util.ArrayList<com.es.phoneshop.model.Product>" scope="request"/>
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
      <td><c:out value="${product.id}"/></td>
      <td>
        <a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.code}</a>
      </td>
      <td><c:out value="${product.description}"/></td>
      <td><c:out value="${product.price}"/></td>
      <td><c:out value="${product.currency}"/></td>
      <td><c:out value="${product.stock}"/></td>
    </tr>
  </c:forEach>
</table>
