<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="vo.QnaVO" %>
<%
	Cookie[] cookies = request.getCookies();
	String userName = null;

	if (cookies != null) {
		for (Cookie cookie : cookies) {
			if ("mem_info".equals(cookie.getName())) {
				String[] memInfo = cookie.getValue().split("\\+");
				if (memInfo.length > 1) {
					userName = memInfo[1];
				}
				break;
			}
		}
	}
	
	if (userName == null) {
		response.sendRedirect("/BeansPaM");
		return;
	}
%>
<!DOCTYPE html>
<html lang="kr">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>게시글 작성</title>
	<link rel="stylesheet" href="/BeansPaM/css/qna_write.css">
	<script src="/BeansPaM/js/fontawesome.js"></script>
	<script src="/BeansPaM/js/jquery.js"></script>
</head>

<body>
	<div class="chart"></div>
	<main>
		<div class="board-write">
			<h2>게시글 작성</h2>

			<!-- 작성된 데이터를 b/qna/submit으로 전송할 form -->
			<form id="writeForm" action="/BeansPaM/b/qna/submit" method="post">
				<table class="write-table">
					<tr>
						<th class="center-align">제목</th>
						<td><input type="text" class="input-title" name="title" placeholder="제목을 입력하세요" required></td>
					</tr>
					<tr>
						<th class="center-align">작성자</th>
<!--                         <td><input type="text" class="input-title" name="writer" placeholder="작성자명을 입력하세요" required></td> -->
						<td><%=userName %></td>
					</tr>
					<tr>
						<th class="center-align">내용</th>
						<td><textarea class="input-content" name="content" placeholder="내용을 입력하세요" required></textarea></td>
					</tr>
				</table>

				<!-- 제출 및 나가기 버튼 -->
				<div class="button-section">
					<button type="button" class="btn-exit" onclick="exitPost()">나가기</button>
					<button type="button" class="btn-submit" onclick="onSubmit();">등록</button>
				</div>
			</form>
		</div>
	</main>

	<script>
		var onSubmit = function(){
			if(confirm("게시글을 등록하시겠습니까?")){
				$('#writeForm').submit();			
			};
		}

		// 나가기 버튼 클릭 시 게시글 리스트로 이동
		function exitPost() {
			window.location.href = '<%=request.getContextPath()%>/b/qna'; // Q&A 목록으로 이동
		}
	</script>
	<script src="/BeansPaM/js/menu.js"></script>
</body>

</html>
