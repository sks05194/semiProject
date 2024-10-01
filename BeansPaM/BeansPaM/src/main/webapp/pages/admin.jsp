<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.lang.Math"%>
<%@page import="vo.MemberVO"%>
<%@page import="dao.MemberDAO"%>
<%!MemberDAO dao = new MemberDAO(); %>
<!DOCTYPE html>
<html lang="kr">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>관리자 메뉴 - 사원 목록</title>

	<link rel="stylesheet" href="/BeansPaM/css/admin.css">
	<script src="/BeansPaM/js/fontawesome.js"></script>
	<script src="/BeansPaM/js/jquery.js"></script>
</head>

<body>
	<main>
		<h1>관리자 메뉴</h1>

		<div class="form-and-button">
			<div class="search">
				<form method="get">
					<select name="target" id="target">
						<option value="m_no" selected>사번</option>
						<option value="m_id">아이디</option>
						<option value="m_name">이름</option>
						<option value="m_dept">부서</option>
					</select>
					<input type="text" name="keyword" id="keyword">
					<input type="submit" value="검색">
				</form>
			</div>
	
		    <div class="employee-register">
		        <a href="admin_regist">
		            <button id="register">등록</button>
		        </a>
		    </div>
	    </div>

		<table id="employeeTable">
			<tr>
				<th>사번</th>
				<th>아이디</th>
				<th>이름</th>
				<th>입사일자</th>
				<th>직책</th>
				<th>전화번호</th>
				<th>부서</th>
				<th>연차</th>
				<th>열람</th>
			</tr>

			<c:if test="${not empty members and members.size() ne 0}">
				<c:forEach var="i" begin="0" end="${members.size() - 1}" step="1">
					<tr>
						<td>${members[i].m_no}</td>
						<td>${members[i].m_id}</td>
						<td>${members[i].m_name}</td>
						<td>${members[i].m_day.toString()}</td>
						<td>${members[i].m_position}</td>
						<td>${members[i].m_phone}</td>
						<td>${members[i].m_dept}</td>
						<td>${members[i].m_leave}일</td>
						<td>
							<button type="button" id="info" onclick="location.href='admin_detail?m_no=${members[i].m_no}'">정보</button>
							<button type="button" id="del" onclick="delEmp(${members[i].m_no})">삭제</button>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</table>

		<div id="pageButtons">
		    <!-- 검색 조건 가져오기 -->
		    <%
		        String target = request.getParameter("target") != null ? request.getParameter("target") : "";
		        String keyword = request.getParameter("keyword") != null ? request.getParameter("keyword") : "";
		    %>
		
		    <c:if test="${not empty members and members.size() ne 0}">
		        <!-- 이전 페이지 버튼 -->
		        <c:if test="${minPageCount > 5}">
		            <button type="button" onclick="goToPage(${minPageCount - 1})">
		                <c:out value="<"/>
		            </button>
		        </c:if>

		        <!-- 페이징 번호 버튼 -->
		        <c:forEach var="i" begin="${minPageCount}" end="${maxPageCount}" step="1">
		        	<c:choose>
		        		<c:when test="${not empty param.p and param.p eq i or empty param.p and i eq 1}">
				            <button type="button" id="curPage">
				                <c:out value="${i}"/>
				            </button>
		        		</c:when>
		        		<c:otherwise>
				            <button type="button" onclick="goToPage(${i})">
				                <c:out value="${i}"/>
				            </button>
		        		</c:otherwise>
		        	</c:choose>
		        </c:forEach>
		
		        <!-- 다음 페이지 버튼 -->
		        <c:if test="${maxPageCount % 5 == 0}">
		            <button type="button" onclick="goToPage(${maxPageCount + 1})">
		                <c:out value=">"/>
		            </button>
		        </c:if>
		    </c:if>
		</div>
	</main>
	
	<script type="text/javascript">
	    function goToPage(page) {
	        var target = '<%= target %>';
	        var keyword = '<%= keyword %>';
	        var url = 'admin?';
	
	        if (target !== '') {
	            url += '&target=' + encodeURIComponent(target);
	        }
	
	        if (keyword !== '') {
	            url += '&keyword=' + encodeURIComponent(keyword) + '&';
	        }
	        
	        url += 'p=' + page;
	
	        window.location.href = url;
	    }
	    
	    function delEmp(m_no) {
	    	if (confirm(m_no + "번 사번의 사원을 제거합니다.")) {
		    	location.href = 'admin_del?m_no=' + m_no;				
			}
		}
	</script>
	
	<script src="/BeansPaM/js/menu.js"></script>
</body>

</html>