<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="products" required="true" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>

<c:if test="${not empty products}">
<h2>
      Recently viewed
  </h2>
          <c:forEach var="product" items="${products}">
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