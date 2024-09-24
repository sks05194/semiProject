<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.SalaryVO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html lang="kr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>salary</title>
    <link rel="stylesheet" href="/BeansPaM/css/workday.css">
    <script src="/BeansPaM/js/fontawsome.js"></script>
    <script src="/BeansPaM/js/jquery.js"></script>
</head>

<body>
<% 
    List<SalaryVO> salaryList = (List<SalaryVO>) request.getAttribute("salaryList");
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
            if (salaryList != null && salaryList.size() > 0) {
                for (int i = 0; i < salaryList.size(); i++) {
                    SalaryVO salary = salaryList.get(i);
            %>
            <tr>
                <td><%= salary.getSal_date() %></td> <!-- 지급일 -->
                <td><%= salary.getSal_salary() %>원</td> <!-- 급여액 -->
                <td><%= String.format("%.0f", salary.getSal_salary() * 0.1) %>원</td> <!-- 공제액 (급여의 10%) -->
                <td><%= String.format("%.0f", salary.getSal_salary() * 0.9) %>원</td> <!-- 총 지급액 -->
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
		<div class="buttons">
			<button onclick="changePage('prev')">이전</button>
			<button onclick="changePage(1)">1</button>
			<button onclick="changePage(2)">2</button>
			<button onclick="changePage(3)">3</button>
			<button onclick="changePage(4)">4</button>
			<button onclick="changePage(5)">5</button>
			<button onclick="changePage(6)">6</button>
			<button onclick="changePage(7)">7</button>
			<button onclick="changePage(8)">8</button>
			<button onclick="changePage(9)">9</button>
			<button onclick="changePage(10)">10</button>
			<button onclick="changePage('next')">다음</button>
		</div>
	</main>

	<footer> </footer>

	<script src="/BeansPaM/js/fms_menu.js"></script>
</body>

</html>
