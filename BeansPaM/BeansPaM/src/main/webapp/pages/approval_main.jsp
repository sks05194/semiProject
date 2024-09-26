<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="/BeansPaM/css/approval_main.css">
<title>결재 현황 리스트</title>
<style>
</style>
</head>
<body>
	<main class="content">
		<div class="board">
			<h2>결재 현황 리스트</h2>
			<!-- 버튼 컨테이너 -->
			<div class="button-container">
				<button onclick="location.href='./approval_write'">결재상신</button>
				<button>결재 승인</button>
				<button>결재 반려</button>
				<button>결재 취소</button>
				<button>문서 수정</button>
				<button>문서 삭제</button>
			</div>
			<table>
				<thead>
					<tr>
						<th>No.</th>
						<th style="width: 60%">제목</th>
						<th>작성자</th>
						<th>작성일자</th>
						<th>결재상태</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>10</td>
						<td>N사 견적서</td>
						<td>설보라 대리</td>
						<td>2024-09-10</td>
						<td>결재대기</td>
					</tr>
					<tr>
						<td>9</td>
						<td>S사 견적서</td>
						<td>송상훈 대리</td>
						<td>2024-09-08</td>
						<td>결재대기</td>
					</tr>
					<tr>
						<td>8</td>
						<td>출장서</td>
						<td>이귀화 대리</td>
						<td>2024-09-06</td>
						<td>결재완료</td>
					</tr>
					<tr>
						<td>7</td>
						<td>지출결의서</td>
						<td>한지수 대리</td>
						<td>2024-09-05</td>
						<td>결재완료</td>
					</tr>
					<tr>
						<td>6</td>
						<td>휴가신청서</td>
						<td>임성현 대리</td>
						<td>2024-09-03</td>
						<td>결재완료</td>
					</tr>
					<tr>
						<td>5</td>
						<td>지출결의서</td>
						<td>손동진 대리</td>
						<td>2024-09-02</td>
						<td>결재완료</td>
					</tr>
					<tr>
						<td>4</td>
						<td>업무기안서</td>
						<td>민기홍 대리</td>
						<td>2024-09-02</td>
						<td>결재완료</td>
					</tr>
					<tr>
						<td>3</td>
						<td>P사 견적서</td>
						<td>고길동 사원</td>
						<td>2024-09-01</td>
						<td>결재완료</td>
					</tr>
					<tr>
						<td>2</td>
						<td>N사 견적서</td>
						<td>이진욱 사원</td>
						<td>2024-08-29</td>
						<td>결재완료</td>
					</tr>
					<tr>
						<td>1</td>
						<td>N사 견적서</td>
						<td>지창욱 사원</td>
						<td>2024-09-28</td>
						<td>결재완료</td>
					</tr>
				</tbody>
			</table>
			<div class="actions-container">
				<!-- 검색창 -->
				<div class="search-container">
					<input type="search" placeholder="검색할 내용 입력">
					<button>검색</button>
				</div>

				<!-- 페이지네이션 -->
				<div class="pagination" style="margin-left: 0;">
					<a href="#">&laquo;</a> <a href="#">1</a> <a href="#">2</a> <a
						href="#">3</a> <a href="#">&raquo;</a>
				</div>


			</div>
		</div>
	</main>
	<script>
		// URL 파라미터에서 값을 가져오는 함수
		function getParameterByName(name) {
			const params = new URLSearchParams(window.location.search);
			return params.get(name);
		}

		// URL 파라미터에서 데이터 가져오기
		const type = getParameterByName('type');
		const subject = getParameterByName('subject');
		const description = getParameterByName('description');

		if (type && subject && description) {
			// 새로운 행을 테이블 맨 위에 추가
			const postTableBody = document.querySelector('table tbody');
			const newRow = document.createElement('tr');

			// 게시물 번호 결정 (가장 큰 번호에 1을 더함)
			const firstRow = postTableBody.querySelector('tr');
			let newNumber = '1'; // 기본값
			if (firstRow) {
				const firstNumberCell = firstRow.querySelector('td');
				const firstNumber = firstNumberCell.textContent.trim();

				const firstNumInt = parseInt(firstNumber, 10);
				if (!isNaN(firstNumInt)) {
					newNumber = firstNumInt + 1;
				}
			}

			const today = new Date().toISOString().split('T')[0];
			newRow.innerHTML = `<td>${newNumber}</td><td>${subject}</td><td>user</td><td>${today}</td><td>결재대기</td>`;
			// 행에 클릭 이벤트 추가 (필요에 따라 수정)
			// newRow.setAttribute('onclick', "location.href='./Detail_page.html'");

			// 테이블 맨 위에 새로운 행 추가
			postTableBody.insertBefore(newRow, postTableBody.firstChild);
		}
	</script>
	<script src="/BeansPaM/js/menu.js"></script>
</body>
</html>