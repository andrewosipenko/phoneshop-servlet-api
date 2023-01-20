<%@ tag trimDirectiveWhitespaces="true" %>
<style><%@ include file="/styles/main.css"%></style>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ attribute name="productDescription" type="java.lang.String" required="true" %>
<%@ attribute name="priceHistoryList" type="java.util.ArrayList" required="true" %>

<div>
    <h2>Price History</h2>
    <p>
        ${productDescription}
    </p>
    <table>
        <thead>
        <tr>
            <td>
                Start date
            </td>
            <td>
                Price
            </td>
        </tr>
        </thead>
        <c:forEach var="priceHistory" items="${priceHistoryList}">
            <tr>
                <td>
                    ${priceHistory.startDate}
                </td>
                <td class="price">
                    <fmt:formatNumber value="${priceHistory.price}" type="currency" currencySymbol="${priceHistory.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
