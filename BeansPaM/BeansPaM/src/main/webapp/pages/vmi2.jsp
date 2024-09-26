<%@ page import="java.sql.*" %>
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
        <section class="material-section">
            <div class="header-container">
                <h2>자재 관리 현황</h2>
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
                            String query = "SELECT * FROM material";  

                            stmt = conn.createStatement();
                            rs = stmt.executeQuery(query);
                            int count = 1;

                            while (rs.next()) {
                                %>
                                <tr>
                                    <td><%= count++ %></td>
                                    <td><%= rs.getString("t_corr") %></td>
                                    <td><%= rs.getString("s_incom") %></td>
                                    <td><%= rs.getDate("s_date") %></td>
                                    <td><%= rs.getInt("t_value") %></td>
                                    <td><%= rs.getString("p_no") %></td>
                                    <td><%= rs.getInt("m_no") %></td>
                                    <td><%= rs.getString("m_dept") %></td>
                                    <td><%= rs.getInt("s_amount") %></td>
                                    <td><%= rs.getInt("w_no") %></td>
                                </tr>
                                <%
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
        </section>
    </main>
    <script>
        let isEditMode = false; // 수정 모드 상태
        let isDeleteMode = false; // 삭제 모드 상태
        let isWritable = false; // 작성 및 수정 가능한 상태

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
                // 수정 모드 또는 작성 모드일 때 저장 후 편집 비활성화
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

            tableBody.appendChild(newRow);
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
        document.querySelector('#material-table tbody').addEventListener('click', function(event) {
            if (isDeleteMode && event.target.closest('tr')) {
                event.target.closest('tr').remove();
                updateRowNumbers(); // 삭제 후 행 번호 업데이트
            }
        });

        // 행 번호를 업데이트하는 함수
        function updateRowNumbers() {
            const rows = document.querySelectorAll('#material-table tbody tr');
            rows.forEach((row, index) => {
                row.cells[0].innerText = index + 1; // 각 행의 No 셀에 순차적으로 번호 부여
            });
        }
    </script>
</body>
</html>
