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
			<div class="search-container">
				<h2>결재 현황</h2>
				<form id="searchForm" method="get" class="search-form" action="approval_search_action">
					<select class="search-select" name="sel" id="sel">
						<option value="D_NO">결재번호</option>
						<option value="D_TITLE">제목</option>
						<option value="M_NAME">작성자</option>
						<option value="D_REQUEST">작성일자</option>
						<option value="D_RESPONSE">결재상태</option>
					</select> 
					<input type="search" name="find" id="find" class="search-input" placeholder="검색할 내용 입력">
					<button type=submit class="search-button">검색</button>
				</form>
			</div>
			<table>
				<thead>
					<tr>
						<th>결재번호</th>
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
									<td><a href="approval_contents_action?d_no=${documentVO.d_no}">${documentVO.d_title}</a></td>
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
					<c:forEach var="num" begin="${pageVO.startPage }" end="${pageVO.endPage }">
						<c:choose>
							<c:when test="${not empty searchCheck}">
								<div class="${pageVO.pageNum eq num ? 'active' : '' }">
									<a href="approval_search_action?pageNum=${num }&amount=${pageVO.amount}&sel=${sel}&sfind=${sfind}&nfind=${nfind}">${num }</a>
								</div>
							</c:when>
							<c:otherwise>
								<div class="${pageVO.pageNum eq num ? 'active' : '' }">
									<a href="approval_main_action?pageNum=${num }&amount=${pageVO.amount}">${num }</a>
								</div>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</div>
			</div>
		</div>
	</main>
	<script>
		<%-- 검색 유효성 검사 --%>
		var sel = "${sel}";
		var find = "${find}";
	
		if (sel == "D_NO") {
			document.getElementById("sel").selectedIndex = 0;
			document.getElementById("find").value = find;
		}
	
		else if (sel == "D_TITLE") {
			document.getElementById("sel").selectedIndex = 1;
			document.getElementById("find").value = find;
		}
	
		else if (sel == "M_NAME") {
			document.getElementById("sel").selectedIndex = 2;
			document.getElementById("find").value = find;
		}
	
		else if (sel == "D_REQUEST") {
			document.getElementById("sel").selectedIndex = 3;
			document.getElementById("find").value = find;
		}
	
		else if (sel == "D_RESPONSE") {
			document.getElementById("sel").selectedIndex = 4;
			document.getElementById("find").value = find;
		}
	
		var inputElement = document.getElementById("find");
	
		inputElement.addEventListener("input", function() {
			var input = inputElement.value.trim();
			inputElement.setCustomValidity("");
	
			var select = document.getElementById("sel").value;
	
			if (input === "") { <%-- 아무값도 입력 안하면 초기화 --%>
				inputElement.setCustomValidity(""); 
			} else {
				if (select === "D_NO" || select === "D_REQUEST") { <%-- 결재번호와 작성일자 검사 --%>
					if (input === "" || isNaN(parseFloat(input))) {
						inputElement.setCustomValidity("숫자값만 입력하세요.");
					}
				} else if (select === "D_TITLE") { <%-- 제목 검사 --%>
					if (input !== "" && (input !== "휴가" && input !== "조퇴" && input !== "외출" && input !== "출장" && input !== "견적")) {
						inputElement.setCustomValidity("휴가, 조퇴, 외출, 출장, 견적 중 하나를 정확히 입력하세요.");
					}
				} else if (select === "D_RESPONSE") { <%-- 결제 상태 검사 --%>
					if (input !== "" && (input !== "대기" && input !== "완료")) {
						inputElement.setCustomValidity("대기, 완료 중 하나를 입력하세요.");
					}
				}
			}
			inputElement.reportValidity();
		});
	</script>
	<script src="/BeansPaM/js/menu.js"></script>
</body>
</html>