<%@ page import="vo.QnaVO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="kr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Q&A</title>
    <link rel="stylesheet" href="/BeansPaM/css/qna_detail.css"> <!-- CSS 경로 설정 -->
</head>
<body>
    <div class="qna-container">
        <h2>게시글 상세보기</h2>
        <%
            QnaVO qna = (QnaVO) request.getAttribute("qna");
            if (qna != null) {
        %>
            <div class="qna-detail">
                <p><strong>제목:</strong> <%= qna.getQ_title() %></p>
                <p><strong>작성자:</strong> <%= qna.getQ_writer() %></p>
                <p><strong>작성일:</strong> <%= qna.getQ_date() %></p>
                <p><strong>조회수:</strong> <%= qna.getQ_views() %></p>
                <!-- 내용에서 줄바꿈을 처리하기 위해 replaceAll 사용 -->
                <p class="qna-content"><strong>내용:</strong> <%= qna.getQ_content().replaceAll("\n", "<br>") %></p>
            </div>
            <!-- 나가기 버튼 섹션 추가 -->
            <div class="button-section">
                <button class="btn-exit" onclick="exitPost()">나가기</button>
            </div>
        <%
            } else {
        %>
            <p>게시글을 찾을 수 없습니다.</p>
        <%
            }
        %>
    </div>

    <script>
        function exitPost() {
            window.location.href = '<%=request.getContextPath()%>/b/qna'; // Q&A 목록으로 이동
        }
    </script>
</body>
</html>
