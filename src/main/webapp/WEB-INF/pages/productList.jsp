<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
  <p>
    Welcome to Expert-Soft training!
  </p>
  <form action="products" method="POST">

      <p>Найти товар по номеру: <input type="text" placeholder="Номер позиции" name="phoneId"></p>
      <button type="submit" value="findProduct" name="action">Найти товар</button>

      <p>Удалить товар по номеру: <input type="text" placeholder="Номер позиции" name="phoneIdToDelete"></p>
      <button type="submit" value="deleteProducts" name="action">Удалить товары</button>

      <button type="submit" value="findNotNullProducts" name="action">Выделить все товары</button>

  </form>
  <table>
    <thead>
      <tr>
        <td>ID</td>
        <td>Image</td>
        <td>Description</td>
        <td class="price">Price</td>
      </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <c:if test="${product.isChosen==true}"><tr style="background-color: aqua"></c:if>
      <c:if test="${product.isChosen==false}"><tr></c:if>
        <td>${product.id}</td>
        <td>
          <img class="product-tile" src="${product.imageUrl}">
        </td>
        <td>${product.description}</td>
        <td class="price">
          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
        </td>
      </tr>
    </c:forEach>
  </table>
</tags:master>