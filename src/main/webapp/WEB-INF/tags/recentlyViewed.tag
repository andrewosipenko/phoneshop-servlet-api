<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="products" required="true" type="java.util.ArrayList" %>

<c:if test="${not empty viewedProducts}">
    <h3>Recently viewed products: </h3><br>
    <table border="1">
        <tr>
            <c:forEach var="viewed" items="${viewedProducts}">
                <td align="center"  width="167">
                    <img class="product-tile" src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${viewed.imageUrl}" align = "center">
                    <p><br><a href="${pageContext.servletContext.contextPath}/products/${viewed.id}">${viewed.description}</a><br>
                        <a href="#x" class="overlay" id="win${viewed.id}"></a>
                        Price: <fmt:formatNumber value="${viewed.price}" type="currency" currencySymbol="&#36"/>
                    </p>
                </td>
            </c:forEach>
        </tr>
    </table>
</c:if>