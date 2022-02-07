<%@ tag trimDirectiveWhitespaces="true" %>

<jsp:useBean id="recentlyViewed" type="java.util.ArrayList" scope="application"/>

<c:if test="${not empty recentlyViewed}">
<h2>
      Recently viewed
  </h2>
          <c:forEach var="product" items="${recentlyViewed}">
              <table>
              <tr>
              <td>
                  <img class="product-tile" src="${product.imageUrl}">
                  <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                  <div>${product.description}</div>
                  </a>
                  <div>
                      <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                  </div>
                  </td>
                  </tr>
              </table>
          </c:forEach>
  </table>
  </c:if>
  <div class="clear"></div>