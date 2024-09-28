<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kr">
<head>
    <meta charset="UTF-8">
    <title>사원 정보 열람</title>
    <link rel="stylesheet" type="text/css" href="/BeansPaM/css/admin.css">
</head>
<body>
	<main>
	    <h1>사원 정보</h1>
	
	    <table class="employee-details">
	        <tr>
	            <th>사원 번호</th>
	            <td>${member_detail.m_no}</td>
	        </tr>
	        <tr>
	            <th>아이디</th>
	            <td>${member_detail.m_id}</td>
	        </tr>
	        <tr>
	            <th>비밀번호</th>
	            <td>${member_detail.m_pw}</td>
	        </tr>
	        <tr>
	            <th>이름</th>
	            <td>${member_detail.m_name}</td>
	        </tr>
	        <tr>
	            <th>입사일</th>
	            <td>${member_detail.m_day}</td>
	        </tr>
	        <tr>
	            <th>직책</th>
	            <td>${member_detail.m_position}</td>
	        </tr>
	        <tr>
	            <th>전화번호</th>
	            <td>${member_detail.m_phone}</td>
	        </tr>
	        <tr>
	            <th>연차</th>
	            <td>${member_detail.m_leave}</td>
	        </tr>
	        <tr>
	            <th>급여(월급)</th>
	            <td>${member_detail.m_salary}</td>
	        </tr>
	        <tr>
	            <th>부서</th>
	            <td>${member_detail.m_dept}</td>
	        </tr>
	        <tr>
	            <th>이메일</th>
	            <td>${member_detail.m_email}</td>
	        </tr>
	    </table>
	
	    <!-- 뒤로가기 버튼 -->
	    <div class="back-button">
	        <a href="admin">
	            <button>뒤로가기</button>
	        </a>
	    </div>
	</main>
	<script src="/BeansPaM/js/menu.js"></script>
</body>
</html>
