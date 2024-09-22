<%@ page import="java.util.List" %>
<%@ page import="vo.QnaVO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="kr">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Q&A</title>
	<link rel="stylesheet" href="/BeansPaM/css/qna.css">
</head>

<body>
	<header>

	</header>
	<main>
		<div class="board">
			<h2>Q&amp;A</h2>

			<table>
				<thead>
					<tr>
						<th>No</th>
						<th>제목</th>
						<th>작성자</th>
						<th>구분</th>
						<th>작성일</th>
						<th>조회수</th>
					</tr>
				</thead>
				<tbody>
					<%
						List<QnaVO> qnaList = (List<QnaVO>) request.getAttribute("qnaList");
						if (qnaList != null && !qnaList.isEmpty()) {
							for (QnaVO qna : qnaList) {
					%>
					<tr>
						<td><%= qna.getQ_no() %></td>
						<td><a href="qnaDetail.do?q_no=<%= qna.getQ_no() %>"><%= qna.getQ_title() %></a></td>
						<td><%= qna.getQ_writer() %></td>
						<td><%= qna.getQ_right() %></td>
						<td><%= qna.getQ_date() %></td>
						<td><%= qna.getQ_views() %></td>
					</tr>
					<%
							}
						} else {
					%>
					<tr>
						<td colspan="6">등록된 게시글이 없습니다.</td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
			
			<!-- 버튼을 포함할 전체 컨테이너 -->
			<div class="button-wrapper">
				<!-- 검색 컨테이너 -->
				<div class="search-container">
					<select class="search-select">
						<option value="name">작성자</option>
						<option value="title">제목</option>
						<option value="content">내용</option>
					</select>
					<input type="text" class="search-input" placeholder="검색어를 입력하세요">
					<button class="search-button">검색</button>
				</div>

				<!-- 페이지네이션 -->
				<div class="pagination">
					<a href="#">1</a>
				</div>

				<!-- 글쓰기 관련 버튼 -->
				<div class="button-container">
					<button onclick="location.href='<%=request.getContextPath()%>/pages/qna_write.jsp'">작성하기</button>
				</div>
			</div>

		</div>
	</main>
</body>
</html>