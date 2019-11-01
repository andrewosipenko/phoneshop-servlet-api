<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="product" class="com.es.phoneshop.model.product.Product" scope="request"/>
<jsp:useBean id="priceHistory" class="com.es.phoneshop.model.product.PriceHistory"/>

<tags:master pageTitle="Product List">
  <p>
    Welcome to Expert-Soft training!
  </p>
  <form>
    <label>
      <input name="query" value="${param.query}">
    </label>
    <button>Search</button>
  </form>
  <table>
    <thead>
      <tr>
        <td>Image</td>
        <td>Description
          <tags:sort sort="description" order="asc"/>
          <tags:sort sort="description" order="desc"/>
        </td>
        <td class="price">Price
          <tags:sort sort="price" order="asc"/>
          <tags:sort sort="price" order="desc"/>
        </td>
      </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
        </td>
        <td>
            <a href="products/${product.id}">${product.description}</a>
        </td>
        <td class="price">
          <div>
            <a href="#popup${product.id}">
              <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </a>
          </div>
          <div id="popup${product.id}" class="overlay">
            <div class="popup">
              <h2>Price history</h2>
              <h1>${product.description}</h1>
              <a class="close" href="#">&times;</a>
              <div class="content">
                <c:forEach var="priceHistory" items="${product.priceHistoryArrayList}">
                  <li>${priceHistory.getStringDate()}:  ${priceHistory.price} ${priceHistory.currency}</li>
                </c:forEach>
              </div>
            </div>
          </div>
            </td>
      </tr>
    </c:forEach>
  </table>
</tags:master>