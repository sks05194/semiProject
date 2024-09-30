<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="/BeansPaM/css/approval_write.css">
	<title>결재 상신 페이지</title>
</head>
<body>
	<main class="content">
		<div class="board">
			<h2>결재 상신</h2>
			<div class="approval-type">
				<label for="approvalType">상신 종류 선택:</label>
				<select id="approvalType" onchange="showForm()">
					<option value="attendance">근태</option>
					<option value="trip">출장</option>
					<option value="estimate">견적</option>
				</select>
			</div>
			<form method="post" id="attendanceForm" class="approval-form" enctype="multipart/form-data" action="/BeansPaM/fms/approval_write_action">
				<h3>근태 상신</h3>
				<label for="attendanceType">근태 종류:</label>
				<select id="attendanceType" name="attendanceType">
					<option value="휴가">휴가</option>
					<option value="조퇴">조퇴</option>
					<option value="외출">외출</option>
				</select>
				<label for="startDate">시작 날짜:</label>
				<input type="date" id="startDate" name="startDate">
				<label for="endDate">종료 날짜:</label>
				<input type="date" id="endDate" name="endDate">
				<label for="reason">사유:</label>
				<textarea id="reason" name="reason"></textarea>
				<label for="fileUpload">파일 첨부:</label>
				<input type="file" id="fileUpload" name="fileUpload">
				<div class="button-container">
					<button type="reset" class="cancel-button">취소</button>
					<button type="submit" class="submit-button">상신하기</button>
				</div>
				<input type="hidden" name="hiddenAttendanceForm" value="hiddenAttendanceForm">
			</form>
			<form method="post" id="tripForm" class="approval-form" enctype="multipart/form-data" action="/BeansPaM/fms/approval_write_action">
				<h3>출장 상신</h3>
				<label for="tripDestination">출장 목적지:</label>
				<input type="text" id="tripDestination" name="tripDestination">
				<label for="tripPurpose">출장 목적:</label>
				<input type="text" id="tripPurpose" name="tripPurpose">
				<label for="tripStartDate">출장 시작 날짜:</label>
				<input type="date" id="tripStartDate" name="tripStartDate">
				<label for="tripEndDate">출장 종료 날짜:</label>
				<input type="date" id="tripEndDate" name="tripEndDate">
				<label for="tripDetails">세부사항:</label>
				<textarea id="tripDetails" name="tripDetails"></textarea>
				<label for="tripFileUpload">파일 첨부:</label>
				<input type="file" id="tripFileUpload" name="tripFileUpload">
				<div class="button-container">
					<button type="reset" class="cancel-button">취소</button>
					<button type="submit" class="submit-button">상신하기</button>
				</div>
				<input type="hidden" name="hiddenTripForm" value="hiddenTripForm">
			</form>
			<form method="post" id="estimateForm" class="approval-form" enctype="multipart/form-data" action="/BeansPaM/fms/approval_write_action">
				<h3>견적 상신</h3>
				<label for="estimateCompany">견적서 발행 회사:</label>
				<input type="text" id="estimateCompany" name="estimateCompany">
				<label for="estimateAmount">견적 금액:</label>
				<input type="number" id="estimateAmount" name="estimateAmount">
				<label for="estimateDate">견적서 발행 날짜:</label>
				<input type="date" id="estimateDate" name="estimateDate">
				<label for="estimateDetails">세부 사항:</label>
				<textarea id="estimateDetails" name="estimateDetails"></textarea>
				<label for="estimateFileUpload">파일 첨부:</label>
				<input type="file" id="estimateFileUpload" name="estimateFileUpload">
				<div class="button-container">
					<button type="reset" class="cancel-button">취소</button>
					<button type="submit" class="submit-button">상신하기</button>
				</div>
				<input type="hidden" name="hiddenEstimateForm" value="hiddenEstimateForm">
			</form>
		</div>
	</main>
	<script>
		<%-- 폼 날짜 유효성 검사 --%>
		const startDate = document.getElementById("startDate");
		const endDate = document.getElementById("endDate");

		startDate.addEventListener("change", function() {
			endDate.min = startDate.value;
		});

		endDate.addEventListener("change", function() {
			startDate.max = endDate.value;
		});

		const tripStartDate = document.getElementById("tripStartDate");
		const tripEndDate = document.getElementById("tripEndDate");

		tripStartDate.addEventListener("change", function() {
			tripEndDate.min = tripStartDate.value;
		});

		tripEndDate.addEventListener("change", function() {
			tripStartDate.max = tripEndDate.value;
		});

		const dateInputs = document.querySelectorAll('input[type="date"]');
		const today = new Date();
		today.setDate(today.getDate() + 1);
		const yyyy = today.getFullYear();
		const mm = String(today.getMonth() + 1).padStart(2, '0'); 
		const dd = String(today.getDate()).padStart(2, '0'); 
		const formattedToday = yyyy + "-" + mm + "-" + dd;

		dateInputs.forEach((dateInput) => {
			dateInput.min = formattedToday;
		});
		
		<%-- 폼 선택 함수 --%>
		function showForm() {
			const approvalType = document.getElementById("approvalType").value;
		if (approvalType === "attendance") {
				document.getElementById("attendanceForm").style.display = "block";
				document.getElementById("tripForm").style.display = "none";
				document.getElementById("estimateForm").style.display = "none";
			} else if (approvalType === "trip") {
				document.getElementById("attendanceForm").style.display = "none";
				document.getElementById("tripForm").style.display = "block";
				document.getElementById("estimateForm").style.display = "none";
			} else if (approvalType === "estimate") {
				document.getElementById("attendanceForm").style.display = "none";
				document.getElementById("tripForm").style.display = "none";
				document.getElementById("estimateForm").style.display = "block";
			}
		}
		
		<%-- 폼 입력값 유효성 검사 --%>
		<%-- 근태 폼 --%>
		document.querySelectorAll(".button-container")[0].querySelector(".submit-button").addEventListener("click", function(e) {
			if (document.getElementById("startDate").value === "") {
				e.preventDefault();
				alert("시작 날짜를 입력해주세요");
			}

			else if (document.getElementById("endDate").value === "") {
				e.preventDefault();
				alert("종료 날짜를 입력해주세요");
			}

		});
		
		<%-- 출장 폼 --%>
		document.querySelectorAll(".button-container")[1].querySelector(".submit-button").addEventListener("click", function(e) {
			if (document.getElementById("tripDestination").value === "") {
				e.preventDefault();
				alert("출장 목적지를 입력해주세요");
			}

			else if (document.getElementById("tripPurpose").value === "") {
				e.preventDefault();
				alert("출장 목적을 입력해주세요");
			}

			else if (document.getElementById("tripStartDate").value === "") {
				e.preventDefault();
				alert("출장 시작 날짜를 선택해주세요");
			}

			else if (document.getElementById("tripEndDate").value === "") {
				e.preventDefault();
				alert("출장 종료 날짜를 선택해주세요");
			}
		});
		
		<%-- 견적 폼 --%>
		document.querySelectorAll(".button-container")[2].querySelector(".submit-button").addEventListener("click", function(e) {
			if (document.getElementById("estimateCompany").value === "") {
				e.preventDefault();
				alert("견적서 발행 회사를 입력해주세요");
			}

			else if (document.getElementById("estimateAmount").value === "") {
				e.preventDefault();
				alert("견적 금액을 입력해주세요");
			}

			else if (document.getElementById("estimateDate").value === "") {
				e.preventDefault();
				alert("견적서 발행 날짜를 선택해주세요");
			}
		});
	</script>
	<script src="/BeansPaM/js/menu.js"></script>
</body>
</html>