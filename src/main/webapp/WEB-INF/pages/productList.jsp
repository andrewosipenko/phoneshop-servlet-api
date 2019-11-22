<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="product" class="com.es.phoneshop.model.product.Product" scope="request"/>
<jsp:useBean id="priceHistory" class="com.es.phoneshop.model.product.PriceHistory"/>

<tags:master pageTitle="Product List">
  <h1 class="display-4">
    Welcome to Expert-Soft training!
  </h1>
  <form>
    <div class="form-row">
      <div class="col-3">
        <input type="text" class="form-control" placeholder="${param.query}" name="query" value="${param.query}">
      </div>
      <div class="col-3">
        <button class="btn btn-dark" method="get">Search</button>
      </div>
    </div>
  </form>
  <div class="row">
    <div class="col-3">
      <ul class="list-group">
        <li class="list-group-item">Sort by</li>
        <li class="list-group-item">Description:
          <tags:sort sort="description" order="asc"/>
          <tags:sort sort="description" order="desc"/>
        </li>
        <li class="list-group-item">Price:
          <tags:sort sort="price" order="asc"/>
          <tags:sort sort="price" order="desc"/>
        </li>
      </ul>
    </div>
  <div class="col-8">
    <div class="row">
      <c:forEach var="product" items="${products}">
        <div class="card col-4">
          <img
                  src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}"
                  class="card-img-top"
                  alt="..."
          />
          <div class="card-body">
            <h5 class="card-title"><a href="products/${product.id}">${product.description}</a></h5>
            <p class="card-text">
              <c:forEach var="priceHistory" items="${product.priceHistoryArrayList}">
                ${priceHistory.getStringDate()}:  ${priceHistory.price} ${priceHistory.currency}<br>
              </c:forEach>
            </p>
            <p class="btn btn-primary">
              <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
            </p>
          </div>
        </div>
      </c:forEach>
    </div>
  </div>
</tags:master>