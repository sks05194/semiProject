<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="kr">

<head>
	<meta charset="UTF-8">
	<title>관리자 메뉴 - 사원 등록</title>
	<link rel="stylesheet" type="text/css" href="/BeansPaM/css/admin.css">
	<script src="/BeansPaM/js/jquery.js"></script>
</head>

<body>
	<main>
		<h1>사원 등록</h1>

		<form action="regist_member" method="post" class="employee-form">
			<div class="form-group">
				<label for="m_name">이름</label>
				<input type="text" id="m_name" name="m_name" required>
			</div>

			<div class="form-group">
				<label for="m_position">직급</label>
				<select id="m_position" name="m_position" required>
					<option value="인턴">인턴</option>
					<option value="사원">사원</option>
					<option value="대리">대리</option>
					<option value="과장">과장</option>
					<option value="차장">차장</option>
					<option value="부장">부장</option>
					<option value="팀장">팀장</option>
					<option value="전무">전무</option>
					<option value="이사">이사</option>
				</select>
			</div>

			<div class="form-group">
				<label for="m_phone">전화번호</label>
				<input type="text" id="m_phone" name="m_phone" required>
			</div>

			<div class="form-group">
				<label for="m_dept">부서</label>
				<select id="m_dept" name="m_dept" required>
					<option value="관리부">관리부</option>
					<option value="영업부">영업부</option>
					<option value="기획부">기획부</option>
					<option value="인사부">인사부</option>
					<option value="재무부">재무부</option>
					<option value="관리부">관리부</option>
				</select>
			</div>

			<div class="form-group">
				<label for="m_email">이메일</label>
				<input type="email" id="m_email" name="m_email" required>
			</div>

			<button type="submit" id="submitBtn">등록</button>
			
		    <div class="back-button" style="display: inline;">
		        <a href="admin">
		            <button type="button" onclick="location.href = '/BeansPaM/fms/admin'">취소</button>
		        </a>
		    </div>
		</form>
	</main>
	
	<script>
		function checkInput() {
			var phoneValue = document.getElementsByName('m_phone')[0].value;
			var phoneRegex = /^01[0-9]-\d{3,4}-\d{4}$/;
			
			if (!phoneRegex.test(phoneValue)) {
				alert('전화번호 형식 오류.');
				return false;
			}
			return true;
		}

		document.querySelector('#m_phone').addEventListener('blur', function () {
			let phoneNum = this.value.split('-').join('');
			if (phoneNum.length == 11) {
				this.value = phoneNum.slice(0, 3) + '-' + phoneNum.slice(3, 7) + '-' + phoneNum.slice(7);
			}
		});

		$('.employee-form').submit(function () {
			event.preventDefault();

			if (checkInput()) {
				$.ajax({
					type: 'POST',
					url: 'regist_member',
					data: $(this).serialize(),
					success: function(response) {
						alert('사원 정보가 정상적으로 등록되었습니다.');
						window.location.href = "admin";
					},
					error: function (xhr, status, error) {
						alert('사원 등록에 실패하였습니다.');
					}
				});
			}
		});
	</script>
	<script src="/BeansPaM/js/menu.js"></script>
</body>

</html>