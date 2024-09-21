<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/BeansPaM/css/notice_write.css">
    <script src="/BeansPaM/js/jquery.js"></script>
    <title>공지사항 작성</title>
</head>
<body>
    <header>
        <div class="menuBar">
            <img src="/BeansPaM/img/full_logo.png" alt="Beans Pam Logo" class="logo">
            <a href="/BeansPaM/">home</a>
            <a href="/BeansPaM/notice.jsp">공지사항</a>
            <a href="#">Q&A</a>
        </div>
    </header>
    <div class="container">
        <h1>공지사항 작성</h1>

        <form id="noticeForm" class="form" action="./register" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="title">제목</label>
                <input type="text" id="title" name="title" placeholder="제목을 입력하세요" required>
            </div><br>
            <div class="form-group" style="position: relative;">
                <div class="content-header">
                    <label for="content">내용</label>
                </div>
                <textarea id="content" name="content" rows="10" placeholder="공지할 내용을 입력하세요" required></textarea>
            </div>
            <div class="form-group" style="display: flex; justify-content: space-between; align-items: center;">
                <button type="button" class="exit-button" onclick="window.history.back();">나가기</button>
                <button type="submit" class="submit-button">등록</button>
            </div>
        </form>
    </div>
</body>
</html>
