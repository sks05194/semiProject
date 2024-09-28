<%@ page import="java.util.List" %>
<%@ page import="vo.QnaVO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="kr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Q&amp;A</title>
    <link rel="stylesheet" href="/BeansPaM/css/qna.css">
</head>

<body>
    <main>
        <div class="board">
            <!-- 테이블 제목 및 검색창을 포함할 컨테이너 -->
            <div class="table-header">
                <h2>Q&amp;A</h2> <!-- 테이블 좌측 제목 -->
                <div class="search-container"> <!-- 우측 상단에 검색창 배치 -->
                    <form id="searchForm" action="<%=request.getContextPath()%>/b/qna/search?page=${currentPage}" method="get">
                        <select name="searchType" class="search-select">
                            <option value="title">제목</option>
                            <option value="writer">작성자</option>
                        </select>
                        <input type="text" name="keyword" class="search-input" placeholder="검색어를 입력하세요">
                        <button type="submit" class="search-button">검색</button>
                    </form>
                </div>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>No</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>작성일</th>
                        <th>조회수</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<QnaVO> qnaList = (List<QnaVO>) request.getAttribute("qnaList");
                        if (qnaList != null && !qnaList.isEmpty()) {
                            for (QnaVO qna : qnaList) {
                    %>
                    <tr>
                        <td><%= qna.getQ_no() %></td>
                        <td><a href="qna/detail.do?q_no=<%= qna.getQ_no() %>"><%= qna.getQ_title().length() >= 10 ? qna.getQ_title().substring(0, 11) + "..." : qna.getQ_title() %></a></td>
                        <td><%= qna.getQ_writer() %></td>
                        <td><%= qna.getQ_date() %></td>
                        <td><%= qna.getQ_views() %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="6">등록된 게시글이 없습니다.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>

            <!-- 페이지네이션 -->
            <div class="pagination">
                <% 
                    Integer currentPage = (Integer) request.getAttribute("currentPage");
                    Integer maxPage = (Integer) request.getAttribute("maxPage");
                    Integer startPage = (Integer) request.getAttribute("startPage");
                    Integer endPage = (Integer) request.getAttribute("endPage");

                    if (startPage != null && startPage > 1) {
                %>
                    <a href="?page=<%= startPage - 1 %>">&laquo; 이전</a>
                <% 
                    }

                    if (startPage != null && endPage != null) {
                        for (int i = startPage; i <= endPage; i++) {
                            if (i == currentPage) {
                %>
                    <a href="?page=<%= i %>" class="active"><%= i %></a>
                <% 
                            } else {
                %>
                    <a href="?page=<%= i %>"><%= i %></a>
                <% 
                            }
                        }
                    }

                    if (endPage != null && endPage < maxPage) {
                %>
                    <a href="?page=<%= endPage + 1 %>">다음 &raquo;</a>
                <% 
                    }
                %>
            </div>

            <!-- 글쓰기 관련 버튼 -->
            <div class="button-wrapper">
                <div class="button-container">
                    <button onclick="location.href='<%=request.getContextPath()%>/pages/qna_write.jsp'" class="search-button">글쓰기</button>
                </div>
            </div>

        </div>
    </main>

    <!-- JavaScript로 게시글 클릭 시 색상 변경 및 상태 저장 -->
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            const links = document.querySelectorAll("td:nth-child(2) a");

            links.forEach(link => {
                const postId = link.getAttribute("href");

                // 페이지 로드 시 localStorage에서 읽음 상태 확인
                if (!localStorage.getItem(postId)) {
                    // 새 글일 경우 'n' 아이콘을 추가
                    const newIcon = document.createElement('span');
                    newIcon.classList.add('new-icon');
                    newIcon.textContent = 'N';
                    link.appendChild(newIcon);
                }

                link.addEventListener("click", function () {
                    // 게시글 클릭하면 'read' 클래스를 추가하여 읽음 상태로 처리
                    this.classList.add("read");

                    // 클릭한 게시글 ID를 localStorage에 저장하여 'n' 아이콘 제거
                    localStorage.setItem(postId, "read");

                    // 'n' 아이콘이 있으면 제거
                    const newIcon = this.querySelector('.new-icon');
                    if (newIcon) {
                        newIcon.remove();
                    }
                });
            });

            // 검색어 입력 여부 확인
            document.getElementById("searchForm").addEventListener("submit", function (e) {
                const keywordInput = document.querySelector("input[name='keyword']");
                if (keywordInput.value.trim() === "") {
                    e.preventDefault(); // 검색어가 없으면 폼 제출을 막음
                    alert("검색어를 입력하세요.");
                }
            });
        });
    </script>
    <script src="/BeansPaM/js/menu.js"></script>
</body>

</html>
