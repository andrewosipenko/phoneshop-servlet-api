<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<jsp:useBean id="recentlyViewed" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <form style="margin:20px 0 20px 0 ;">
        <input name="query" value="${param.query}">
        <button>Search</button>
    </form>
    <table>
        <thead>
            <tr>
                <td>Image</td>
                <td>
                Description
                    <span class="lowercase">
                        <tags:sortLink sort="DESCRIPTION" order="ASC"/>
                        <tags:sortLink sort="DESCRIPTION" order="DESC"/>
                    </span>
                </td>
                <td>
                Price
                    <span class="lowercase">
                        <tags:sortLink sort="PRICE" order="ASC"/>
                        <tags:sortLink sort="PRICE" order="DESC"/>
                    </span>
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
                <td class="field">
                    <a href="${pageContext.servletContext.contextPath}/products/price-history/${product.id}">
                        <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
  <c:if test="${not empty recentlyViewed}">
      <h2>
          Recently viewed
      </h2>
      <c:forEach var="product" items="${recentlyViewed}">
          <figure>
              <img class="product-tile" src="${product.imageUrl}">
              <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                  <div>${product.description}</div>
              </a>
              <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
          </figure>
      </c:forEach>
  </c:if>
  <div class="clear"></div>
</tags:master>