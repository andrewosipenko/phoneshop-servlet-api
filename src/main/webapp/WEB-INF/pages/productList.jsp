<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<script src="${pageContext.servletContext.contextPath}/js/main.js"></script>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<jsp:useBean id="recently_viewed" type="com.es.phoneshop.model.recentlyViewedProducts.RecentlyViewedProducts"
             scope="request"/>

<tags:master pageTitle="Product List">
    <h2>
        Welcome to Expert-Soft training!
    </h2>

    <div>
        <tags:cart cart="${cart}"/>
    </div>

    <h1>
        Products
    </h1>
    <form>
        <input name="query" value="${param.query}">
        <button>Search</button>
    </form>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td class="description">
                Description
                <tags:sortLink sort="description" order="asc"/>
                <tags:sortLink sort="description" order="desc"/>
            </td>
            <td class="price">
                Price
                <tags:sortLink sort="price" order="asc"/>
                <tags:sortLink sort="price" order="desc"/>
            </td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}" varStatus="productIndex">
            <tr>
                <td>
                    <img class="product-tile" src="${product.imageUrl}">
                </td>
                <td>
                    <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                            ${product.description}
                    </a>
                </td>
                <td class="price">
                    <a href="javascript:void(0)" onmouseover="showPopup(${productIndex.index})"
                       onmouseleave="showPopup(${productIndex.index})">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </a>
                    <div id="popup${productIndex.index}" class="popup" style="display:none">
                        <tags:productPriceHistoryPopup priceHistoryList="${product.priceHistoryList}"
                                                       productDescription="${product.description}"/>
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>

    <div>
        <tags:recentlyViewedProducts recently_viewed="${recently_viewed}"/>
    </div>
</tags:master>
  <p>
    Welcome to Expert-Soft training!
  </p>
  <form>
    <input name="query" value="${param.query}">
    <button>Search</button>
  </form>
  <table>
    <thead>
    <tr>
      <td>Image</td>
      <td class="description">
        Description
        <tags:sortLink sort="description" order="asc"/>
        <tags:sortLink sort="description" order="desc"/>
      </td>
      <td class="price">
        Price
        <tags:sortLink sort="price" order="asc"/>
        <tags:sortLink sort="price" order="desc"/>
      </td>
    </tr>
    </thead>
    <c:forEach var="product" items="${products}">
      <tr>
        <td>
          <img class="product-tile" src="${product.imageUrl}">
        </td>
        <td>
          <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
              ${product.description}
          </a>
        </td>
        <td class="price">
          <a href="${pageContext.servletContext.contextPath}/products/price-history/${product.id}">
            <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
          </a>
        </td>
      </tr>
    </c:forEach>
  </table>
</tags:master>
