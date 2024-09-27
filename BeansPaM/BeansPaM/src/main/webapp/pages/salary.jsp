<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="vo.SalaryVO"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.DecimalFormat" %>
<!DOCTYPE html>
<html lang="kr">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>salary</title>
<link rel="stylesheet" href="/BeansPaM/css/workday.css">
</head>

<body>	
	<script src="/BeansPaM/js/menu.js"></script>
	<%
	new DecimalFormat("#,###");
	ArrayList<SalaryVO> salaryList = (ArrayList<SalaryVO>) request.getAttribute("salaryList");
	
	// 페이지 당 항목 수 설정
	int pageSize = 8;
	int totalRecords = salaryList != null ? salaryList.size() : 0;
	int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

	// 현재 페이지 번호 가져오기 (최소값 1, 최대값 totalPages로 제한)
	int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
	if (currentPage < 1)
		currentPage = 1;
	if (currentPage > totalPages)
			currentPage = totalPages;

	// 표시할 데이터의 시작 인덱스와 끝 인덱스 계산
	int startRecord = (currentPage - 1) * pageSize;
	int endRecord = Math.min(startRecord + pageSize, totalRecords);
	%>

	<div class="chart"></div>
	<main>
		<h1>급여표</h1>

		<table>
			<tr>
				<th>지급일</th>
				<th>급여액</th>
				<th>공제액</th>
				<th>총 지급액</th>
			</tr>

			<!-- 급여 데이터 출력 -->
			<%
			if (salaryList != null && totalRecords > 0) {
				for (int i = startRecord; i < endRecord; i++) {
					SalaryVO salary = salaryList.get(i);
			%>
			<tr>
				<td><%=salary.getSal_date()%></td>
				<!-- 지급일 -->
				<td><%=new DecimalFormat("#,###").format(salary.getSal_salary())%>원</td>
				<!-- 급여액 -->
				<td><%=new DecimalFormat("#,###").format(salary.getSal_salary() * 0.1)%>원</td>
				<!-- 공제액 (급여의 10%) -->
				<td><%=new DecimalFormat("#,###").format(salary.getSal_salary() * 0.9)%>원</td>
				<!-- 총 지급액 -->
			</tr>
			<%
			}
			} else {
			%>
			<tr>
				<td colspan="4">데이터가 없습니다.</td>
			</tr>
			<%
			}
			%>

		</table>

		<!-- 페이지 전환 버튼 -->
		<div class="pagination">
			<!-- 이전 페이지 버튼 -->
			<%
			if (currentPage > 1) {
			%>
			<a href="?page=<%=currentPage - 1%>">이전</a>
			<%
			}
			%>

			<!-- 페이지 번호 링크 -->
			<%
			for (int i = 1; i <= totalPages; i++) {
			%>
			<a href="?page=<%=i%>"
				<%=i == currentPage ? "class='active'" : ""%>><%=i%></a>
			<%
			}
			%>

			<!-- 다음 페이지 버튼 -->
			<%
			if (currentPage < totalPages) {
			%>
			<a href="?page=<%=currentPage + 1%>">다음</a>
			<%
			}
			%>
		</div>
	</main>

</body>

</html>
