<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>아이디/비밀번호 찾기 화면</title>
	<style>
		* {
			margin: 0px auto;
		}

		input { 
			display: block;
			margin: 0px auto;
			width: 400px;
			height: 50px;
			border: 1px solid rgb(41, 15, 5);
			padding-left: 10px;
			outline: none;
			font-size: 16px;
			border-radius: 10px;
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
			margin-top: 50px;
		}

		button:hover {
			cursor: pointer;
		}

		.form1 {
			border: 0px;
			border-radius: 10px;
			margin: 0px auto;
			margin-top: 200px;
			padding: 30px;
			background-color: rgb(41, 15, 5);
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
		}

		.p2 {
			display: none;
			position: relative;
			margin: 0px auto;
			margin-left: 5%;
			margin-top: 30px;
			color: #945121;
			font-size: 18px;
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

		.p4 {
			position: relative;
			margin: 0px auto;
			margin-left: 45%;
			color: #945121;
		}
	</style>
	<script>
		window.onload = function() {
			<%-- 로고 클릭시 처음 화면으로 이동하는 코드 --%>
			document.querySelector(".img1").addEventListener("click", function() {
				window.location = "index.l";
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
		    history.replaceState({}, null, location.pathname);
		}
	</script>
</head>

<body>
   <div class="div1">
      <form class="form1" method="get" action="findIdPw.l">
         <div class="div2">
	        <img class="img1" src="/BeansPaM/img/logo.png" alt="logo" title="logo">
	     </div>
	     <input class="ipt1" id="findIdPw" name="findIdPw" type="text" placeholder="사원번호나 아이디를 입력하세요"> 
	     <button type="submit">아이디/비밀번호 찾기</button>
		 <p class="p1">아이디 : <%= findId%></p>
		 <p class="p2">비밀번호 : <%= findPw%></p> 
		 <p class="p3">올바르지 않은 사원번호나 아이디를 입력했습니다.</p> 
      </form>
   </div>
   <p class="p4">Copyright &copy; Beans PaM All Rights Reserved.</p>	
</body>

</html>