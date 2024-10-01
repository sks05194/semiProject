<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.AttendanceVO"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.ParseException"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.DecimalFormat" %>
<!DOCTYPE html>
<html lang="kr">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>workday</title>
	<link rel="stylesheet" href="/BeansPaM/css/workday.css">
</head>

<body>
	<div class="chart"></div>
	<main>
		<h1>근무표</h1>
		<div class="scheduler">
			<form method="get" action="">
				<label for="start-date">기간 선택: </label>
				<input type="date" id="start-date" name="start-date" title="검색할 시작 날짜를 선택하세요." max="<%= new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>">
				<span> ~ </span>
				<input type="date" id="end-date" name="end-date" title="검색할 종료 날짜를 선택하세요." max="<%= new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>">
				<button type="submit" class="dateaction">조회</button>
			</form>
		</div>
		<table class="mTable">
			<tr class="mbclose">
				<th scope="col">출근일자</th>
				<th scope="col">출근시간</th>
				<th scope="col">퇴근시간</th>
				<th scope="col">특이사항</th>
			</tr>

			<%
			@SuppressWarnings("unchecked")
			ArrayList<AttendanceVO> attendanceList = (ArrayList<AttendanceVO>) request.getAttribute("attendanceList");
			List<AttendanceVO> filteredAttendanceList = new ArrayList<>();

			// 날짜 필터링 로직
			String startDateParam = request.getParameter("start-date");
			String endDateParam = request.getParameter("end-date");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = null, endDate = null;

			if (startDateParam != null && endDateParam != null) {
				try {
					startDate = sdf.parse(startDateParam);
					endDate = sdf.parse(endDateParam);
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (attendanceList != null && startDate != null && endDate != null) {
					for (AttendanceVO attendance : attendanceList) {
						Date workDate = attendance.getA_workdate();
						if (!workDate.before(startDate) && !workDate.after(endDate)) {
							filteredAttendanceList.add(attendance);
						}
					}
				}
			}

			// 날짜 필터링이 없으면 전체 데이터 사용
			if (filteredAttendanceList.isEmpty() && (startDateParam == null || endDateParam == null)) {
				filteredAttendanceList = attendanceList;
			}

			// 페이지 당 항목 수 설정
			int pageSize = 10;
			int totalRecords = filteredAttendanceList != null ? filteredAttendanceList.size() : 0;
			int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

			// 현재 페이지 번호 가져오기 (최소값 1, 최대값 totalPages로 제한)
			int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
			if (currentPage < 1) {
				currentPage = 1;
			}
			if (currentPage > totalPages) {
				currentPage = totalPages;
			}

			// 표시할 데이터의 시작 인덱스와 끝 인덱스 계산
			int startRecord = (currentPage - 1) * pageSize;
			int endRecord = Math.min(startRecord + pageSize, totalRecords);

			SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat timeFormat2 = new SimpleDateFormat("HH:mm:ss");

			// 데이터 출력
			if (filteredAttendanceList != null && totalRecords > 0) {
				for (int i = startRecord; i < endRecord; i++) {
					AttendanceVO attendance = filteredAttendanceList.get(i);

					String checkinTimeStr = attendance.getA_checkin() != null ? attendance.getA_checkin().toString() : null;
					String checkoutTimeStr = attendance.getA_checkout() != null ? attendance.getA_checkout().toString() : null;

					Date checkinTime = null;
					Date checkoutTime = null;

					try {
						if (checkinTimeStr != null) {
							checkinTime = timeFormat.parse(checkinTimeStr);
						}
						if (checkoutTimeStr != null) {
							checkoutTime = timeFormat.parse(checkoutTimeStr);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					} %>
					<tr>
						<td scope="row" data-label="출근일자"><%= sdf.format(attendance.getA_workdate()) %></td>
						<td data-label="출근시간"><%= checkinTime != null ? timeFormat2.format(checkinTime) : "----" %></td>
						<td data-label="퇴근시간"><%= checkoutTime != null ? timeFormat2.format(checkoutTime) : "----" %></td>
						<td data-label="특이사항"><%= attendance.getA_issue() != null ? attendance.getA_issue() : "" %></td>
					</tr>
				<% }
			} else { %>
				<tr>
					<td colspan="4">근무 정보가 없습니다.</td>
				</tr>
			<% } %>
		</table>

		<!-- 페이지 전환 버튼 -->
		<div class="pagination">
			<!-- 이전 페이지 버튼 -->
			<% if (currentPage > 1) { %>
				<a href="?page=<%= currentPage - 1 %><%= startDateParam != null ? "&start-date=" + startDateParam + "&end-date=" + endDateParam : "" %>">이전</a>
			<% } %>

			<!-- 페이지 번호 링크 -->
			<% for (int i = 1; i <= totalPages; i++) { %>
				<a href="?page=<%= i %><%= startDateParam != null ? "&start-date=" + startDateParam + "&end-date=" + endDateParam : "" %>" <%= i == currentPage ? "class='active'" : "" %>><%= i %></a>
			<% } %>

			<!-- 다음 페이지 버튼 -->
			<% if (currentPage < totalPages) { %>
				<a href="?page=<%= currentPage + 1 %><%= startDateParam != null ? "&start-date=" + startDateParam + "&end-date=" + endDateParam : "" %>">다음</a>
			<% } %>
		</div>
	</main>

	<script src="/BeansPaM/js/menu.js"></script>
</body>

</html>
