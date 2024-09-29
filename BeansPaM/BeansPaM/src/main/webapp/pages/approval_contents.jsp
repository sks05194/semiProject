<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="kr">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>결재 게시판</title>
	<link rel="stylesheet" href="/BeansPaM/css/approval_contents.css">
	<script src="/BeansPaM/js/jquery.js"></script>
</head>
<body>
	<div class="qna-container">
		<h2>결재 게시판</h2>
		<c:choose>
			<c:when test="${not empty getPageContents}">
				<c:forEach var="contents" items="${getPageContents}">
					<div class="qna-detail" data-dno="${contents.getD_no()}">
						<table style="width: 100%">
							<tr>
								<td><strong>결재 번호</strong></td>
								<td>${contents.getD_no()}</td>
							</tr>
							<tr>
								<td><strong>결재 제목</strong></td>
								<td>${contents.getD_title()}</td>
							</tr>
							<tr>
								<td><strong>결재 종류</strong></td>
								<td>${contents.getD_class()}</td>
							</tr>
							<tr>
								<td><strong>결재 작성자</strong></td>
								<td>${contents.getM_name()}${contents.getM_position()}</td>
							</tr>
							<tr>
								<td><strong>결재 작성일</strong></td>
								<td>${contents.getD_request()}</td>
							</tr>
							<tr>
								<c:choose>
									<c:when test="${contents.getD_m_no() eq 0}">
										<td><strong>결재 승인자</strong></td>
										<td></td>
									</c:when>
									<c:otherwise>
										<td><strong>결재 승인자</strong></td>
										<td><c:out value="${m_name}"/></td>
									</c:otherwise>
								</c:choose>
							</tr>
							<tr>
								<c:choose>
									<c:when test="${not empty contents.getD_response()}">
										<td><strong>결재 승인일</strong></td>
										<td><c:out value="${contents.getD_response()}" /></td>
									</c:when>
									<c:otherwise>
										<td><strong>결재 승인일</strong></td>
										<td></td>
									</c:otherwise>
								</c:choose>
							</tr>
							<tr>
								<td><strong>결재 파일</strong></td>
								<td><a href="approval_contents_file_action?filename=${contents.getFilename()}">${contents.getFilename()}</a></td>
							</tr>
						</table>
					</div>
					<p class="qna-content">${contents.getD_content()}</p>
				</c:forEach>
				<div class="button-section">
					<button class="btn-delete" onclick="deletePost()">삭제하기</button>
					<c:forEach var="confirm" items="${getName}">
						<c:if test="${confirm.m_name eq '관리자'}">
							<button class="btn-confirm" onclick="confirmPost()">결재승인</button>
							<div class="div-confirm1" data-dno="${confirm.m_position}"></div>
							<div class="div-confirm2" data-dno="${confirm.m_name}"></div>
						</c:if>
					</c:forEach>
					<button class="btn-exit" onclick="exitPost()">나가기</button>
				</div>
			</c:when>
			<c:otherwise>
				<p>게시글을 찾을 수 없습니다.</p>
			</c:otherwise>
		</c:choose>
	</div>
	<script>
		<%-- 게시글 삭제 --%>
		function deletePost() {
			let d_no = document.querySelector(".qna-detail").getAttribute("data-dno");
			if (confirm('정말로 이 게시글을 삭제하시겠습니까?')) {
				window.location.href = 'approval_contents_del_action?d_no=' + d_no;
			}
		}

		<%-- 결재승인 --%>
		function confirmPost() {
			let m_position = document.querySelector(".div-confirm1").getAttribute("data-dno");
			let m_name = document.querySelector(".div-confirm2").getAttribute("data-dno");
			let d_no = document.querySelector(".qna-detail").getAttribute("data-dno");
			window.location.href = 'approval_contents_confirm_action?d_no=' + d_no + '&m_name=' + m_name + '&m_position=' + m_position;
		}
		
		<%-- 나가기 --%>
		function exitPost() {
			window.location.href = 'approval_main_action'; 
		}
	</script>
</body>
</html>