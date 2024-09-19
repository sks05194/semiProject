<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>로그인 화면</title>
	<style>
		* {
			margin: 0px auto;
		}

		input { 
			display: block;
			margin: 0px auto;
			width: 400px;
			height: 50px;
			border: 1px solid #af886c;
			border-radius: 10px;
			padding-left: 10px;
			outline: none;
			font-size: 16px;
		}

	    input::placeholder {
			font-weight: bold;
			color: #af886c;
			font-size: 16px;
		}

	    input:focus {
			border: 1px solid rgb(41, 15, 5);
		}

		.ipt1 {
			margin: 0px auto;
			margin-bottom: 20px;
		}

		.ipt2 {
			margin: 0px auto;
			margin-bottom: 20px;
		}

		.div1 {
			display: flex;
			background-color: white;
		}

		.div2 {
			margin: 0px auto;
			margin-top: 20px;
			margin-bottom: 20px;
			text-align: center;
		}

		button {
			display: block;
			margin: 0px auto;
			width: 400px;
			height: 50px;
			border-radius: 50px;
			border: 0px;
			outline: none;
			background-color: #945121;
			color: white;
			font-weight: bold;
			font-size: 16px;
		}

		button:hover {
			cursor: pointer;
		}

		.form1 {
			border: 0px;
			border: 3px solid #af886c;
			border-radius: 10px;
			margin: 0px auto;
			margin-top: 200px;
			padding: 30px;
		}

		.img1 {
			display: inline-block;
			width: 100px;
			height: 100px;
			margin: 0px auto;
			margin-left: 165px;
			margin-bottom: 20px;
			margin-top: -5px;
		}

		.img1:hover {
			cursor: pointer;
		}

		.a1 {
			display: inline-block;
			font-size: 18px;
			margin: 0px auto;
			margin-right: 150px;
			margin-top: 20px;
			color: #945121;
		}

		.a2 {
			display: inline-block;
			font-size: 18px;
			margin: 0px auto;
			color: #945121;
		}

		a:hover {
			cursor: pointer;
		}

        .p1 {
            display: none;
            color: red;
            margin-left: 40px;
            font-size: 18px;
        }
        
		.p2 {
			position: relative;
			margin: 0px auto;
			margin-left: 45%;
			color: #945121;
			font-size: bolder;
		}
	</style>
	<script>
		window.onload = function() {
			<%-- 로고 클릭시 처음 화면으로 이동하는 코드 --%>
			document.querySelector(".img1").addEventListener("click", function() {
				window.location = "firstPage.l";
			});

			<%-- 로그인이 실패하면 작동할 코드 --%>
			<% 
			    String failedLogin = (String) session.getAttribute("failedLogin");
			    
			    if (failedLogin != null) { %>
			       document.querySelector(".p1").style.display = "block";
			       document.querySelector(".ipt1").style.borderColor = "red";
			       document.querySelector(".ipt2").style.borderColor = "red";
			<%     session.removeAttribute("failedLogin");
			 } %>		
		}
	</script>
</head>

<body>
   <div class="div1">
      <form class="form1" action="login.l" method="post">
	     <img class="img1" src="/BeansPaM/img/logo.png" alt="logo" title="logo">
	     <input class="ipt1" id="inputId" name="inputId" type="text" placeholder="아이디를 입력하세요">
	     <input class="ipt2" id="inputPw" name="inputPw" type="password" placeholder="비밀번호를 입력하세요">  
	     <button id="btnLogin" name="btnLogin" type="submit">로그인</button>  
	     <div class="div2">
		   <a class="a1" href="registerMenu.l"><ins>사용자 신청</ins></a>
		   <a class="a2" href="findIdPwMenu.l"><ins>아이디/비밀번호 찾기</ins></a>
	     </div>
	     <p class="p1">아이디와 비밀번호를 정확히 입력해 주세요.</p>	
      </form>
   </div>
   <p class="p2">Copyright &copy; Beans PaM All Rights Reserved.</p>
</body>

</html>