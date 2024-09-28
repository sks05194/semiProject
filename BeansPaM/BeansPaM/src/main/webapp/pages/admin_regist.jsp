<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>사원 등록</title>
	<link rel="stylesheet" type="text/css" href="/BeansPaM/css/admin.css">
</head>

<body>
	<main>
		<h1>사원 등록</h1>

		<form action="regist_member" method="post" class="employee-form" onsubmit="return checkInput()">
			<div class="form-group">
				<label for="m_name">이름</label>
				<input type="text" id="m_name" name="m_name" required>
			</div>

			<div class="form-group">
				<label for="m_position">직책</label>
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
				</select>
			</div>

			<div class="form-group">
				<label for="m_email">이메일</label>
				<input type="email" id="m_email" name="m_email" required>
			</div>

			<button type="submit" id="submitBtn">등록</button>
			
		    <div class="back-button" style="display: inline;">
		        <a href="admin">
		            <button type="button" style="background-color: gray;" onclick="location.href = '/BeansPaM/fms/admin'">취소</button>
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
	</script>
	<script src="/BeansPaM/js/menu.js"></script>
</body>

</html>