<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.productdao.Product" scope="request"/>
<jsp:useBean id="productHistoryList" type="java.util.ArrayList" scope="request"/>

<tags:master pageTitle="Price History">
    <p>
        <strong>Price History</strong>
    </p>
    ${product.description}

    <c:choose>
        <c:when test="${productHistoryList == null || productHistoryList.isEmpty()}">
            </br>
            There is no history of price
        </c:when>
        <c:otherwise>
            <table>
                <thead>
                <tr>
                    <td>Date</td>
                    <td>Price</td>
                </tr>
                </thead>
                <c:forEach var="productHistory" items="${productHistoryList}">
                    <tr>
                        <td>
                            <tags:localDate date="${productHistory.getDate()}"/>
                        </td>
                        <td>
                            <fmt:formatNumber value="${productHistory.getPrice()}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</tags:master>