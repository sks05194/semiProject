<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.ArrayList"%>
<%@page import="vo.NoticeVO"%>
<%@page import="java.sql.*, java.util.*"%>

<%
// DB 연결 정보
String jdbcDriver = "oracle.jdbc.driver.OracleDriver"; // Oracle 드라이버
String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:XE"; // Oracle DB URL
String jdbcUser = "BEANSPAM"; // DB 사용자
String jdbcPass = "1111"; // DB 비밀번호

Connection con = null;
PreparedStatement ps1 = null;
ResultSet rs1 = null;

// 현재 페이지 가져오기 (파라미터 없을 경우 기본값 1)

int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
int recordsPerPage = 10; // 한 페이지에 보여줄 게시글 수
int startRecord = (currentPage - 1) * recordsPerPage + 1; // 시작 게시글 번호
int endRecord = currentPage * recordsPerPage; // 끝 게시글 번호

int totalRecords = 0; // 전체 게시글 수
int totalPages = 0; // 전체 페이지 수

// 공지사항 데이터를 저장할 리스트
List<Map<String, Object>> noticeList = new ArrayList<>();

try {
	// DB 연결
	Class.forName(jdbcDriver);
	con = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPass);

	// 전체 공지사항 개수 조회
	String countSql = "SELECT COUNT(*) FROM NOTICE WHERE N_DELETE_YN = 'N'";
	ps1 = con.prepareStatement(countSql);
	rs1 = ps1.executeQuery();
	if (rs1.next()) {
		totalRecords = rs1.getInt(1); // 전체 레코드 수
	}

	totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage); // 전체 페이지 수 계산

	/* keyword 검색 */
	String keyword = request.getParameter("keyword");

	String sql = "SELECT * FROM " + "(SELECT ROWNUM AS NO, N_NO, N_TITLE, N_VIEWS, N_R_DATE " + "FROM ( "
	+ "SELECT N_NO, N_TITLE, N_VIEWS, N_R_DATE FROM NOTICE " + "WHERE N_DELETE_YN = 'N' ";

	// 검색어가 있을 경우 WHERE 절에 추가
	if (keyword != null && !keyword.trim().isEmpty()) {
		sql += "AND N_TITLE LIKE ? ";
	}

	sql += " ORDER BY N_NO DESC ) WHERE ROWNUM <= ? " // 최대 조회 범위 설정
	+ ") WHERE NO BETWEEN ? AND ? ";

	ps1 = con.prepareStatement(sql);

	if (keyword != null && !keyword.trim().isEmpty()) {
		ps1.setString(1, "%" + keyword + "%");
		ps1.setInt(2, totalRecords); // 최대 게시글 수
		ps1.setInt(3, startRecord); // 시작 게시글
		ps1.setInt(4, endRecord); // 끝 게시글
	} else {
		ps1.setInt(1, totalRecords); // 최대 게시글 수
		ps1.setInt(2, startRecord); // 시작 게시글
		ps1.setInt(3, endRecord); // 끝 게시글
	}

	rs1 = ps1.executeQuery();

	while (rs1.next()) {
		Map<String, Object> notice = new HashMap<>();
		notice.put("no", rs1.getString("NO"));
		notice.put("n_no", rs1.getInt("N_NO"));
		notice.put("n_title", rs1.getString("N_TITLE"));
		notice.put("n_views", rs1.getInt("N_VIEWS"));
		notice.put("n_r_date", rs1.getDate("N_R_DATE"));

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
<%
// 검색 필터 확인 (선택된 검색 필터 출력)
String searchType = request.getParameter("search_notice");
String keyword = request.getParameter("keyword");
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
							<option value="n_title">제목</option>
							<!-- 		            <option value="n_no">글 번호</option> -->
						</select>
						<div class="search">
							<input type="search" name="keyword" id="keyword"
								placeholder="검색할 내용 입력" value="${param.keyword}">
						</div>
						<input type="submit" value="검색">
					</form>
				</div>
			</div>
			<table border="1">
				<thead>
					<tr>
						<th>No.</th>
						<th>제목</th>
						<th>작성자</th>
						<th>작성일</th>
						<th>조회수</th>
					</tr>
				</thead>
				<tbody>
					<%
					if (noticeList != null && !noticeList.isEmpty()) {
						for (Map<String, Object> notice : noticeList) {
					%>
						<tr
							onClick="location.href='/BeansPaM/b/notice/detail?n_no=<%=notice.get("n_no")%>';">
							<td><%=notice.get("n_no")%></td>
							<td><%=notice.get("n_title")%></td>
							<td>관리자</td>
							<td><%=notice.get("n_r_date")%></td>
							<td><%=notice.get("n_views")%></td>
						</tr>
					<% }
					} %>
				</tbody>
			</table>

			<!-- 페이지네이션 -->
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
				<div class="button-container"
					onclick="location.href='/BeansPaM/b/notice/write'">
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
