<%@ page import="java.util.List" %>
<%@ page import="vo.QnaVO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="kr">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Q&amp;A</title>
	<link rel="stylesheet" href="/BeansPaM/css/qna.css">
</head>

<body>
	<main class="content">
		<div class="board">
			<!-- 테이블 제목 및 검색창을 포함할 컨테이너 -->
			<div class="table-header">
				<h2>Q&amp;A</h2> <!-- 테이블 좌측 제목 -->
				<div class="search-container"> <!-- 우측 상단에 검색창 배치 -->
					<form id="searchForm" action="<%=request.getContextPath()%>/b/qna/search" method="get">
						<select name="searchType" class="search-select">
							<option value="title" <%= "title".equals(request.getParameter("searchType")) ? "selected" : "" %>>제목</option>
							<option value="writer" <%= "writer".equals(request.getParameter("searchType")) ? "selected" : "" %>>작성자</option>
						</select>
						<input type="text" name="keyword" class="search-input" placeholder="검색어를 입력하세요" value="<%= request.getParameter("keyword") == null ? "" : request.getParameter("keyword") %>">
						<button type="submit" class="search-button">검색</button>
					</form>
				</div>
			</div>

			<table>
				<thead>
					<tr>
						<th>No</th>
						<th>제목</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>조회수</th>
					</tr>
				</thead>
				<tbody>
					<%
						List<QnaVO> qnaList = (List<QnaVO>) request.getAttribute("qnaList");
						if (qnaList != null && !qnaList.isEmpty()) {
							for (QnaVO qna : qnaList) {
								// 새로운 글인 경우 N 아이콘을 추가
								String postId = "post_" + qna.getQ_no();
					%>
					<tr>
						<td><%= qna.getQ_no() %></td>
						<td>
							<a href="<%=request.getContextPath()%>/b/qna/detail.do?q_no=<%= qna.getQ_no() %>">
								<%= qna.getQ_title().length() >= 10 ? qna.getQ_title().substring(0, 11) + "..." : qna.getQ_title() %>
								<!-- 새로운 글일 경우 N 아이콘 추가 -->
								<span class="new-icon" id="new-icon-<%= postId %>">N</span>
							</a>
						</td>
						<td><%= qna.getQ_writer() %></td>
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

			<!-- 페이지네이션 -->
			<%
				Integer currentPage = (Integer) request.getAttribute("currentPage");
				Integer maxPage = (Integer) request.getAttribute("maxPage");
				Integer startPage = (Integer) request.getAttribute("startPage");
				Integer endPage = (Integer) request.getAttribute("endPage");
				String searchType = request.getParameter("searchType");
				String keyword = request.getParameter("keyword");
				
				// 현재 검색 중인 경우와 아닌 경우 URL을 다르게 처리하기 위해 baseURL 설정
				String baseURL;
				if (searchType != null && keyword != null && !searchType.isEmpty() && !keyword.isEmpty()) {
					baseURL = request.getContextPath() + "/b/qna/search?searchType=" + searchType + "&keyword=" + keyword;
				} else {
					baseURL = request.getContextPath() + "/b/qna?";
				}
			%>

			<!-- 글쓰기 관련 버튼 -->
			<div class="button-wrapper">
				<div class="pagination">
					<% if (startPage != null && startPage > 1) { %>
		   				 <!-- 이전 페이지 링크 -->
		   				 <% if (searchType != null && keyword != null && !searchType.isEmpty() && !keyword.isEmpty()) { %>
		  				  <a href="<%= baseURL %>&page=<%= startPage - 1 %>">&laquo; 이전</a>
		  				 <% } else { %>
		  				  <a href="<%= baseURL %>&page=<%= startPage - 1 %>">&laquo; 이전</a>
		  				 <% } %>		  
		  				 
					<% } %>
				
					<% if (startPage != null && endPage != null) {
						for (int i = startPage; i <= endPage; i++) {
							if (i == currentPage) { %>
								<!-- 현재 페이지는 active 클래스 추가 -->
								<% if (searchType != null && keyword != null && !searchType.isEmpty() && !keyword.isEmpty()) { %>
				  				  <a href="<%= baseURL %>&page=<%= i %>" class="active"><%= i %></a>
				  				 <% } else { %>
				  				  <a href="<%= baseURL %>page=<%= i %>" class="active"><%= i %></a>
				  				 <% } %>	
							<% } else { %>
								<!-- 다른 페이지는 기본 링크 -->
								<% if (searchType != null && keyword != null && !searchType.isEmpty() && !keyword.isEmpty()) { %>
				  				 	<a href="<%= baseURL %>&page=<%= i %>"><%= i %></a>
				  				 <% } else { %>
				  				  	<a href="<%= baseURL %>page=<%= i %>"><%= i %></a>
				  				 <% } %>	
							<% }
						}
					} %>
				
					<% if (endPage != null && endPage < maxPage) { %>
						<!-- 다음 페이지 링크 -->
						<% if (searchType != null && keyword != null && !searchType.isEmpty() && !keyword.isEmpty()) { %>
		  					 <a href="<%= baseURL %>&page=<%= endPage + 1 %>">다음 &raquo;</a>
		  				 <% } else { %>
		  					 <a href="<%= baseURL %>page=<%= endPage + 1 %>">다음 &raquo;</a>
		  				 <% } %>	
					<% } %>
				</div>
				<div class="button-container">
					<button onclick="location.href='<%=request.getContextPath()%>/pages/qna_write.jsp'" class="search-button">작성하기</button>
				</div>
			</div>

		</div>
	</main>

	<!-- JavaScript로 게시글 클릭 시 색상 변경 및 상태 저장 -->
	<script>
		document.addEventListener("DOMContentLoaded", function () {
			const links = document.querySelectorAll("td:nth-child(2) a");

			links.forEach(link => {
				const postId = link.getAttribute("href").split("?q_no=")[1];
				const iconId = "new-icon-post_" + postId;

				// 페이지 로드 시 로컬스토리지에서 읽음 상태 확인
				if (localStorage.getItem(postId)) {
					const newIcon = document.getElementById(iconId);
					if (newIcon) {
						newIcon.style.display = "none"; // 읽은 게시물에서 N 아이콘 제거
					}
				}

				// 게시글 클릭 시 로컬스토리지에 저장하고 N 아이콘 제거
				link.addEventListener("click", function () {
					localStorage.setItem(postId, "read");

					const newIcon = document.getElementById(iconId);
					if (newIcon) {
						newIcon.style.display = "none";
					}
				});
			});

			// 검색어 입력 여부 확인
			document.getElementById("searchForm").addEventListener("submit", function (e) {
				const keywordInput = document.querySelector("input[name='keyword']");
				if (keywordInput.value.trim() === "") {
					e.preventDefault(); // 검색어가 없으면 폼 제출을 막음
					alert("검색어를 입력하세요.");
				}
			});
		});
	</script>
	<script src="/BeansPaM/js/menu.js"></script>
</body>

</html>
