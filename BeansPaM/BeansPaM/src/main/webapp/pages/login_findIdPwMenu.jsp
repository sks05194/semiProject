<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>아이디/비밀번호 찾기 화면</title>
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
			border-top: 0px;
			border-right: 0px;
			border-left: 0px;
			border-bottom: 2px solid #945121;
			padding-left: 10px;
			outline: none;
			font-size: 16px;
		}

	    input::placeholder {
			font-weight: bolder;
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
		}

		.div1 {
			display: flex;
			background-color: white;
		}

        .div2 {
            text-align: center;
        }
        
		button {
			display: block;
			margin: 0px auto;
			width: 400px;
			height: 50px;
			border-radius: 5px;
			border: 0px;
			outline: none;
			background-color: #945121;
			color: white;
			font-weight: bold;
			font-size: 16px;
			margin-top: 50px;
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
		
		.p1 {
 			display: none; 
			position: relative;
			margin: 0px auto;
			margin-left: 5%;
			margin-top: 30px;
			color: #945121;
			font-size: 18px;
			font-weight: bolder;
		}

		.p2 {
			display: none;
			position: relative;
			margin: 0px auto;
			margin-left: 5%;
			margin-top: 30px;
			color: #945121;
			font-size: 18px;
			font-weight: bolder;
		}
		
		.p3 {
 			display: none; 
			position: relative;
			margin: 0px auto;
			margin-left: 1%;
			margin-top: 30px;
			color: red;
			font-size: 18px;
		}
	</style>
	<script>
		window.onload = function() {
			<%-- 로고 클릭시 로그인 화면으로 이동하는 코드 --%>
			document.querySelector(".img1").addEventListener("click", function() {
				window.location = "/BeansPaM/loginMenu.l";
			});
			
			<%-- 아이디와 비밀번호를 가져오는 코드 --%>
		    <%
		    String findId = request.getParameter("findId");
		    String findPw = request.getParameter("findPw");
		    String notExistIdPw = request.getParameter("notExistIdPw");
		
		    if (findId != null) { %>
			    document.querySelector('.p1').style.display = "block";
		    <%	
		    }
		
		    if (findPw != null) { %>
			    document.querySelector('.p2').style.display = "block";
		    <%	
		    } 
			
		    if (notExistIdPw != null) { %>
			    document.querySelector('.p3').style.display = "block";
			    document.querySelector('input').style.borderColor = "red";
		    <%	
		    }
		    %>
			<%-- 주소창의 QueryString으로 처리된 문자열 제거하기 --%>
			<%-- 참고사이트: https://lahuman.github.io/javascript-remove-url-parameter/ --%>
		    history.replaceState({}, null, location.pathname);
		}
	</script>
</head>

<body>
   <div class="div1">
      <form class="form1" method="get" action="/BeansPaM/findIdPw.l">
         <div class="div2">
	        <img class="img1" src="/BeansPaM/img/logo.png" alt="logo.png" title="logo">
	     </div>
	     <input class="ipt1" id="findIdPw" name="findIdPw" type="text" placeholder="사원번호나 아이디를 입력하세요"> 
	     <button type="submit">아이디/비밀번호 찾기</button>
		 <p class="p1">아이디 : <%= findId%></p>
		 <p class="p2">비밀번호 : <%= findPw%></p> 
		 <p class="p3">올바르지 않은 사원번호나 아이디를 입력했습니다.</p> 
      </form>
   </div>	
</body>

</html>