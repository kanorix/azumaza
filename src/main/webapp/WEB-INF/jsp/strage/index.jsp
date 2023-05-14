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
  <header class="d-flex flex-row align-items-center p-1 px-4 mb-3 bg-white border-bottom">
    <a class="" href="${pageContext.request.contextPath}/strage">
      <img src="${pageContext.request.contextPath}/img/header.jpg" height="50" alt="">
    </a>
    <a class="h5 mx-2 my-0 me-auto text-decoration-none" href="${pageContext.request.contextPath}/strage">Strage Service</a>
    <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/auth/logout">Logout</a>
  </header>

  <main class="container">

    <form id="upload" method="POST" action="${pageContext.request.contextPath}/strage/upload" enctype="multipart/form-data">
      <div class="card mb-4">
        <div class="card-header">
          Fileアップロード
        </div>
        <div class="card-body">
          <div class="">
            <c:if test="${status.equals('NOT_SELECTED')}">
              <div class="alert alert-danger" role="alert">
                <c:out value="ファイルが選択されていません。" />
              </div>
            </c:if>
            <c:if test="${status.equals('COMPLETED')}">
              <div class="alert alert alert-info" role="alert">
                <c:out value="アップロードが完了しました。" />
              </div>
            </c:if>
            <c:if test="${status.equals('DELETED')}">
              <div class="alert alert-warning" role="alert">
                <c:out value="ファイルの削除が完了しました。" />
              </div>
            </c:if>

            <div class="input-group">
              <input type="file" class="form-control" id="file" name="uploadFile">
              <button type="submit" id="upload-button" class="btn btn-outline-primary">
                <div id="upload-spinner" class="spinner-border text-primary align-middle" style="width: 1.3rem; height: 1.3rem;" role="status" hidden></div>
                UPLOAD
              </button>
            </div>
          </div>
        </div>
      </div>
    </form>

    <div class="accordion-parent">

      <div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="confirmModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="confirmModalLabel">確認</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
              このファイルを削除します。よろしいですか？
            </div>
            <div class="modal-footer">
              <form method="POST" action="${pageContext.request.contextPath}/strage/delete">
                <input type="hidden" id="modal-data" name="fileId">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">やめとく</button>
                <button type="submit" class="btn btn-danger">削除します</button>
              </form>
            </div>
          </div>
        </div>
      </div>

      <div class="card">
        <div class="card-header">
          File一覧
        </div>
        <div class="card-body">

          <c:if test="${empty strageForm.files}">
            <div class="alert alert-primary mt-3" role="alert">
              <c:out value="ファイルがありません！" />
            </div>
          </c:if>

          <c:forEach var="file" items="${strageForm.files}" varStatus="status">
            <div class="accordion-item">
              <h2 class="accordion-header" id="files${status.index}">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#filesInfo${status.index}" aria-expanded="true" aria-controls="filesInfo${status.index}">
                  <c:out value="${file.name}" />&nbsp;&nbsp;(&nbsp;size:&nbsp;<c:out value="${file.size}" />&nbsp;)
                </button>
              </h2>
              <div id="filesInfo${status.index}" class="accordion-collapse collapse" aria-labelledby="files${status.index}" data-bs-parent=".accordion-parent">
                <div class="accordion-body">
                  <div class="row justify-content-between" role="group">
                    <a class="btn btn-outline-primary col-lg-10 mr-2" href="${pageContext.request.contextPath}/strage/download?id=${file.id}" >Download</a>
                    <button type="button" class="btn btn-danger col-lg-1" data-bs-toggle="modal" data-bs-target="#confirmModal" data-file-id="${file.id}">Delete</button>
                  </div>
                </div>
              </div>
            </div>
          </c:forEach>

        </div>
      </div>

    </div>

  </main>

  <script src="${pageContext.request.contextPath}/css/bootstrap-5.0.0-beta/js/bootstrap.bundle.min.js"></script>
  <script src="${pageContext.request.contextPath}/js/strage/strage.js"></script>
</body>

</html>