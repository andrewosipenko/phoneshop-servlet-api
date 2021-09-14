<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product Info">
  <p>
  ${ product.description }
  </p>

  <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer${product.imageUrl}"/>
  <br/>
  <br/>
   Cost: 
   <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
   
   <br/>
   <br/>
   ${product.stock > 0? 'Available now!' : 'Unavailable' }
   
   <br/>
   <br/>
   <a href="${pageContext.servletContext.contextPath}/products">Back to products list</a>

</tags:master>
