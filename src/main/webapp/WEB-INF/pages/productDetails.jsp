<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product Details">
  <p>
    Product ${product.description}
  </p>
  <a href="${pageContext.servletContext.contextPath}/cart">
  <p>
    Cart: ${cart}
  </p>
  </a>
   <c:if test = "${not empty param.message and empty error}">
      <div class = "success">
          ${param.message}
      </div>
    </c:if>

  <c:if test = "${not empty error}">
    <div class = "error">
        Error while adding to cart
    </div>
  </c:if>

  <form method = "post">
      <table>
        <tr>
          <td>Image</td>
          <td>
           <img src="${product.imageUrl}">
          </td>
        </tr>
        <tr>
          <td class="price">Price</td>
          <td class="price">
             <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
           </td>
        </tr>

        <tr>
        <td class="code">Code</td>
        <td class="code">
           ${product.code}
        </td>
        </tr>
        <tr>
          <td class="stock">Stock</td>
          <td class="stock" valign="top">
             ${product.stock}
          </td>
        </tr>
        <tr>
          <td class="quantity">Quantity</td>
          <td class="quantity">
             <input name = "quantity" value = "${not empty error ? param.quantity : 1}">
             <c:if test = "${not empty error}">
                         <div class = "error">
                             ${error}
                         </div>
              </c:if>
          </td>

        </tr>
       </table>
         <Button>Add to cart</Button>
   </form>
 <p>
        Recently viewed products
    </p>


        <tags:recentlyViewedProducts />

</tags:master>
<div style="clear: both"></div>
  <tags:footer />
