<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>로그인 화면</title>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.9.0/css/all.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.js"></script>
	<style>
		* {
			box-sizing: border-box;
			margin: 0px;
			padding: 0px;
		}

		input {
			display: block;
			margin: 0px auto;
			width: 400px;
			height: 50px;
			padding-left: 10px;
			outline: none;
			font-size: 16px;
			border-top: 0px;
			border-left: 0px;
			border-right: 0px;
			border-bottom: 2px solid #945121;
		}

		input::placeholder {
			font-weight: bold;
			color: #945121;
			font-size: 16px;
		}

		.ipt1 {
			margin: 0px auto;
			margin-bottom: 20px;
		}

		.ipt2 {
			margin: 0px auto;
			margin-bottom: 20px;
			padding-right: 40px;
		}

		.div1 {
			display: flex;
			background-color: white;
		}

		.div2 {
			text-align: center;
		}

		.div3 {
			position: relative;
			margin: 0px auto;
			margin-top: 20px;
			margin-bottom: 20px;
			text-align: center;
		}

		button {
			display: block;
			margin: 0px auto;
			width: 410px;
			height: 50px;
			border-radius: 5px;
			border: 0px;
			outline: none;
			background-color: #945121;
			color: white;
			font-size: 16px;
			font-weight: bold;
			margin-top: 50px;
			margin-bottom: 50px;
		}

		button:hover {
			cursor: pointer;
		}

		.form1 {
			border: 2px solid #945121;
			border-radius: 5px;
			margin: 0px auto;
			margin-top: 200px;
			padding: 30px;
		}

		.img1 {
			width: 190px;
		}

		.img1:hover {
			cursor: pointer;
		}

		.a1 {
			position: absolute;
			display: inline-block;
			width: 100px;
			font-size: 18px;
			font-weight: bold;
			right: 310px;
			bottom: 0px;
			color: #945121;
			text-decoration: none;
		}

		.a2 {
			position: absolute;
			display: inline-block;
			width: 200px;
			font-size: 18px;
			font-weight: bold;
			left: 225px;
			bottom: 0px;
			color: #945121;
			text-decoration: none;
		}

		a:hover {
			cursor: pointer;
		}

		.p1 {
			display: none;
			color: red;
			margin-left: 40px;
			margin-top: 70px;
			font-size: 18px;
		}

		.eyes {
		    visibility: hidden;
			position: relative;
			margin: 0px auto;
			margin-left: 92%;
			margin-top: -14.5%;
			height: 30px;
			font-size: 22px;
			cursor: pointer;
			color: #945121;
		}
	</style>
	<script>
		window.onload = function() {
			<%-- 로고 클릭시 처음 화면으로 이동하는 코드 --%>
			document.querySelector(".img1").addEventListener("click", function() {
				window.location = "/BeansPaM/";
			});

			<%-- 로그인이 실패하면 작동할 코드 --%>
			<% 
			    String failedLogin = request.getParameter("failedLogin");
			    
			    if (failedLogin != null) { %>
			       document.querySelector(".p1").style.display = "block";
			       document.querySelector(".ipt1").style.borderColor = "red";
			       document.querySelector(".ipt2").style.borderColor = "red";	    
			<% } %>	
			
			<%-- 주소창의 QueryString으로 처리된 문자열 제거하기 --%>
			<%-- 참고사이트: https://lahuman.github.io/javascript-remove-url-parameter/ --%>
			history.replaceState({}, null, location.pathname);
			
			<%-- 비밀번호를 입력할 때에만 눈 아이콘 표시하는 코드--%>
			$('.ipt2').on('input', function () {
				if ($(this).val()) {
				   $('.eyes').css({
					   visibility: 'visible'
				   });			
				} else {
				   $('.eyes').css({
					   visibility: 'hidden'
				   });
				}
			});
			
			<%-- 숨긴 비밀번호를 표시하는 눈 아이콘 코드 --%>
			$('.eyes').on('click', function () {
				const passwordInput = $(this).siblings('#inputPw');
				const isActive = passwordInput.attr('type') === 'text';

				if (isActive) {
					$(this).find('.fa-eye-slash').removeClass('fa-eye-slash').addClass('fa-eye');
					passwordInput.attr('type', 'password');
				} else {
					$(this).find('.fa-eye').removeClass('fa-eye').addClass('fa-eye-slash');
					passwordInput.attr('type', 'text');
				}
			});	
		}
	</script>
</head>

<body>
   <div class="div1">
		<form class="form1" action="login.l" method="post">
			<div class="div2">
				<img class="img1" src="/BeansPaM/img/logo.png" alt="logo.png" title="logo">
			</div>
			<input class="ipt1" id="inputId" name="inputId" type="text" placeholder="아이디를 입력하세요">
			<input class="ipt2" id="inputPw" name="inputPw" type="password" placeholder="비밀번호를 입력하세요">
			<div class="eyes">
				<i class="fa fa-eye"></i>
			</div>
			<button id="btnLogin" name="btnLogin" type="submit">로그인</button>
			<div class="div3">
				<a class="a1" href="registerMenu.l">사용자 신청</a>
				<a class="a2" href="findIdPwMenu.l">아이디/비밀번호 찾기</a>
			</div>
			<p class="p1">아이디와 비밀번호를 정확히 입력해 주세요.</p>
		</form>
	</div>
</body>

</html>