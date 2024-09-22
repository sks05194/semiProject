<%@ page import="java.util.List" %>
<%@ page import="vo.QnaVO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="kr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시글 작성</title>
    <link rel="stylesheet" href="/BeansPaM/css/qna_write.css">
    <script src="/BeansPaM/js/fontawsome.js"></script>
    <script src="/BeansPaM/js/jquery.js"></script>
</head>

<body>
    <header></header>

    <div class="chart"></div>
    <main>
        <div class="board-write">
            <h2>게시글 작성</h2>

            <!-- 작성된 데이터를 b/qna/submit으로 전송할 form -->
            <form action="<%=request.getContextPath()%>/b/qna/submit" method="post">
                <table class="write-table">
                    <tr>
                        <th class="center-align">제목</th>
                        <td><input type="text" class="input-title" name="title" placeholder="제목을 입력하세요" required></td>
                    </tr>
                    <tr>
                        <th class="center-align">작성자</th>
                        <td><input type="text" class="input-title" name="writer" placeholder="작성자명을 입력하세요" required></td>
                    </tr>
                    <tr>
                        <th class="center-align">내용</th>
                        <td><textarea class="input-content" name="content" placeholder="내용을 입력하세요" required></textarea></td>
                    </tr>
                </table>

                <!-- 카테고리 선택 라디오 버튼 -->
                <div class="radio-section">
                    <label><input type="radio" name="category" value="일반" required> 일반</label>
                    <label><input type="radio" name="category" value="보안" required> 보안</label>
                </div>

                <!-- 제출 및 나가기 버튼 -->
                <div class="button-section">
                    <button type="submit" class="btn-submit">게시글 등록</button>
                    <button type="button" class="btn-exit" onclick="exitPost()">나가기</button>
                </div>
            </form>
        </div>
    </main>

    <footer></footer>

    <script>
    	$('form').on('submit', function(event) {
        	event.preventDefault(); // 실제 서버로 전송되는 것을 막고, 값이 올바르게 전송되는지 확인
        	console.log($(this).serialize());
        	// 서버로 전송하려면 아래 코드를 사용
        	this.submit();
    	});

        function exitPost() {
            window.location.href = '<%=request.getContextPath()%>/b/qna'; // 나가기 버튼 클릭 시 Q&A 목록으로 이동
        }
    </script>

    <script src="/BeansPaM/js/board_menu.js"></script>
</body>

</html>
