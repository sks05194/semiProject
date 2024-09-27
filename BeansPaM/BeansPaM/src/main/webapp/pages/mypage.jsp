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
    <main>
        <%
        MemberVO mvo = (MemberVO) request.getAttribute("member");
        if (mvo != null) {
        %>
        <div class="container">
            <!-- 이미지와 이름 -->
            <div class="image-container">
					<img src="../img/emp.jpg" alt="프로필 이미지">
                <p><%=mvo.getM_name()%></p>
			<form id="commuteForm" action="commute" method="post" class="commute" >
					<button class="commute-button"  name="actionType" value="checkin">출근</button><button class="commute-button"  name="actionType" value="checkout">퇴근</button>
				</form>
                <!-- 이름 표시 -->
            </div>

            <!-- 테이블 데이터 -->
            <div class="mytable">
                <table>
                    <tr>
                        <td>소속</td>
                        <td><%=mvo.getM_dept()%></td>
                    </tr>
                    <tr>
                        <td>아이디</td>
                        <td><%=mvo.getM_id()%></td>
                    </tr>
                    <tr>
                        <td>이메일</td>
                        <td><%=mvo.getM_email()%></td>
                    </tr>
                    <tr>
                        <td>사번</td>
                        <td><%=mvo.getM_no()%></td>
                    </tr>
                    <tr>
                        <td>입사일자</td>
                        <td><%=mvo.getM_day()%></td>
                    </tr>
                    <tr>
                        <td>전화 번호</td>
                        <td><%=mvo.getM_phone()%></td>
                    </tr>
                    <tr>
                        <td>남은 연차</td>
                        <td><%=mvo.getM_leave()%></td>
                    </tr>
                    <tr>
                        <td>직급</td>
                        <td><%=mvo.getM_position()%></td>
                    </tr>
                </table>
            </div>
            <!-- 버튼 -->
            <div class="button-container">
                <button type="button" id="editInfoBtn">회원정보 수정</button>
                <button type="button" id="changePwdBtn">비밀번호 변경</button>
                <form id="salaryForm" action="salary" method="post">
                    <button type="submit">급여정보 확인</button>
                </form>
                <form id="salaryForm" action="workday" method="post">
                    <button type="submit">근퇴 시간 조회</button>
                </form>
            </div>
        </div>

        <!-- 회원정보 수정 모달 -->
        <div id="editInfoModal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <h2>회원정보 수정</h2>
                 <form id="editInfoForm" action="updateMember" method="post" onsubmit="return validateForm()">
                    <label for="email">이메일:</label> <input type="text" id="email"
                        name="email" value="<%=mvo.getM_email()%>"><br>
                        <small>형식: example@example.com</small>
                    <br> <label for="phone">전화 번호:</label> <input type="text"
                        id="phone" name="phone" value="<%=mvo.getM_phone()%>"><br>
                        <small>형식: 010-1234-5678</small>
                    <br>
                    <button type="submit">수정 완료</button>
                </form>
            </div>
        </div>


        <!-- 비밀번호 변경 모달 -->
        <div id="changePwdModal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <h2>비밀번호 변경</h2>
        <!-- mvo.getM_pw() 값을 data 속성으로 전달 -->
        <form id="changePwdForm" action="changePassword" method="post" onsubmit="return validatePasswordForm()" data-mpw="<%= mvo.getM_pw() %>">
            <label for="oldPwd">현재 비밀번호:</label>
            <input type="password" id="oldPwd" name="oldPwd"><br>
            <small>현재 비밀번호를 입력하세요.</small>
            <br><br>

            <label for="newPwd">새 비밀번호:</label>
            <input type="password" id="newPwd" name="newPwd"><br>
            <small>새 비밀번호는 최소 3자리 이상이어야 합니다.</small>
            <br><br>

            <label for="confirmPwd">비밀번호 확인:</label>
            <input type="password" id="confirmPwd" name="confirmPwd"><br>
            <small>새 비밀번호와 동일하게 입력하세요.</small>
            <br><br>

            <button type="submit">비밀번호 변경</button>
        </form>
            </div>
        </div>
        <% String errorMsg = (String) request.getAttribute("errorMsg"); %>
        <% if (errorMsg != null) { %>
            <div class="error-message"><%= errorMsg %></div>
        <% } %>
        <% } else { %>
            <p>사용자 정보를 불러올 수 없습니다.</p>
        <% } %>

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
            // 유효성 검사 함수
            function validateForm() {
                var phone = document.getElementById('phone').value;
                var email = document.getElementById('email').value;

                // 전화번호 형식: 010-1234-5678 또는 011-123-4567 등 허용
                var phoneRegex = /^01[0-9]-\d{3,4}-\d{4}$/;

                // 이메일 형식: 일반적인 이메일 형식 (예: example@example.com)
                var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

                // 전화번호 유효성 검사
                if (!phoneRegex.test(phone)) {
                    alert('전화번호 형식이 올바르지 않습니다. 010-1234-5678 형식으로 입력해주세요.');
                    return false;  // 폼 전송 중단
                }

                // 이메일 유효성 검사
                if (!emailRegex.test(email)) {
                    alert('이메일 형식이 올바르지 않습니다. example@example.com 형식으로 입력해주세요.');
                    return false;  // 폼 전송 중단
                }

                return true;  // 유효성 검사가 성공하면 폼 전송 허용
            }
                
            // 유효성 검사 함수
            function validatePasswordForm() {
                var oldPwd = document.getElementById('oldPwd').value;
                var newPwd = document.getElementById('newPwd').value;
                var confirmPwd = document.getElementById('confirmPwd').value;
                
                // 폼의 data 속성에서 mvo.getM_pw() 값 가져오기
                var formElement = document.getElementById('changePwdForm');
                var actualPwd = formElement.getAttribute('data-mpw');

                // 현재 비밀번호 입력 확인
                if (!oldPwd) {
                    alert('현재 비밀번호를 입력해주세요.');
                    return false;
                }

                // 현재 비밀번호와 실제 비밀번호가 일치하는지 확인
                if (oldPwd !== actualPwd) {
                    alert('현재 비밀번호가 올바르지 않습니다.');
                            return false;
                }
        
                // 새 비밀번호 최소 길이 3자리 이상 체크
                if (newPwd.length < 5) {
                    alert('새 비밀번호는 최소 5자리 이상이어야 합니다.');
                    return false;
                }

                // 새 비밀번호와 비밀번호 확인이 일치하는지 확인
                if (newPwd !== confirmPwd) {
                    alert('새 비밀번호와 비밀번호 확인이 일치하지 않습니다.');
                    return false;
                }

                return true;  // 모든 조건이 충족되면 폼 전송 허용
            }
        </script>
    </main>
    <script src="/BeansPaM/js/menu.js"></script>
</body>

</html>