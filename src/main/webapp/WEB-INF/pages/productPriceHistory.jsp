<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.entity.Product" scope="request"/>
<tags:master pageTitle="Price History">
    <h1>Price History</h1>
    <p>
            ${product.description}
    </p>
    <table>
        <tr>
            <td>Start date</td>
            <td>Price</td>
        </tr>
        <c:forEach var="priceHistory" items="${product.priceHistory}">
            <tr>
                <td>
                        ${priceHistory.date}
                </td>
                <td>
                    <fmt:formatNumber value="${priceHistory.price}" type="currency"
                                      currencySymbol="${product.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>