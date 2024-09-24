<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>사용자 신청 화면</title>
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.9.0/css/all.min.css">
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
			width: 480px;
			margin: 0px auto;
			margin-bottom: 30px;
		}

		.ipt2 {
			width: 480px;
			margin: 0px auto;
			margin-bottom: 30px;
		}

		.ipt3 {
			width: 480px;
			margin: 0px auto;
			margin-bottom: 30px;
			padding-right: 50px;
		}

		.div1 {
			display: flex;
			background-color: white;
		}
		
		.div2 {
		    text-align: center;
		    margin-bottom: 20px;
		}
		
		.div3 {
		    position: relative;
			margin-bottom: 40px;
		}

		button {
			display: block;
			height: 50px;
			border-radius: 5px;
			border: 0px;
			outline: none;
			background-color: #945121;
			color: white;
		}

		button:hover {
			cursor: pointer;
		}

		.btn1 {
			display: inline-block;
			margin: 0px auto;
			margin-bottom: -100px;
			margin-top: 55px;
			margin-left: 7px;
			width: 480px;
			font-size: 16px;
			font-weight: bold;
		}

		.form1 {
			width: 550px;
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
			font-size: 16px;
		}

		.p2 {
			display: none;
			margin: 0px auto;
			margin-top: -20px;
			color: red;
			font-size: 16px;
		}

		.p3 {
			display: none;
			margin: 0px auto;
			margin-top: 20px;
			color: red;
			font-size: 16px;
		}

		
		.p4 {
			position: absolute;
			display: none;
			color: red;
			font-size: 18px; 
 			top: 30px; 
 			margin-left: 10px;
		}

		.p5 {
			position: absolute;
			display: none;
			color: red;
			font-size: 18px;
 			top: 30px; 
 			margin-left: 10px;
		}

		.p6 {
			position: absolute;
			display: none;
			color: red;
			font-size: 18px;
 			top: 30px; 
 			margin-left: 10px;
		}		

		.p7 {
			position: absolute;
			display: none;
			color: red;
			font-size: 18px;
 			top: 30px; 
 			margin-left: 10px;
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
		    
			<%-- 비밀번호를 입력할 때에만 눈 아이콘 표시하는 코드--%>
			$('.ipt3').on('input', function () {
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
				const passwordInput = $(this).siblings('#registerPw');
				const isActive = passwordInput.attr('type') === 'text';

				if (isActive) {
					$(this).find('.fa-eye-slash').removeClass('fa-eye-slash').addClass('fa-eye');
					passwordInput.attr('type', 'password');
				} else {
					$(this).find('.fa-eye').removeClass('fa-eye').addClass('fa-eye-slash');
					passwordInput.attr('type', 'text');
				}
			});

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
	     <input class="ipt3" id="registerPw" name="registerPw" type="password" placeholder="비밀번호를 입력하세요">
		 <div class="eyes">
		    <i class="fa fa-eye"></i>
		 </div>
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