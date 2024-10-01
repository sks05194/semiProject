<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="vo.AttendanceVO"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.ParseException"%>
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

		<table class="mTable">
			<tr class="mbclose">
				<th	scope="col">출근일자</th>
				<th scope="col">출근시간</th>
				<th scope="col">퇴근시간</th>
				<th scope="col">특이사항</th>
			</tr>

			<%
			@SuppressWarnings("unchecked")
			ArrayList<AttendanceVO> attendanceList = (ArrayList<AttendanceVO>) request.getAttribute("attendanceList");

			int pageSize = 10;
			int totalRecords = attendanceList != null ? attendanceList.size() : 0;
			int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

			int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
			int startRecord = (currentPage - 1) * pageSize;
			int endRecord = Math.min(startRecord + pageSize, totalRecords);

			SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat timeFormat2 = new SimpleDateFormat("HH:mm:ss");

			if (attendanceList != null) {
				for (int i = startRecord; i < endRecord; i++) {
					AttendanceVO attendance = attendanceList.get(i);
					
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
						<td scope="row" data-label="출근일자"><%= attendance.getA_workdate() %></td>
						<td data-label="출근시간"><%= checkinTime != null ? timeFormat2.format(checkinTime) : "Null" %></td>
						<td data-label="퇴근시간"><%= checkoutTime != null ? timeFormat2.format(checkoutTime) : "Null" %></td>
						<td data-label="특이사항"><%= attendance.getA_issue() != null ? attendance.getA_issue() : "Null" %></td>
					</tr>
				<% }
			} else { %>
				<tr>
					<td colspan="4">근무 정보가 없습니다.</td>
				</tr>
			<% } %>

		
		</table>

		<div class="pagination">
			<% if (currentPage > 1) { %>
				<a href="?page=<%= currentPage - 1 %>">이전</a>
			<% } %>

			<% for (int i = 1; i <= totalPages; i++) { %>
				<a href="?page=<%= i %>" <%= i == currentPage ? "class='active'" : "" %>><%= i %></a>
			<% } %>

			<% if (currentPage < totalPages) { %>
				<a href="?page=<%= currentPage + 1 %>">다음</a>
			<% } %>
		</div>
	</main>

	<script src="/BeansPaM/js/menu.js"></script>
</body>

</html>
