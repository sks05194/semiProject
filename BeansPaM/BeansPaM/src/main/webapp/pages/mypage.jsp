<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="vo.MemberVO"%>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마이페이지</title>
    <link rel="stylesheet" href="/BeansPaM/css/mypage.css">
    <script src="https://kit.fontawesome.com/b19de6d406.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="/BeansPaM/js/fontawsome.js"></script>
    <script src="/BeansPaM/js/jquery.js"></script>
</head>

<body>
    <%
        MemberVO mvo = (MemberVO) request.getAttribute("member");
        if (mvo != null) {
    %>
    <div class="container">
        <table class="mytable">
            <tr><td>소속</td><td><%=mvo.getM_dept()%></td></tr>
            <tr><td>사번</td><td><%=mvo.getM_no()%></td></tr>
            <tr><td>입사일자</td><td><%=mvo.getM_day()%></td></tr>
            <tr><td>전화 번호</td><td><%=mvo.getM_phone()%></td></tr>
            <tr><td>남은 연차</td><td><%=mvo.getM_leave()%></td></tr>
            <tr><td>직급</td><td><%=mvo.getM_position()%></td></tr>
        </table>

        <div class="button-container">
            <button type="button">회원정보 수정</button>
            <button type="button">비밀번호 변경</button>
        </div>
    </div>

    <%
        } else {
    %>
    <p>사용자 정보를 불러올 수 없습니다.</p>
    <%
        }
    %>

    <script src="/BeansPaM/js/fms_menu.js"></script>
</body>

</html>
