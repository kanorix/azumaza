<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-5.0.0-beta/css/bootstrap.min.css">
  <meta charset="UTF-8">
  <title>Strage</title>
</head>

<body>
  <header class="d-flex flex-row align-items-center p-3 px-4 mb-3 bg-white border-bottom">
    <a class="h5 my-0 me-auto fw-normal" href="${pageContext.request.contextPath}/strage">Strage Service</a>
    <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/auth/logout">Logout</a>
  </header>

  <main class="container">

    <h1 class="display-1">
      404 Not Found.
    </h1>

    <c:if test="${!empty message}">
      <div class="alert alert-danger" role="alert">
        <c:out value="${message}" />
      </div>
    </c:if>

  </main>

  <script src="${pageContext.request.contextPath}/css/bootstrap-5.0.0-beta/js/bootstrap.bundle.min.js"></script>
</body>

</html>