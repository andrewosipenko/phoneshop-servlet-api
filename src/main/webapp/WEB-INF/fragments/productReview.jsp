<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="productReviews" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="product" class="com.es.phoneshop.core.dao.product.Product" scope="request"/>
<c:if test="${not empty productReviews}">
    <table>
        <tr>
            <td>Name</td>
            <td>Rating</td>
            <td>Comment</td>
        </tr>
        <c:forEach items="${productReviews}" var="productReview">
            <c:if test="${productReview.approved}">
                <tr>
                    <td>
                            ${productReview.name}
                    </td>
                    <td>${productReview.rating}</td>
                    <td>${productReview.comment}</td>
                </tr>
            </c:if>
        </c:forEach>
    </table>
</c:if>

<form action="${pageContext.servletContext.contextPath}/products/postReview/${product.id}" method="post">
    <div>
        <label for="name">Name </label>
        <input type="text" id="name" name="name" value="${param.name}" required>
    </div>
    <p>
        <c:if test="${not empty requestScope['errorRating']}">
    <h4>
            <span style="color: red">
                Error: rating should be from 0 to 5!
            </span>
    </h4>
    </c:if>
    <div>
        <label for="rating">Rating </label>
        <input type="number" id="rating" name="rating" value="${param.rating}" required>
    </div>
    <p>
    <div>
        <label for="comment">Comment </label>
        <input type="text" id="comment" name="comment" value="${param.comment}" required>
    </div>
    <input type="hidden" id="productId" name="productId" value="${product.id}">
    <p>
        <input type="submit" value="Post">
</form>
