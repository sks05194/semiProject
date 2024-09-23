<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> --%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/BeansPaM/css/notice_list.css">
    <script src="/BeansPaM/js/jquery.js"></script>
    <title>공지사항 초기화면</title>
    <script>
//      	   const itemsPerPage = 10;
//         let currentPage = 1; 
//         let totalPages = 1;

//         function searchTable() {
//             var input = document.getElementById("searchInput").value.toLowerCase();
//             var table = document.querySelector("table tbody"); 
//             var rows = table.getElementsByTagName("tr"); 

//             for (var i = 0; i < rows.length; i++) {
//                 var rowText = rows[i].innerText.toLowerCase(); 
//                 if (rowText.includes(input)) {
//                     rows[i].style.display = ""; 
//                 } else {
//                     rows[i].style.display = "none"; 
//                 }
//             }
//         }

        // 공지사항 목록을 localStorage에서 가져와서 테이블에 표시하는 함수
        function loadNotices() {
//             const noticeTableBody = document.querySelector('table tbody');
//             noticeTableBody.innerHTML = ''; 

//             const notices = JSON.parse(localStorage.getItem('notices')) || [];

//             totalPages = Math.ceil(notices.length / itemsPerPage);

//             const start = (currentPage - 1) * itemsPerPage;
//             const end = start + itemsPerPage;

//             const noticesToDisplay = notices.slice(start, end);

//             noticesToDisplay.forEach((notice, index) => {
//                 const newRow = document.createElement('tr');
//                 const noticeIndex = start + index + 1; // 번호를 1부터 최신순으로
//                 newRow.innerHTML = `
//                     <td>${notices.length - noticeIndex + 1}</td>
//                     <td>${notice.title}</td>
//                     <td>${notice.author}</td>
//                     <td>${notice.date}</td>
//                     <td>${notice.views}</td>
//                 `;
                
//                 newRow.onclick = function() {
//                     // 조회수 증가
//                     notices[notices.length - noticeIndex].views += 1;
//                     localStorage.setItem('notices', JSON.stringify(notices));

//                     // 상세 페이지로 이동
//                     window.location.href = './notice_detail.jsp?index=' + (notices.length - noticeIndex);
//                 };

//                 noticeTableBody.appendChild(newRow);
//             });

//             updatePagination();
        }

              // 페이지 로드 시 공지사항 데이터를 불러와 표시
        window.onload = function() {
            loadNotices();
        };
        
        function goDetail(n_no){
        	location.href="/BeansPaM/b/notice/detail?n_no=" + n_no;
        }
    </script>
</head>

<body>
    <header>
        <div class="menuBar">
            <!-- 로고 이미지 추가 -->
            <img src="/BeansPaM/img/logo.png" alt="Beans Pam Logo" class="logo">
            <a href="/BeansPaM/">home</a>
            <a href="/BeansPaM/notice_list.jsp">공지사항</a>
            <a href="#">Q&amp;A</a>
        </div>
    </header>

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
                    <input type="search" id="searchInput" placeholder="검색할 내용 입력">
                    <button onclick="searchTable()">검색</button>
                </div>
                <div class="pagination">
                </div>
				<c:if test='${loginName == "관리자"}'>	
	                <div class="button-container" onclick="location.href='/BeansPaM/b/notice/write'">
	                    <button>글쓰기</button>
	                </div>
                </c:if>
<%--                 <p>Login Name: ${loginName}</p> --%>
            </div>
        </div>
    </main>
</body>
</html>
