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
                        <input type="date" id="start-date" name="start-date">
                        <span> ~ </span>
                        <input type="date" id="end-date" name="end-date">
                    </div>
                    <div class="action-buttons">
                        <button class="action-button" id="delete-row-btn">삭제하기</button>
                        <button class="action-button" id="add-row-btn">작성하기</button>
                        <button class="action-button" id="edit-row-btn">수정하기</button>
                        <button class="action-button" id="save-row-btn">저장하기</button>
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
				
				            // 데이터를 모두 메모리에 저장
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
				
				            // 총 데이터 수를 기준으로 번호 매기기
				            int totalRowCount = materials.size();
				            int count = totalRowCount; // 마지막 번호에서 시작
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
				            
				            // 나머지 데이터 숨기기
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
	            <button id="load-more-btn">더보기</button>
            </div>
        </section>
    </main>
    <script>
    // 작성하기 버튼 기능
    document.getElementById('add-row-btn').addEventListener('click', function() {
        isWritable = true; // 작성 모드 활성화
        addRow(); // 행 추가 함수 호출
        enableEditing(); // 작성 모드에서 편집 가능
    });

    // 수정하기 버튼 기능
    document.getElementById('edit-row-btn').addEventListener('click', function() {
        isEditMode = true;
        isDeleteMode = false;
        isWritable = true; // 수정 모드 활성화
        enableEditing(); // 수정 모드에서 편집 가능
    });

    // 삭제하기 버튼 기능
    document.getElementById('delete-row-btn').addEventListener('click', function() {
        isEditMode = false;
        isDeleteMode = true;
        isWritable = false; // 삭제 모드에서는 편집 불가
        disableEditing(); // 삭제 모드에서 편집 비활성화
        document.querySelectorAll('#material-table tbody tr').forEach(row => {
            row.classList.add('deletable-row'); // 삭제 가능한 행 스타일 적용
        });
    });

    // 저장하기 버튼 기능
    document.getElementById('save-row-btn').addEventListener('click', function() {
        if (isEditMode || isWritable) {
            disableEditing(); // 저장 후 편집 비활성화
            alert('수정이 완료되었습니다.');
            isEditMode = false;
            isWritable = false;
        } else {
            alert('저장이 완료되었습니다.');
        }

        // 삭제 모드 종료 시 삭제 가능한 스타일 제거
        if (isDeleteMode) {
            document.querySelectorAll('#material-table tbody tr').forEach(row => {
                row.classList.remove('deletable-row');
            });
            isDeleteMode = false;
        }
    });

    // 행 추가 기능
    function addRow() {
        const tableBody = document.querySelector('#material-table tbody');
        const newRow = document.createElement('tr');

        const noCell = document.createElement('td');
        noCell.innerText = tableBody.rows.length + 1; // 자동으로 번호 증가
        newRow.appendChild(noCell);

        for (let i = 0; i < 9; i++) {
            const newCell = document.createElement('td');
            newCell.contentEditable = true; // 셀을 편집 가능하게 설정
            newCell.innerText = ''; // 빈 셀
            newRow.appendChild(newCell);
        }

        // 새로운 행에 클릭 이벤트 추가
        newRow.addEventListener('click', function() {
            if (isDeleteMode) {
                this.remove(); // 선택한 행 삭제
            }
        });

        tableBody.insertBefore(newRow, tableBody.firstChild);
    }

    // 편집 가능하게 설정하는 함수
    function enableEditing() {
        if (isWritable) {
            document.querySelectorAll('#material-table tbody tr td').forEach(cell => {
                cell.contentEditable = true; // 편집 가능할 때 설정
                cell.classList.add('editable'); // 편집 가능할 때 스타일 적용
            });
        }
    }

    // 편집 불가능하게 설정하는 함수
    function disableEditing() {
        document.querySelectorAll('#material-table tbody tr td').forEach(cell => {
            cell.contentEditable = false; // 편집 불가능할 때 설정
            cell.classList.remove('editable'); // 편집 불가능할 때 스타일 제거
        });
    }

    // 행 삭제 기능 - 행 클릭 시 삭제
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
        // end-date의 최소 선택 가능 날짜를 start-date + 1일로 설정
        const minEndDate = new Date(startDate);
        minEndDate.setDate(startDate.getDate() + 1);
        
        // end-date의 최소값을 업데이트
        endDateInput.min = minEndDate.toISOString().split('T')[0];
        
        // 만약 end-date의 값이 minEndDate보다 이전이면 초기화
        if (endDateInput.value && new Date(endDateInput.value) < minEndDate) {
            endDateInput.value = '';
        }
    });
    endDateInput.addEventListener('change', filterTable);

    function filterTable() {
        const startDate = new Date(startDateInput.value);
        const endDate = new Date(endDateInput.value);
        
        // endDate가 선택되지 않은 경우 오늘 날짜로 설정
        if (!endDateInput.value) {
            endDate.setDate(endDate.getDate() + 1); // 범위를 위해 하루 더함
        } else {
            endDate.setDate(endDate.getDate() + 1); // 범위를 위해 하루 더함
        }

        const rows = document.querySelectorAll('#material-table tbody tr');

        rows.forEach(row => {
            const dateCell = row.cells[3]; // s_date 컬럼, 0부터 시작하므로 3번 인덱스
            if (dateCell) {
                const rowDate = new Date(dateCell.innerText);
                if (rowDate >= startDate && rowDate < endDate) {
                    row.classList.remove('hidden'); // 범위에 포함되면 보이기
                } else {
                    row.classList.add('hidden'); // 범위에 포함되지 않으면 숨기기
                }
            }
        });
    }

    let isEditMode = false; // 수정 모드 상태
    let isDeleteMode = false; // 삭제 모드 상태
    let isWritable = false; // 작성 및 수정 가능한 상태
    let rowsLoaded = 10; // 현재 로드된 행 수

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
