<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
  <html>
    <head></head>
    <body>
    <jsp:include page="header.jsp"/>
      <p>
        Hello from product list!
      </p>
      <table bgcolor="#32cd32" border="1px">
        <thead>
          <tr>
            <td>ID</td>
            <td>Code</td>
            <td>Description</td>
            <td>Price</td>
            <td>Currency</td>
            <td>Stock</td>
          </tr>
        </thead>
        <c:forEach var="product" items="${products}">
          <tr>
            <td>${product.id}</td>
            <td>
              <a href="${pageContext.servletContext.contextPath}/products/${product.id}">${product.code}</a>
            </td>
            <td>${product.description}</td>
            <td>$ ${product.price}</td>
            <td>${product.currency}</td>
            <td>${product.stock}</td>
          </tr>
        </c:forEach>
      </table>
      <jsp:include page="footer.jsp"/>
    </body>
  </html>