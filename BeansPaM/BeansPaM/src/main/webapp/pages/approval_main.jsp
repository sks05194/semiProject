<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="/BeansPaM/css/approval_main.css">
	<title>결재 현황</title>
</head>

<body>
	<main class="content">
		<div class="board">
			<h2>결재 현황</h2>
			<!-- 버튼 컨테이너 -->
			<div class="button-container">
				<button onclick="location.href='/BeansPaM/fms/approval_write'">결재 상신</button>
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
					<c:choose>
						<c:when test="${not empty getPageList}">
							<c:forEach var="documentVO" items="${getPageList}">
								<tr>
									<td>${documentVO.d_no}</td>
									<td>
										<a href="approval_contents_action?d_no=${documentVO.d_no}">${documentVO.d_title}</a>
									</td>
									<td>${documentVO.m_name} ${documentVO.m_position}</td>
									<td>${documentVO.d_request}</td>
									<td>
										<c:choose>
											<c:when test="${empty documentVO.d_response}">
												${"결재대기"}
											</c:when>
											<c:otherwise>
												${"결재완료"}
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="6" align="center">게시글이 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<div class="actions-container">
				<div class="pagination" style="margin-left: 0;">

					<!-- 2. 이전버튼 활성화 여부 -->
					<c:if test="${pageVO.prev }">
						<a href="approval_main_action?pageNum=${pageVO.startPage - 1 }&amount=${pageVO.amount}">이전</a>
					</c:if>

					<!-- 1. 페이지번호 처리 -->
					<c:forEach var="num" begin="${pageVO.startPage }" end="${pageVO.endPage }">
						<div class="${pageVO.pageNum eq num ? 'active' : '' }"><a href="approval_main_action?pageNum=${num }&amount=${pageVO.amount}">${num }</a>
						</div>
					</c:forEach>

					<!-- 3. 다음버튼 활성화 여부 -->
					<c:if test="${pageVO.next }">
						<a href="approval_main_action?pageNum=${pageVO.endPage + 1 }&amount=${pageVO.amount}">다음</a>
					</c:if>
				</div>
				<!-- <div class="search-container">
					<input type="search" placeholder="검색할 내용 입력">
					<button>검색</button>
				</div> -->
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