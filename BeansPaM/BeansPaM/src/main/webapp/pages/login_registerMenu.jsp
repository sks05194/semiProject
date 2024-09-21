<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>사용자 신청 화면</title>
	<script src="/BeansPaM/js/jquery.js"></script>
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
			width: 400px;
			margin: 0px auto;
			margin-bottom: 20px;
		}

		.ipt2 {
			width: 400px;
			margin: 0px auto;
			margin-bottom: 20px;
		}

		.ipt3 {
			width: 400px;
			margin: 0px auto;
			margin-bottom: 20px;
		}

		.div1 {
			display: flex;
			background-color: white;
			max-width: 100%;
		}
		
		.div2 {
		    text-align: center;
		}
		
		.div3 {
		    position: relative;
			margin-bottom: 40px;
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
			font-size: 14px;
		}

		button:hover {
			cursor: pointer;
		}

		.btn1 {
			display: inline-block;
			margin: 0px auto;
			margin-bottom: -100px;
			margin-top: 30px;
			margin-left: 7px;
			width: 400px;
			font-size: 16px;
		}

		.form1 {
			width: 480px;
			border: 0px;
			border-radius: 5px;
			border: 2px solid #945121;
			margin: 0px auto;
			margin-top: 200px;
			padding: 30px;
		}

		.img1 {
			display: inline-block;
 			width: 190px; 
			margin: 0px auto;
			margin-bottom: 5px;
		}

		.img1:hover {
			cursor: pointer;
		}
		
		
		.p1 {
			display: none;
			margin: 0px auto;
			margin-top: -20px;
			color: red;
			font-size: 18px;
		}

		.p2 {
			display: none;
			margin: 0px auto;
			margin-top: -20px;
			color: red;
			font-size: 18px;
		}

		.p3 {
			display: none;
			margin: 0px auto;
			margin-top: -20px;
			color: red;
			font-size: 18px;
		}

		
		.p4 {
			position: absolute;
			display: none;
			color: red;
			font-size: 18px; 
 			top: 30px; 
		}

		.p5 {
			position: absolute;
			display: none;
			color: red;
			font-size: 18px;
 			top: 30px; 
		}

		.p6 {
			position: absolute;
			display: none;
			color: red;
			font-size: 18px;
 			top: 30px; 
		}		

		.p7 {
			position: absolute;
			display: none;
			color: red;
			font-size: 18px;
 			top: 30px; 
		}	
	</style>
	<script>
		window.onload = function() {
		    <%-- 로고 클릭시 로그인 화면으로 이동하는 코드 --%>
			document.querySelector(".img1").addEventListener("click", function() {
				window.location = "loginMenu.l";
			});	

			<%-- 유효성 검사가 실행되는 조건 코드 --%>
			document.getElementById('regex').addEventListener("submit", regex1);
            $('.ipt1').on('blur', regex2);
            $('.ipt2').on('blur', regex3);
            $('.ipt3').on('blur', regex4);

            <%-- 사용자 신청 버튼을 클릭했을때 이벤트 --%>
		    function regex1 (event) {
			    <%-- 입력값 초기화 --%>
			    let registerNo = document.getElementById('registerNo').value;
			    let registerId = document.getElementById('registerId').value;
			    let registerPw = document.getElementById('registerPw').value;

			    <%-- 에러 메세지 초기화 --%>
			    let errorNo = document.getElementById('errorNo');
			    let errorId = document.getElementById('errorId');
                let errorPw = document.getElementById('errorPw');
		
			    <%-- 유효성 검사 --%>
			    let isValid = true;

			    <%-- 사원번호, 1~4자리의 숫자만 입력가능 --%>
			    let noPattern = /^\d{1,4}$/;
			    if (!noPattern.test(registerNo)) {
				    isValid = false;
				    errorNo.style.display = "block";
				    document.querySelector('.ipt1').style.borderColor = "red";
			    } else {
				    errorNo.style.display = "none";
				    document.querySelector('.ipt1').style.borderColor = "#af886c";
			    }

			    <%-- 아이디, 5~20자리의 대소영문자,숫자,_ 만 입력가능 --%>
			    let idPattern = /^\w{5,20}$/;
			    if (!idPattern.test(registerId)) {
				    isValid = false;
				    errorId.style.display = "block";
				    document.querySelector('.ipt2').style.borderColor = "red";
			    } else {
				    errorId.style.display = "none";
				    document.querySelector('.ipt2').style.borderColor = "#af886c";
			    }

			    <%-- 비밀번호, 5~20자리의 대소영문자, 특수문자(!,@,#,$,%,^,&,*), _ 만 입력 가능 --%>
			    let pwPattern = /^[\w!@#$%^&*]{5,20}$/;
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
		    
		    <%-- 사원번호 blur 이벤트 --%>
		    function regex2 (event) {
		    	let registerNo = document.getElementById('registerNo').value;
		    	let errorNo = document.getElementById('errorNo');
		    	
		    	<%-- 사원번호, 1~4자리의 숫자만 입력가능 --%>
			    let noPattern = /^\d{1,4}$/;
			    if (!noPattern.test(registerNo)) {
				    isValid = false;
				    errorNo.style.display = "block";
				    document.querySelector('.ipt1').style.borderColor = "red";
			    } else {
				    errorNo.style.display = "none";
				    document.querySelector('.ipt1').style.borderColor = "#af886c";
			    }
		    }
		    
		    <%-- 아이디 blur 이벤트 --%>
		    function regex3 (event) {
		    	let registerId = document.getElementById('registerId').value;
		    	let errorId = document.getElementById('errorId');
		    	
			    <%-- 아이디, 5~20자리의 대소영문자,숫자,_ 만 입력가능 --%>
			    let idPattern = /^\w{5,20}$/;
			    if (!idPattern.test(registerId)) {
				    isValid = false;
				    errorId.style.display = "block";
				    document.querySelector('.ipt2').style.borderColor = "red";
			    } else {
				    errorId.style.display = "none";
				    document.querySelector('.ipt2').style.borderColor = "#af886c";
			    }
		    }
		    
		    <%-- 비밀번호 blur 이벤트 --%>
		    function regex4 (event) {
		    	let registerPw = document.getElementById('registerPw').value;
		    	let errorPw = document.getElementById('errorPw');
		    	
			    <%-- 비밀번호, 5~20자리의 대소영문자, 특수문자(!,@,#,$,%,^,&,*), _ 만 입력 가능 --%>
			    let pwPattern = /^[\w!@#$%^&*]{5,20}$/;
			    if (!pwPattern.test(registerPw)) {
				    isValid = false;
				    errorPw.style.display = "block";
				    document.querySelector('.ipt3').style.borderColor = "red";
			    } else {
				    errorPw.style.display = "none";
				    document.querySelector('.ipt3').style.borderColor = "#af886c";
			    }
		    }

      <%-- 사용자 신청이 실패할 경우 작동할 코드 --%>
	  <%
	     String registerFailure = request.getParameter("registerFailure");                                                                  
	     String invalidNo = request.getParameter("invalidNo");                                                                  
	     String idExists = request.getParameter("idExists");                                                                  
	     String duplicateId = request.getParameter("duplicateId");
	     
	     if (registerFailure != null) { %>
	    	 document.querySelector(".p4").style.display = "block";
	  <%  	 
	     }
	  
	     if (invalidNo != null) { %>
	    	 document.querySelector(".p5").style.display = "block";
	    	 document.querySelector('.ipt1').style.borderColor = "red";
	  <%  	 
	     }
	    
	     if (idExists != null) { %>
	    	 document.querySelector(".p6").style.display = "block";
	  <%  	 
	      }
	    
	     if (duplicateId != null) { %>
	    	 document.querySelector(".p7").style.display = "block";
	    	 document.querySelector('.ipt2').style.borderColor = "red";
	  <%  }  %>
	  
		<%-- 주소창의 QueryString으로 처리된 문자열 제거하기 --%>
		<%-- 참고사이트: https://lahuman.github.io/javascript-remove-url-parameter/ --%>
         history.replaceState({}, null, location.pathname);
	    }
	</script>
</head>

<body>
   <div class="div1">
      <form id="regex" class="form1" action="register.l" method="post">
         <div class="div2">
	        <img class="img1" src="/BeansPaM/img/logo.png" alt="logo" title="logo">
	     </div>
	     <input class="ipt1" id="registerNo" name="registerNo" type="text" placeholder="사원번호를 입력하세요">
		 <p class="p1" id="errorNo">· 사원번호: 1~4자리의 숫자만 입력 가능합니다.</p>
		 <input class="ipt2" id="registerId" name="registerId" type="text" placeholder="아이디를 입력하세요"> 
		 <p class="p2" id="errorId">· 아이디: 5~20자리의 대소영문자,숫자만 입력 가능합니다.</p>
	     <input class="ipt3" id="registerPw" name="registerPw" type="text" placeholder="비밀번호를 입력하세요">
		 <p class="p3" id="errorPw">· 비밀번호: 5~20자리의 대소영문자,특수문자만 입력 가능합니다.</p>
	     <button class="btn1" id="btnLogin" name="btnLogin" type="submit">사용자 신청</button>
	     <div class="div3"> 
		    <p class="p4">사용자 신청에 실패했습니다.</p> 
		    <p class="p5">올바르지 않은 사원번호를 입력했습니다.</p>
		    <p class="p6">이미 등록된 사용자입니다.</p>
		    <p class="p7">중복 아이디입니다. 다른 아이디를 입력해 주세요.</p>
		 </div>
      </form>
   </div>	
</body>

</html>