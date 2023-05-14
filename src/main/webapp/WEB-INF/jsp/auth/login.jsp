<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<head>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-5.0.0-beta/css/bootstrap.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth/login.css">
  <meta charset="UTF-8">
  <title>LOGIN</title>
</head>

<body>

  <main class="form-signin">
    <form method="POST" action="${pageContext.request.contextPath}/auth/login">
      <img class="mb-3" src="${pageContext.request.contextPath}/img/logo.png" alt="" width="300" loading="lazy">
      <div>
        <label for="inputEmail" class="visually-hidden">ユーザー名</label>
        <input type="text" id="inputEmail" name="name" class="form-control" placeholder="ユーザー名"
          value="${loginForm.name}" required autofocus>
      </div>
      <div class="mt-1">
        <label for="inputPassword" class="visually-hidden">パスワード</label>
        <input type="password" id="inputPassword" name="password" class="form-control"
          placeholder="パスワード" required>
      </div>
      <button class="w-100 btn btn-lg btn-primary mt-3" type="submit">サインイン</button>
      <c:if test="${loginForm.name != '' || loginForm.password != ''}">
        <div class="alert alert-danger mt-3" role="alert">
          <c:out value="ユーザー名またはパスワードが違います。" />
        </div>
      </c:if>
    </form>
  </main>
</body>

</html>