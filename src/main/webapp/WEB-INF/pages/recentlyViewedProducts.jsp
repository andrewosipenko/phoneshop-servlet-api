<c:if test="${not empty sessionScope.recentlyViewedProducts}">
    <div>
      <h3>Recently Viewed Products</h3>
      <table>
        <tr>
          <c:forEach var="recentProduct" items="${sessionScope.recentlyViewedProducts}">
            <td>
              <img src="${recentProduct.imageUrl}">
            </td>
          </c:forEach>
        </tr>
        <tr>
          <c:forEach var="recentProduct" items="${sessionScope.recentlyViewedProducts}">
            <td>
              <a href="${pageContext.servletContext.contextPath}/products/${recentProduct.id}">
                ${recentProduct.description}
              </a>
            </td>
            </c:forEach>
        </tr>
        <tr>
          <c:forEach var="recentProduct" items="${sessionScope.recentlyViewedProducts}">
              <td class="price">
                <fmt:formatNumber value="${recentProduct.price}" type="currency" currencySymbol="${recentProduct.currency.symbol}"/>
              </td>
          </c:forEach>
        </tr>
      </table>
    </div>
</c:if>