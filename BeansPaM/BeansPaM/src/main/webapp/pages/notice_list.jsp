<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.ArrayList"%>
<%@page import="vo.NoticeVO"%>
<%@page import="java.sql.*, java.util.*" %>

<%
	// DB 연결 정보와 변수 초기화는 기존과 동일
	String jdbcDriver = "oracle.jdbc.driver.OracleDriver"; // Oracle 드라이버
	String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE"; // Oracle DB URL
	String jdbcUser = "BEANSPAM"; // DB 사용자
	String jdbcPass = "1111"; // DB 비밀번호

	Connection con = null;
	PreparedStatement ps1 = null;
	ResultSet rs1 = null;

	int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
	int recordsPerPage = 10; // 한 페이지에 보여줄 게시글 수
	int startRecord = (currentPage - 1) * recordsPerPage + 1; // 시작 게시글 번호
	int endRecord = currentPage * recordsPerPage; // 끝 게시글 번호

	int totalRecords = 0; // 전체 게시글 수
	int totalPages = 0; // 전체 페이지 수

	List<Map<String, Object>> noticeList = new ArrayList<>();
	boolean isEmpty = true; // 검색 결과가 없는지 여부를 나타내는 플래그
	
	try {
		Class.forName(jdbcDriver);
		con = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);

		String keyword = request.getParameter("keyword");
		String searchType = request.getParameter("search_notice"); // 검색할 필드(제목 또는 내용)

		// 전체 공지사항 개수 조회 (검색어 포함)
		String countSql = "SELECT COUNT(*) FROM NOTICE WHERE N_DELETE_YN = 'N'";
		if (keyword != null && !keyword.trim().isEmpty()) {
			if ("n_content".equals(searchType)) {
				countSql += " AND N_CONTENT LIKE ?";
			} else {
				countSql += " AND N_TITLE LIKE ?";
			}
		}
		
		ps1 = con.prepareStatement(countSql);
		if (keyword != null && !keyword.trim().isEmpty()) {
			ps1.setString(1, "%" + keyword + "%");
		}

		rs1 = ps1.executeQuery();
		if (rs1.next()) {
			totalRecords = rs1.getInt(1); // 전체 레코드 수
		}

		totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage); // 전체 페이지 수 계산

		
		// 게시글 조회 (페이징 처리 포함)
		String sql = "SELECT * FROM ( " +
		             "  SELECT RN, N_NO, N_TITLE, N_VIEWS, N_R_DATE, N_IMPORTANT_YN " +
		             "  FROM ( " +
		             "    SELECT ROWNUM AS RN, N_NO, N_TITLE, N_VIEWS, N_R_DATE, N_IMPORTANT_YN " +
		             "    FROM ( " +
		             "      SELECT N_NO, N_TITLE, N_VIEWS, N_R_DATE,CASE WHEN N_IMPORTANT_YN = 'Y' THEN 1 ELSE 0 END AS N_IMPORTANT_YN " +
		             "      FROM NOTICE " +
		             "      WHERE N_DELETE_YN = 'N' ";
		             
		// 검색 조건 추가 (있을 경우)
		if (keyword != null && !keyword.trim().isEmpty()) {
		    if ("n_content".equals(searchType)) {
		        sql += " AND N_CONTENT LIKE ? ";
		    } else {
		        sql += " AND N_TITLE LIKE ? ";
		    }
		}
		
		// 공지사항 상단 고정 + 번호순 정렬
		sql += " ORDER BY N_IMPORTANT_YN DESC, N_NO DESC " +
		       "      ) " + // 정렬 후
		       "    ) WHERE ROWNUM <= ? " + // 끝 게시글 번호 지정
		       "  ) WHERE RN >= ?"; // 시작 게시글 번호 지정
					   
		ps1 = con.prepareStatement(sql);

		if (keyword != null && !keyword.trim().isEmpty()) {
			ps1.setString(1, "%" + keyword + "%");
			ps1.setInt(2, endRecord);  // 최대 조회할 게시글 수
			ps1.setInt(3, startRecord);  // 시작 게시글 번호
		} else {
			ps1.setInt(1, endRecord);  // 최대 조회할 게시글 수
			ps1.setInt(2, startRecord);  // 시작 게시글 번호
		}

		rs1 = ps1.executeQuery();

		while (rs1.next()) {
			isEmpty = false; // 데이터가 있으면 플래그를 false로 설정
			Map<String, Object> notice = new HashMap<>();
			notice.put("no", rs1.getString("RN")); // 행 번호
			notice.put("n_no", rs1.getInt("N_NO"));
			notice.put("n_title", rs1.getString("N_TITLE"));
			notice.put("n_views", rs1.getInt("N_VIEWS"));
			notice.put("n_r_date", rs1.getDate("N_R_DATE"));
			notice.put("n_important_yn", rs1.getString("N_IMPORTANT_YN"));

			noticeList.add(notice); // 리스트에 공지사항 추가
		}
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		if (rs1 != null) try { rs1.close(); } catch (SQLException ignored) {}
		if (ps1 != null) try { ps1.close(); } catch (SQLException ignored) {}
		if (con != null) try { con.close(); } catch (SQLException ignored) {}
	}
%>

<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="/BeansPaM/css/notice_list.css">
   	<script src="/BeansPaM/js/fontawsome.js"></script>
	<script src="/BeansPaM/js/jquery.js"></script>
	<title>공지사항</title>
</head>
<body>
	<main class="content">
		<div class="board">
			<div class="actions-container">
				<h2>공지사항</h2>
				<div class="search-container">
					<form method="get" action="/BeansPaM/b/notice">
						<select name="search_notice" id="search_notice">
							<option value="n_title" <c:if test="${param.search_notice == 'n_title'}">selected</c:if>>제목</option>
							<option value="n_content" <c:if test="${param.search_notice == 'n_content'}">selected</c:if>>내용</option>
						</select>
						<div class="search">
							<input type="search" name="keyword" id="keyword" placeholder="검색할 내용 입력" value="${param.keyword}">
						</div>
						<input type="submit" value="검색">
					</form>
				</div>
			</div>

			<table border="1">
				<thead>
					<tr>
						<th>No.</th>
						<th style="text-align:left">제목</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>조회수</th>
					</tr>
				</thead>
				<tbody>
					<% if (isEmpty) { // 검색 결과가 없을 경우 %>
						<tr>
							<td colspan="5" style="text-align: center;">검색된 데이터가 없습니다.</td>
						</tr>
					<% } else { // 검색 결과가 있을 경우
						for (Map<String, Object> notice : noticeList) {
					%>
						<tr onClick="location.href='/BeansPaM/b/notice/detail?n_no=<%= notice.get("n_no") %>';">

							<!-- No -->
							<td><%= notice.get("n_no") %></td>
			
							<!-- 제목 (긴 텍스트는 ...으로 표시) -->
							<td class="title" style="text-align:left;">
								<% if ("1".equals(notice.get("n_important_yn"))) { %>
							       <p><b style="color:red;">[중요]</b>
						  	 	<% } %>
							    <%= notice.get("n_title") %></p>
							</td>
			
							<!-- 작성자 -->
							<td>관리자</td>
			
							<!-- 작성일 -->
							<td><%= notice.get("n_r_date") %></td>
			
							<!-- 조회수 -->
							<td><%= notice.get("n_views") %></td>
						</tr>
					<% }
					} %>
				</tbody>
			</table>

			<div class="bottom-buttons">
			<div class="pagination">
				<% if (currentPage > 1) { %>
					<a href="?page=<%=currentPage - 1%>">이전</a>
				<% } %>

				<% for (int i = 1; i <= totalPages; i++) { %>
					<% if (i == currentPage) { %>
						<span><%=i%></span>
					<% } else { %>
						<a href="?page=<%=i%>"><%=i%></a>
					<% } %>
				<% } %>

				<% if (currentPage < totalPages) { %>
					<a href="?page=<%=currentPage + 1%>">다음</a>
				<% } %>
			</div>
		
			<c:if test='${loginName == "관리자"}'>	
				 <div class="write-button" onclick="location.href='/BeansPaM/b/notice/write'">
					 <button>글쓰기</button>
				 </div>
			</c:if>
	   </div>
	</main>
	<script>
		function goDetail(n_no) {
			location.href = "/BeansPaM/b/notice/detail?n_no=" + n_no;
		}
	</script>
	<script src="/BeansPaM/js/menu.js"></script>
</body>
</html>