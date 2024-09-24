<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/BeansPaM/css/notice_update.css">
    <title>공지사항 수정</title>
</head>

<body>
    <header>
        <div class="menuBar">
            <img src="/BeansPaM/img/full_logo.png" alt="Beans Pam Logo" class="logo">
            <a href="/BeansPaM/">home</a>
            <a href="./notice_list.jsp">공지사항</a>
            <a href="#">Q&A</a>
        </div>
    </header>

    <div class="container">
        <h1>공지사항 수정 페이지</h1>

        <form id="updateForm" class="form" action="updateNoticeAction.do" method="post">
            <input type="hidden" name="n_no" value="${notice.n_no}">
            
            <div class="form-group1">
                <label for="title">제목</label>
                <input type="text" id="title" name="title" value="${notice.n_title}" style="border: solid 1px lightgray; border-radius: 4px;">
            </div>
            
            <div class="form-group2">
                <label for="content">내용</label>
                <textarea id="content" name="content" rows="10">${notice.n_content}</textarea>
            </div>

            <div class="button-group">
                <button type="button" class="exit-button" onclick="location.href='/BeansPaM/b/notice'">취소</button>
                <button type="submit" id="saveButton">저장</button>
            </div>
        </form>
    </div>
</body>
</html>
