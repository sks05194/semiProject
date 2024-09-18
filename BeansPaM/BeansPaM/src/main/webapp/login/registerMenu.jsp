<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>사용자 신청 화면</title>
	<script src="/BeansPaM/js/jquery.js"></script>
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
			width: 450px;
			margin: 0px auto;
			margin-left: -50px;
			margin-bottom: 20px;
		}

		.ipt2 {
			width: 450px;
			margin: 0px auto;
			margin-left: -50px;
			margin-bottom: 20px;
		}

		.ipt3 {
			width: 450px;
			margin: 0px auto;
			margin-bottom: 20px;
			margin-left: -50px;
		}

		.div1 {
			display: flex;
			background-color: white;
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
			font-size: 14px;
		}

		button:hover {
			cursor: pointer;
		}

		.btn1 {
			display: inline-block;
			margin: 0px auto;
			margin-left: -50px;
			margin-bottom: -10px;
			width: 470px;
			font-size: 16px;
		}

		.form1 {
			border: 0px;
			border: 3px solid #af886c;
			border-radius: 10px;
			margin: 0px auto;
			margin-top: 200px;
			padding: 75px 20px 75px 75px;
		}

		.img1 {
			display: inline-block;
			width: 100px;
			height: 100px;
			margin: 0px auto;
			margin-left: 130px;
			margin-bottom: 20px;
			margin-top: -50px;
		}

		.img1:hover {
			cursor: pointer;
		}
		
		
		.p1 {
			display: none;
			margin: 0px auto;
			margin-top: -20px;
			margin-left: -50px;
			color: red;
			font-size: 16px;
		}

		.p2 {
			display: none;
			margin: 0px auto;
			margin-top: -20px;
			margin-left: -50px;
			color: red;
			font-size: 16px;
		}

		.p3 {
			display: none;
			margin: 0px auto;
			margin-top: -20px;
			margin-left: -50px;
			color: red;
			font-size: 16px;
		}

		
		.p4 {
			position: absolute;
			display: none;
			color: red;
			font-size: 18px;
			left: 730px;
			top: 640px;
		}

		.p5 {
			position: absolute;
			display: none;
			color: red;
			font-size: 18px;
			left: 730px;
			top: 640px;
		}

		.p6 {
			position: absolute;
			display: none;
			color: red;
			font-size: 18px;
			left: 730px;
			top: 640px;
		}		

		.p7 {
			position: absolute;
			display: none;
			color: red;
			font-size: 18px;
			left: 730px;
			top: 640px;
		}		

		.p8 {
			position: absolute;
			display: none;
			color: red;
			font-size: 18px;
			left: 730px;
			top: 640px;
		}
				
		.p9 {
			position: relative;
			margin: 0px auto;
			margin-left: 46%;
			color: #945121;
		}
	</style>
	<script>
		window.onload = function() {
		    <%-- 로고 클릭시 처음 화면으로 이동하는 코드 --%>
			document.querySelector(".img1").addEventListener("click", function() {
				window.location = "firstPage.l";
			});	

			<%-- 유효성 검사가 실행되는 조건 코드 --%>
			document.getElementById('regex').addEventListener("submit", regex);
            $('input').on('blur', regex);

            <%-- 정규식을 이용한 유효성 검사 함수 --%>
		    function regex (event) {
			    <%-- 입력값 초기화 --%>
			    var registerNo = document.getElementById('registerNo').value;
			    var registerId = document.getElementById('registerId').value;
			    var registerPw = document.getElementById('registerPw').value;

			    <%-- 에러 메세지 초기화 --%>
			    var errorNo = document.getElementById('errorNo');
			    var errorId = document.getElementById('errorId');
                var errorPw = document.getElementById('errorPw');
		
			    <%-- 유효성 검사 --%>
			    var isValid = true;

			    <%-- 사원번호, 1~4자리의 숫자만 입력가능 --%>
			    var noPattern = /^\d{1,4}$/;
			    if (!noPattern.test(registerNo)) {
				    isValid = false;
				    errorNo.style.display = "block";
				    document.querySelector('.ipt1').style.borderColor = "red";
			    } else {
				    errorNo.style.display = "none";
				    document.querySelector('.ipt1').style.borderColor = "#af886c";
			    }

			    <%-- 아이디, 1~20자리의 대소영문자,숫자,_ 만 입력가능 --%>
			    var idPattern = /^\w{1,20}$/;
			    if (!idPattern.test(registerId)) {
				    isValid = false;
				    errorId.style.display = "block";
				    document.querySelector('.ipt2').style.borderColor = "red";
			    } else {
				    errorId.style.display = "none";
				    document.querySelector('.ipt2').style.borderColor = "#af886c";
			    }

			    <%-- 비밀번호, 1~20자리의 대소영문자, 특수문자(!,@,#,$,%,^,&,*), _ 만 입력 가능 --%>
			    var pwPattern = /^[\w!@#$%^&*]{1,20}$/;
			    if (!pwPattern.test(registerPw)) {
				    isValid = false;
				    errorPw.style.display = "block";
				    document.querySelector('.ipt3').style.borderColor = "red";
			    } else {
				    errorPw.style.display = "none";
				    document.querySelector('.ipt3').style.borderColor = "#af886c";
			    }

			    <%-- 유효성 검사가 실패시에 폼 제출 방지 --%>
			    if (!isValid) {
				    event.preventDefault();
			    }
		    }
      <%-- 사용자 신청이 실패할 경우 작동할 코드 --%>
	  <%
	     String registerFailure = (String) session.getAttribute("registerFailure");                                                                  
	     String invalidNo = (String) session.getAttribute("invalidNo");                                                                  
	     String idExists = (String) session.getAttribute("idExists");                                                                  
	     String duplicateId = (String) session.getAttribute("duplicateId");                                                                  
	     
	     if (registerFailure != null) { %>
	    	 document.querySelector(".p5").style.display = "block";
	  <%  	 session.removeAttribute("registerFailure");
	     }
	  
	     if (invalidNo != null) { %>
	    	 document.querySelector(".p6").style.display = "block";
	    	 document.querySelector('.ipt1').style.borderColor = "red";
	  <%  	 session.removeAttribute("invalidNo");
	     }
	    
	     if (idExists != null) { %>
	    	 document.querySelector(".p7").style.display = "block";
	  <%  	 session.removeAttribute("idExists");
	      }
	    
	     if (duplicateId != null) { %>
	    	 document.querySelector(".p8").style.display = "block";
	    	 document.querySelector('.ipt2').style.borderColor = "red";
	  <%  	 session.removeAttribute("duplicateId");
	     } %>
	    }
	</script>
</head>

<body>
   <div class="div1">
      <form id="regex" class="form1" action="register.l" method="post">
	     <img class="img1" src="/BeansPaM/img/logo.png" alt="logo" title="logo">
	     <input class="ipt1" id="registerNo" name="registerNo" type="text" placeholder="사원번호를 입력하세요">
		 <p class="p1" id="errorNo">· 사원번호: 1~4자리의 숫자만 입력 가능합니다.</p>
		 <input class="ipt2" id="registerId" name="registerId" type="text" placeholder="아이디를 입력하세요"> 
		 <p class="p2" id="errorId">· 아이디: 1~20자리의 대소영문자,숫자만 입력 가능합니다.</p>
	     <input class="ipt3" id="registerPw" name="registerPw" type="text" placeholder="비밀번호를 입력하세요">
		 <p class="p3" id="errorPw">· 비밀번호: 1~20자리의 대소영문자,특수문자만 입력 가능합니다.</p>
	     <button class="btn1" id="btnLogin" name="btnLogin" type="submit">사용자 신청</button> 
		 <p class="p5">사용자 신청에 실패했습니다.</p> 
		 <p class="p6">올바르지 않은 사원번호를 입력했습니다.</p>
		 <p class="p7">이미 등록된 사용자입니다.</p>
		 <p class="p8">사용중인 아이디입니다. 다른 아이디를 입력해 주세요.</p>
      </form>
   </div>
   <p class="p9">Copyright &copy; Beans PaM All Rights Reserved.</p>	
</body>

</html>