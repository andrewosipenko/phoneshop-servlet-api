<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<p>
  Hello from product list!
</p>
<table>
  <thead>
    <tr>
      <td>Image</td>
      <td>Brand</td>
      <td>Name</td>
      <td>Price</td>
    </tr>
  </thead>
  <c:forEach var="product" items="${products}">
    <tr>
      <td>
        <%--<img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">--%>
      </td>
      <%--<td>${product.brand}</td>--%>
      <td>${product.code}</td>
      <td>$ ${product.price}</td>
    </tr>
  </c:forEach>
</table>