<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Advanced Search">
    <form>
        <h2>Advanced Search</h2>
        <table>
            <tags:advancedSearchFormRow name="description" label="Description"
                                        errors="${errors}"></tags:advancedSearchFormRow>
            <td>
                <select name="searchType">
                    <c:forEach var="searchType" items="${searchTypes}">
                        <option value="${searchType}"
                                <c:if test="${searchType eq param['searchType']}">
                                    selected
                                </c:if>> ${searchType}
                        </option>
                    </c:forEach>
                </select>
                <c:set var="error" value="${errors['searchType']}"/>
                <c:if test="${not empty error}">
                    <p class="error">
                            ${error}
                    </p>
                </c:if>
            </td>
            <tags:advancedSearchFormRow name="minPrice" label="Min price"
                                        errors="${errors}"></tags:advancedSearchFormRow>
            <tags:advancedSearchFormRow name="maxPrice" label="Max price"
                                        errors="${errors}"></tags:advancedSearchFormRow>
        </table>
        <p>
            <button>Search</button>
        </p>
    </form>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>
                Description
                <tags:sortLink sorting="DESCRIPTION" type="ASC"></tags:sortLink>
                <tags:sortLink sorting="DESCRIPTION" type="DESC"></tags:sortLink>
            </td>
            <td class="quantity">Quantity</td>
            <td class="price">
                Price
                <tags:sortLink sorting="PRICE" type="ASC"></tags:sortLink>
                <tags:sortLink sorting="PRICE" type="DESC"></tags:sortLink>
            </td>
            <td>Operation</td>
        </tr>
        </thead>
        <c:forEach var="product" items="${products}" varStatus="status">
            <form action="${pageContext.servletContext.contextPath}/products/addCartItem/${product.id}"
                  method="post">
                <tr>
                    <td>
                        <img class="product-tile" src="${product.imageUrl}">
                    </td>
                    <td>
                        <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                                ${product.description}
                        </a>
                    </td>
                    <td class="quantity">
                        <c:set var="err" value="${errors[product.id]}"/>
                        <input name="quantity"
                               value="${not empty err ? param.quantity : 1}"
                               class="quantity"/>
                        <c:if test="${not empty err}">
                            <p class="error">
                                    ${errors[product.id]}
                            </p>
                        </c:if>
                        <input name="id" type="hidden" value="${product.id}"/>
                    </td>
                    <td class="price">
                        <a href="${pageContext.servletContext.contextPath}/history/${product.id}">
                            <fmt:formatNumber value="${product.price}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/>
                        </a>
                    </td>
                    <td>
                        <button>Add to cart</button>
                    </td>
                </tr>
            </form>
        </c:forEach>
    </table>
</tags:master>
