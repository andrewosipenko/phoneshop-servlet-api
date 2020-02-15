<%@ attribute name="pageClass" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>

<html>
<head>
  <title>${pageTitle}</title>
  <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
</head>
<body class="product-list">
  <header>
    <a href="${pageContext.servletContext.contextPath}">
      <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
      PhoneShop
    </a>
    <a class ="cart" href='http://localhost:8080/phoneshop_servlet_api_war_exploded/cart'>
      Cart
      <img src="${pageContext.servletContext.contextPath}/images/cart.jpg"/>
    </a>
  </header>
  <main>
    <jsp:doBody/>
  </main>
</body>
</html>