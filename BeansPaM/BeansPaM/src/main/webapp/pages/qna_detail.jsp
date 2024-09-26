<%@ page import="vo.QnaVO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="kr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Q&A</title>
    <link rel="stylesheet" href="/BeansPaM/css/qna_detail.css">
</head>
<body>
    <div class="qna-container">
        <h2>Q&A</h2>
        <%
            QnaVO qna = (QnaVO) request.getAttribute("qna");
            if (qna != null) {
        %>
            <div class="qna-detail">
                <p><strong>제목:</strong> <%= qna.getQ_title() %></p>
                <p><strong>작성자:</strong> <%= qna.getQ_writer() %></p>
                <p><strong>작성일:</strong> <%= qna.getQ_date() %></p>
                <p><strong>조회수:</strong> <%= qna.getQ_views() %></p>
                <p class="qna-content"><%= qna.getQ_content().replaceAll("\n", "<br>") %></p> <br>
            </div>

            <!-- 답변 안내 문구 추가 -->
            <div class="response-info">
                <p style="color: red; font-weight: bold;">해당 질문사항에 대한 답변은 관리자 계정으로 답변드릴 예정입니다.</p>
                <p style="color: black; font-weight: bold;">관리자 E-Mail : admin@beanspam.com</p><br>
                
            </div>

            <!-- 삭제하기 버튼 추가 -->
            <div class="button-section">
                <button class="btn-delete" onclick="deletePost('<%= qna.getQ_no() %>')">삭제하기</button>
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
        function deletePost(q_no) {
            if (confirm('정말로 이 게시글을 삭제하시겠습니까?')) {
                // 서버로 삭제 요청을 보냄
                window.location.href = '<%=request.getContextPath()%>/b/qna/delete?q_no=' + q_no;
            }
        }

        function exitPost() {
            window.location.href = '<%=request.getContextPath()%>/b/qna'; // Q&A 목록으로 이동
        }
    </script>
</body>
</html>
