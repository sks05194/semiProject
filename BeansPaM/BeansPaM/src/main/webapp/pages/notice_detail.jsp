<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="ko">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="/BeansPaM/css/notice_detail.css">
	<title>공지사항 상세화면</title>
</head>

<body>
	<div class="container">
		<h1>공지사항 상세 페이지</h1>

		<form id="detailForm" class="form" action="./update" method="post">

			<div class="form-group1">
				<label for="title">제목</label>
			</div>
			<input type="text" id="title" name="title" value="${title}"
				style="border: solid 1px lightgray; border-radius: 4px;"
				<c:if test='${loginName != "관리자"}'>disabled</c:if>>
			<div class="form-group2" style="position: relative;"><br>
				<div class="content-header">
					<label for="content">내용</label>
				</div>
				<textarea id="content" name="content" rows="10" required
					<c:if test='${loginName != "관리자"}'>disabled</c:if>>${content}</textarea>
				<input type="hidden" name="n_no" value="${n_no}"> <input
					type="hidden" name="n_delete_yn" value="${n_delete_yn}">
			</div>
			<div class="button-group">
				<button type="button" class="exit-button"
					onclick="location.href='/BeansPaM/b/notice'">나가기</button>
				<div class="action-buttons">
					<c:if test='${loginName == "관리자"}'>
						<button type="button" id="deleteButton"
							onclick="goDelete(${n_no})">삭제</button>
						<button type="submit" id="editButton" onclick="goUpdate()">수정완료</button>
					</c:if>
				</div>
			</div>
		</form>
	</div>
	<script>
		function goDelete(n_no) {
			if (confirm("정말 삭제하시겠습니까?")) {
				location.href = "/BeansPaM/b/notice/delete?n_no=" + ${n_no}
			}
		}
		
		function goUpdate(){
			alert("수정이 완료되었습니다.");
		}
	</script>
	<script src="/BeansPaM/js/menu.js"></script>
</body>
</html>
