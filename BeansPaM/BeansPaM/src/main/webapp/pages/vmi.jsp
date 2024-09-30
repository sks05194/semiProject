<%@ page import="java.sql.*, java.util.List, java.util.Map, java.util.ArrayList, java.util.HashMap" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="/BeansPaM/css/vmi.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
	<title>자재 관리 현황 ERP 시스템</title>
</head>
<body>
	<main class="content">
		<h1>자재 관리 현황</h1>
		<section class="material-section">
			<div class="header-container">
				<div class="controls">
					<div class="scheduler">
						<label for="start-date">기간 선택: </label>
						<input type="date" id="start-date" name="start-date" title="검색할 시작 날짜를 선택하세요.">
						<span> ~ </span>
						<input type="date" id="end-date" name="end-date" title="검색할 종료 날짜를 선택하세요.">
					</div>
					<div class="action-buttons">
						<button class="action-button" id="delete-row-btn" title="선택한 행을 삭제합니다.">삭제하기</button>
						<button class="action-button" id="add-row-btn" title="새로운 행을 추가합니다.">작성하기</button>
						<button class="action-button" id="edit-row-btn" title="선택한 행을 수정합니다.">수정하기</button>
						<button class="action-button" id="save-row-btn" title="변경 내용을 저장합니다.">저장하기</button>
					</div>
				</div> 
			</div>
			<table class="status-table" id="material-table">
				<thead>
					<tr>
						<th>No</th> 
						<th>입고처</th> 
						<th>입고번호</th> 
						<th>입고일자</th> 
						<th>수량</th>         
						<th>품번</th> 
						<th>담당자사번</th>    
						<th>부서</th> 
						<th>현재 수량</th> 
						<th>창고 위치</th> 
					</tr>
				</thead>
				<tbody>
					<%
						String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
						String jdbcURL = "jdbc:oracle:thin:@localhost:1521:xe"; 
						String dbUser = "BeansPaM"; 
						String dbPassword = "1111"; 
						Connection conn = null;
						Statement stmt = null;
						ResultSet rs = null;

						try {
							Class.forName(jdbcDriver);
							conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
							String query = "SELECT * FROM material ORDER BY s_date DESC";  

							stmt = conn.createStatement();
							rs = stmt.executeQuery(query);

							List<Map<String, Object>> materials = new ArrayList<>();
							while (rs.next()) {
								Map<String, Object> material = new HashMap<>();
								material.put("t_corr", rs.getString("t_corr"));
								material.put("s_incom", rs.getString("s_incom"));
								material.put("s_date", rs.getDate("s_date"));
								material.put("t_value", rs.getInt("t_value"));
								material.put("p_no", rs.getString("p_no"));
								material.put("m_no", rs.getInt("m_no"));
								material.put("m_dept", rs.getString("m_dept"));
								material.put("s_amount", rs.getInt("s_amount"));
								material.put("w_no", rs.getInt("w_no"));
								materials.add(material);
							}

							int totalRowCount = materials.size();
							int count = totalRowCount;
							for (int i = 0; i < Math.min(totalRowCount, 10); i++) {
								Map<String, Object> material = materials.get(i);
								%>
								<tr>
									<td><%= count %></td>
									<td><%= material.get("t_corr") %></td>
									<td><%= material.get("s_incom") %></td>
									<td><%= material.get("s_date") %></td>
									<td><%= material.get("t_value") %></td>
									<td><%= material.get("p_no") %></td>
									<td><%= material.get("m_no") %></td>
									<td><%= material.get("m_dept") %></td>
									<td><%= material.get("s_amount") %></td>
									<td><%= material.get("w_no") %></td>
								</tr>
								<%
								count--;
							}
							
							for (int i = 10; i < totalRowCount; i++) {
								Map<String, Object> material = materials.get(i);
								%>
								<tr class="hidden">
									<td><%= count %></td>
									<td><%= material.get("t_corr") %></td>
									<td><%= material.get("s_incom") %></td>
									<td><%= material.get("s_date") %></td>
									<td><%= material.get("t_value") %></td>
									<td><%= material.get("p_no") %></td>
									<td><%= material.get("m_no") %></td>
									<td><%= material.get("m_dept") %></td>
									<td><%= material.get("s_amount") %></td>
									<td><%= material.get("w_no") %></td>
								</tr>
								<%
								count--;
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (rs != null) try { rs.close(); } catch (SQLException e) {}
							if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
							if (conn != null) try { conn.close(); } catch (SQLException e) {}
						}
					%>
				</tbody>
			</table>
			<div class="load-more-container">
				<button id="load-more-btn" title="숨겨진 데이터를 추가로 불러옵니다.">더보기</button>
			</div>
			<div class="tooltip" id="tooltip"></div>
		</section>
	</main>
	<script>
		let currentMaxNo = 0; // 현재 최대 고유번호
		const columnName = ['No', '입고처', '입고번호', '입고일자', '수량', '품번', '담당자사번', '부서', '현재 수량', '창고 위치'];
		let sqlArray = [];
		let isEditMode = false; // 수정 모드 상태
		let isDeleteMode = false; // 삭제 모드 상태
		let isWritable = false; // 작성 및 수정 가능한 상태
		let rowsLoaded = 10; // 현재 로드된 행 수

		// 현재 최대 고유번호를 계산하는 함수
		function calculateMaxNo() {
			const rows = document.querySelectorAll('#material-table tbody tr');
			rows.forEach(row => {
				const noCell = row.cells[0]; // No 컬럼
				if (noCell) {
					const noValue = parseInt(noCell.innerText);
					if (noValue > currentMaxNo) {
						currentMaxNo = noValue; // 최대값 업데이트
					}
				}
			});
		}

		// 페이지 로드 시 현재 최대 고유번호 계산
		document.addEventListener('DOMContentLoaded', calculateMaxNo);

		// 도움말 툴팁 기능
		const tooltip = document.getElementById('tooltip');
		document.querySelectorAll('.action-button').forEach(el => {
			el.addEventListener('click', function(event) {
				tooltip.innerText = this.getAttribute('title');
				tooltip.style.display = 'block';
				tooltip.style.left = `${event.target.offsetLeft}px`; // 버튼 아래에 위치
				tooltip.style.top = `${event.target.offsetTop + event.target.offsetHeight}px`;
			});
		});

		document.addEventListener('click', function(event) {
			if (!event.target.classList.contains('action-button')) {
				tooltip.style.display = 'none'; // 버튼이 아닌 곳 클릭 시 툴팁 숨김
			}
		});

		// 작성하기 버튼 기능
		document.getElementById('add-row-btn').addEventListener('click', function() {
			isWritable = true; // 작성 모드 활성화
			addRow(); // 행 추가 함수 호출
			enableEditing(); // 작성 모드에서 편집 가능
			tooltip.style.display = 'none'; // 툴팁 숨김
		});

		// 수정하기 버튼 기능
		document.getElementById('edit-row-btn').addEventListener('click', function() {
			isEditMode = true;
			isDeleteMode = false;
			isWritable = true; // 수정 모드 활성화
			enableEditing(); // 수정 모드에서 편집 가능
			tooltip.style.display = 'none'; // 툴팁 숨김
		});

		// 삭제하기 버튼 기능
		document.getElementById('delete-row-btn').addEventListener('click', function() {
			isDeleteMode = true; // 삭제 모드 활성화
			disableEditing(); // 삭제 모드에서는 편집 불가능
			tooltip.style.display = 'none'; // 툴팁 숨김
			document.querySelectorAll('#material-table tbody tr').forEach(row => {
                row.classList.add('deletable-row'); // 삭제 가능한 행 스타일 적용
            });
		});

		// 저장하기 버튼 기능 - 강동준
		document.getElementById('save-row-btn').addEventListener('click', function () {
			fetch('/BeansPaM/fms/save-vmi', {
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: sqlArray.join(',')
			})
			.then(response => response.text())
			.then(data => {
				alert("저장되었습니다."); 
		        sqlArray = []; 
		        disableEditing(); 
			})
			.catch(error => console.log('error: ' + error));
		});

		// 행 추가 기능
		function addRow() {
			const tableBody = document.querySelector('#material-table tbody');
			const newRow = document.createElement('tr');

			const noCell = document.createElement('td');
			noCell.textContent = ++currentMaxNo; // 고유번호 부여
			newRow.appendChild(noCell);

			for (let i = 0; i < 9; i++) { // 9개의 추가 셀 생성
				const cell = document.createElement('td');
				cell.contentEditable = true; // 편집 가능하게 설정
				newRow.appendChild(cell);
			}

			tableBody.insertBefore(newRow, tableBody.firstChild);

			// 행 클릭 시 삭제 기능 추가
			newRow.addEventListener('click', function() {
				if (isDeleteMode) {
					this.remove(); // 선택한 행 삭제
				}
			});
		}

		// 편집 가능하게 설정하는 함수
		function enableEditing() {
		    if (isWritable) {
		        document.querySelectorAll('#material-table tbody tr').forEach(row => {
		            const cells = row.children;
		            const editableColumns = [2, 3, 5, 6, 8, 9];
		            editableColumns.forEach(index => {
		                if (cells[index]) {
		                    cells[index].contentEditable = true; // 편집 가능하게 설정
		                    cells[index].classList.add('editable'); // 편집 가능할 때 스타일 적용
		                }
		            });
		        });
		    }
		}

		// 편집 불가능하게 설정하는 함수
		function disableEditing() {
		    document.querySelectorAll('#material-table tbody tr').forEach(row => {
		        const cells = row.children;
		        // 수정 가능한 컬럼 인덱스
		        const editableColumns = [2, 3, 5, 6, 8, 9];
		        editableColumns.forEach(index => {
		            if (cells[index]) {
		                cells[index].contentEditable = false; // 편집 불가능하게 설정
		                cells[index].classList.remove('editable'); // 편집 불가능할 때 스타일 제거
		            }
		        });
		    });
		}

		
		// 기존 행 클릭 시 삭제 기능 추가
		document.querySelectorAll('#material-table tbody tr').forEach(row => {
			row.addEventListener('click', function() {
				if (isDeleteMode) {
					this.remove(); // 선택한 행 삭제
				}
			});
		});
		
		// 날짜 필터링 기능
		const startDateInput = document.getElementById('start-date');
		const endDateInput = document.getElementById('end-date');

		// 날짜 선택 이벤트 핸들러 추가
		startDateInput.addEventListener('change', function() {
			const startDate = new Date(startDateInput.value);
			const minEndDate = new Date(startDate);
			minEndDate.setDate(startDate.getDate() + 1);
			endDateInput.min = minEndDate.toISOString().split('T')[0];

			if (endDateInput.value && new Date(endDateInput.value) < minEndDate) {
				endDateInput.value = '';
			}
		});
		endDateInput.addEventListener('change', filterTable);

		function filterTable() {
			const startDate = new Date(startDateInput.value);
			const endDate = new Date(endDateInput.value);

			if (!endDateInput.value) {
				endDate.setDate(endDate.getDate() + 1);
			} else {
				endDate.setDate(endDate.getDate() + 1);
			}

			const rows = document.querySelectorAll('#material-table tbody tr');

			rows.forEach(row => {
				const dateCell = row.cells[3]; // s_date 컬럼
				if (dateCell) {
					const rowDate = new Date(dateCell.innerText);
					if (rowDate >= startDate && rowDate < endDate) {
						row.classList.remove('hidden');
					} else {
						row.classList.add('hidden');
					}
				}
			});

			// 날짜 검색 후 버튼 텍스트 변경
			const loadMoreBtn = document.getElementById('load-more-btn');
			loadMoreBtn.textContent = '돌아가기';
			loadMoreBtn.onclick = function() {
				rows.forEach(row => row.classList.remove('hidden')); // 모든 데이터 보여주기
				loadMoreBtn.textContent = '더보기'; // 텍스트 원래대로 복구
				loadMoreBtn.onclick = null; // 기존 클릭 이벤트 리셋
			};
		}

		// "더보기" 버튼 클릭 시 숨겨진 데이터 표시
		document.getElementById('load-more-btn').addEventListener('click', function() {
			const hiddenRows = document.querySelectorAll('#material-table tbody tr.hidden');
			const rowsToShow = Math.min(hiddenRows.length, 10); // 한 번에 10줄 보여주기

			for (let i = 0; i < rowsToShow; i++) {
				if (hiddenRows[i]) {
					hiddenRows[i].classList.remove('hidden');
				}
			}

			rowsLoaded += rowsToShow; // 로드된 행 수 업데이트

			// 숨겨진 데이터가 더 이상 없으면 버튼 비활성화
			if (hiddenRows.length <= rowsToShow) {
				this.disabled = true;
				this.style.display = 'none';
			}
		});
	</script>
	<script src="/BeansPaM/js/menu.js"></script>
</body>
</html>
