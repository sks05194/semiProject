<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.ArrayList"%>
<%@page import="vo.MemberVO"%>
<%@page import="dao.MemberDAO"%>
<%!MemberDAO dao = new MemberDAO(); %>
<!DOCTYPE html>
<html lang="kr">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>admin</title>

	<link rel="stylesheet" href="/BeansPaM/css/admin.css">
	<script src="/BeansPaM/js/fontawsome.js"></script>
	<script src="/BeansPaM/js/jquery.js"></script>
</head>

<body>
	<div class="chart">
		<h1>관리자 메뉴</h1>

		<div class="nav">
			<h3>BeansPaM</h3>
			<img src="/BeansPaM/img/logo.png" alt="">
		</div>
	</div>

	<main>
		<div class="search">
			<select name="" id="search">
				<option value="">사번</option>
				<option value="">아이디</option>
				<option value="">이름</option>
				<option value="">입사일자</option>
				<option value="">전화번호</option>
				<option value="">연차</option>
				<option value="">열람</option>
			</select>
			<input type="text" id="serchinput">
			<button onclick="">검색</button>
		</div>
		
		<table id="employeeTable">
			<tr>
				<th>사번</th>
				<th>아이디</th>
				<th>이름</th>
				<th>입사일자</th>
				<th>전화번호</th>
				<th>연차</th>
				<th>열람</th>
			</tr>
			
			<c:forEach var="i" begin="0" end="${members.size() - 1}" step="1">
				<tr>
					<td>${members[i].m_no}</td>
					<td>${members[i].m_id}</td>
					<td>${members[i].m_name}</td>
					<td>${members[i].m_day.toString()}</td>
					<td>${members[i].m_phone}</td>
					<td>${members[i].m_leave}일</td>
					<td>
						<button type="button" id="info" onclick="location.href='admin_detail?m_no=${members[i].m_no}'">정보</button>
						<button type="button" id="del" onclick="location.href='admin_del?m_no=${members[i].m_no}'">삭제</button>
					</td>
				</tr>
			</c:forEach>
			
			<c:forEach var="i" begin="0" end="2" step="1">
				<tr>
					<td>empty</td>
					<td>empty</td>
					<td>empty</td>
					<td>empty</td>
					<td>empty</td>
					<td>empty</td>
					<td>empty</td>
				</tr>
			</c:forEach>
			
		</table>
	</main>

	<script>
		const originalEmployees = [
			{ empnum: '33334444', id: '강동준', name: '기획부', date: '사원', tel: 's@coffee.com', break: '02-3333-4444', open: '열람' },

			{ empnum: '33334444', id: '강동준', name: '기획부', date: '사원', tel: 's@coffee.com', break: '02-3333-4444', open: '열람' },

			{ empnum: '33334444', id: '강동준', name: '기획부', date: '사원', tel: 's@coffee.com', break: '02-3333-4444', open: '미열람' },

			{ empnum: '33334444', id: '강동준', name: '기획부', date: '사원', tel: 's@coffee.com', break: '02-3333-4444', open: '열람' }
		];
	</script>

	<script src="/BeansPaM/js/fms_menu.js"></script>
</body>

</html>