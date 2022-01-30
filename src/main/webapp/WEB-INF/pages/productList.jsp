<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/priceHictory.css">

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">

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
            <td>
                Description
                <tags:sortLink sort="description" order="asc" value="&#9650;"></tags:sortLink>
                <tags:sortLink sort="description" order="desc" value="&#9660;"></tags:sortLink>
            </td>

            <td class="price">
                Price
                <tags:sortLink sort="price" order="asc" value="&#9650;"></tags:sortLink>
                <tags:sortLink sort="price" order="desc" value="&#9660;"></tags:sortLink>
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
                    <div id="container">
                        <div class="link">
                            <a href="" >
                                <fmt:formatNumber value="${product.price}" type="currency"
                                                  currencySymbol="${product.currency.symbol}"/>
                            </a>

                            <span class="tip">
                                 <h2>Price History</h2>
                                    <h3> ${product.description}</h3>
                                    <table>
                                        <thread>
                                            <tr>
                                                <td>Start date</td>
                                                <td>Price</td>
                                            </tr>
                                        </thread>

                                <c:forEach var="priceHistory" items="${product.priceHistoryList}">
                                    <tr>
                                        <td>
                                            <fmt:formatDate value="${priceHistory.startDate}" pattern="yyyy-MM-dd"/>
                                        </td>
                                        <td>
                                            <fmt:formatNumber value="${priceHistory.price}" type="currency"
                                                              currencySymbol="${product.currency.symbol}"/>
                                        </td>
                                    </tr>
                                </c:forEach>
                                    </table>
                             </span>
                        </div>
                    </div>

                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>