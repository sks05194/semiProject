<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    
   
%>

<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/BeansPaM/css/notice_detail.css">
    <title>공지사항 상세화면</title>
</head>

<body>
    <header>
        <div class="menuBar">
            <img src="/BeansPaM/img/logo.png" alt="Beans Pam Logo" class="logo">
            <a href="/BeansPaM/">home</a>
            <a href="./notice_list.jsp">공지사항</a>
            <a href="#">Q&amp;A</a>
        </div>
    </header>

    <div class="container">
        <h1>공지사항 상세 페이지</h1>

        <form id="detailForm" class="form" action="updateNotice.jsp" method="post">

            <div class="form-group1">
                <label for="title">제목</label>
            </div>
			<input type="text" id="title" name="title" value="${title}" style="border: solid 1px lightgray; border-radius: 4px;" disabled><br>
			
            <div class="form-group2" style="position: relative;">
                <div class="content-header">
                    <label for="content">내용</label>
                </div><br>
                <input type="file" id="file-upload" name="file">
                <textarea id="content" name="content" rows="10" required disabled> ${content}</textarea>
            </div>

            <div class="button-group">
                <button type="button" class="exit-button" onclick="location.href='/BeansPaM/b/notice'">나가기</button>
                <div class="action-buttons">
                    <c:if test='${loginName == "관리자"}'>
						 <button type="button" id="deleteButton" onclick="deleteNotice(${n_no})">삭제</button>
                    	 <button type="button" id="editButton" onclick="modeifyNotice()">수정</button>
                    	 <button type="submit" id="saveButton" style="display: none;">저장</button>
					</c:if>
                </div>
            </div>
        </form>
    </div>
    <script>
    </script>						
</body>
</html>
