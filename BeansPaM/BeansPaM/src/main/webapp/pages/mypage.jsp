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
    
</head>

<body>
    <%
        MemberVO mvo = (MemberVO) request.getAttribute("member");
        if (mvo != null) {
    %>
    <div class="container">
        <!-- 이미지와 이름 -->
        <div class="image-container">
            <img src="" alt="프로필 이미지">
            <p><%= mvo.getM_name() %></p> <!-- 이름 표시 -->
        </div>
	
	
        <!-- 테이블 데이터 -->
        <div class="mytable">
            <table>
                <tr><td>소속</td><td><%=mvo.getM_dept()%></td></tr>
                <tr><td>아이디</td><td><%=mvo.getM_id()%></td></tr>
                <tr><td>이메일</td><td><%=mvo.getM_email()%></td></tr>
                <tr><td>사번</td><td><%=mvo.getM_no()%></td></tr>
                <tr><td>입사일자</td><td><%=mvo.getM_day()%></td></tr>
                <tr><td>전화 번호</td><td><%=mvo.getM_phone()%></td></tr>
                <tr><td>남은 연차</td><td><%=mvo.getM_leave()%></td></tr>
                <tr><td>직급</td><td><%=mvo.getM_position()%></td></tr>
            </table>
        </div>
    </div>

    <!-- 버튼 -->
    <div class="button-container">
        <button type="button" id="editInfoBtn">회원정보 수정</button>
        <button type="button" id="changePwdBtn">비밀번호 변경</button>
    </div>

    <!-- 회원정보 수정 모달 -->
    <div id="editInfoModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>회원정보 수정</h2>
            <form action="/BeansPaM/updateMember" method="post">
                <label for="dept">이메일:</label>
                <input type="text" id="dept" name="dept" value="<%= mvo.getM_email()%>"><br><br>
                <label for="phone">전화 번호:</label>
        		        <input type="text" id="phone" name="phone" value="<%= mvo.getM_phone() %>"><br><br>
                <button type="submit">수정 완료</button>
            </form>
        </div>
    </div>

    <!-- 비밀번호 변경 모달 -->
    <div id="changePwdModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>비밀번호 변경</h2>
            <form action="/BeansPaM/changePassword" method="post">
                <label for="oldPwd">현재 비밀번호:</label>
                <input type="password" id="oldPwd" name="oldPwd"><br><br>
                <label for="newPwd">새 비밀번호:</label>
                <input type="password" id="newPwd" name="newPwd"><br><br>
                <label for="confirmPwd">비밀번호 확인:</label>
                <input type="password" id="confirmPwd" name="confirmPwd"><br><br>
                <button type="submit">비밀번호 변경</button>
            </form>
        </div>
    </div>

    <%
        } else {
    %>
    <p>사용자 정보를 불러올 수 없습니다.</p>
    <%
        }
    %>

    <script>
        // 모달 열기 및 닫기 로직
        $(document).ready(function() {
            // 모달 엘리먼트 가져오기
            var editInfoModal = $("#editInfoModal");
            var changePwdModal = $("#changePwdModal");

            // 회원정보 수정 버튼 클릭 시
            $("#editInfoBtn").click(function() {
                editInfoModal.show();
            });

            // 비밀번호 변경 버튼 클릭 시
            $("#changePwdBtn").click(function() {
                changePwdModal.show();
            });

            // 모달 닫기 버튼 기능
            $(".close").click(function() {
                editInfoModal.hide();
                changePwdModal.hide();
            });

            // 모달 외부 클릭 시 닫기
            $(window).click(function(event) {
                if ($(event.target).is(editInfoModal)) {
                    editInfoModal.hide();
                }
                if ($(event.target).is(changePwdModal)) {
                    changePwdModal.hide();
                }
            });
        });
    </script>
    <footer>
    </footer>
	
</body>

</html>
