<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="java.util.ArrayList"%>
<%@page import="vo.NoticeVO"%>
<%@page import="dao.NoticeDAO"%>
<%-- <%!NoticeDAO dao = new NoticeDAO(); %> --%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/BeansPaM/css/notice_list.css">
    <script src="/BeansPaM/js/jquery.js"></script>
    <title>공지사항 초기화면</title>
</head>
<body>

    <main class="content">
        <div class="board">
            <h2>공지사항</h2>
            <table>
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>작성일</th>
                        <th>조회수</th>
                    </tr>
                    <!-- JSTL을 이용하여 리스트 출력 -->
			        <c:forEach var="item" items="${noticeList}">
				        <tr onClick="goDetail(${item.n_no});">
		                    <td>${item.no}</td>
		                    <td>${item.n_title}</td>
		                    <td>admin</td>
		                    <td>${item.n_r_date}</td>
		                    <td>${item.n_views}</td>
	                    </tr>
			        </c:forEach>
                </thead>
                <tbody>
                </tbody>
            </table>
            <div class="actions-container">
                <!-- 검색창 -->
                <div class="search-container">
               		<form method="get" action="/BeansPaM/b/notice">
						<div class="search">
							<input type="text" name="keyword" id="keyword" placeholder="검색할 내용 입력" value="${param.keyword}">
						</div>
					<input type="submit" value="검색">
					</form>
                </div>
                <div class="pagination">
                </div>
				<c:if test='${loginName == "관리자"}'>	
	                <div class="button-container" onclick="location.href='/BeansPaM/b/notice/write'">
	                    <button>글쓰기</button>
	                </div>
                </c:if>
            </div>
        </div>
    </main>
    <script>
//         window.onload = function() {
//             loadNotices();
//         };
        
        function goDetail(n_no){
        	location.href="/BeansPaM/b/notice/detail?n_no=" + n_no;
        }
    </script>
</body>
</html>
