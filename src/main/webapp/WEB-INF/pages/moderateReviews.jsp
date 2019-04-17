<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="reviews" class="java.util.LinkedList" scope="request"/>
<table>
    <tr>
        <td>Name</td>
        <td>Rating</td>
        <td>Comment</td>
        <td>Actions</td>
    </tr>
    <c:forEach items="${reviews}" var="productReview">
        <c:if test="${not productReview.approved}">
            <tr>
                <td>
                        ${productReview.name}
                </td>
                <td>
                        ${productReview.rating}
                </td>
                <td>
                        ${productReview.comment}
                </td>
                <td>
                    <form action="${pageContext.servletContext.contextPath}/moderateReviews" method="post">
                        <input type="hidden" name="reviewId" id="reviewId" value="${productReview.reviewId}">
                        <input type="submit" value="Approve">
                    </form>
                </td>
            </tr>
        </c:if>
    </c:forEach>
</table>