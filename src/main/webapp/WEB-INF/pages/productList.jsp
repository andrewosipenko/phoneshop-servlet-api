<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
  <c:if test="${not empty param.errors}">
      <span class="error">Error occurred while placing products: ${param.errors}</span>
  </c:if>
  <form>
    <input name="query" value="${param.query}">
    <button>Search</button>
  </form>
  <form method="post">
    <table>
        <thead>
          <tr>
            <td>Image</td>
            <td>Description
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
            <td>
                <input name="quantity" value="${not empty error? param.quantity: 1}" class= "quantity">
                <input type="hidden" name="productId" value="${product.id}"/>
                <c:if test="${not empty error}">
                    <div class="error">
                        ${errors[product.id]}
                    </div>
                </c:if>
            </td>
            <td class="price">
                <a href="${pageContext.servletContext.contextPath}/products/price/${product.id}">
                    <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                </a>
            </td>
            <td>
                <button formaction="${pageContext.servletContext.contextPath}/cart/addToCart/${product.id}">Add to cart</button>
            </td>
          </tr>
        </c:forEach>
      </table>
  </form>
</tags:master>