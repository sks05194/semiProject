<%@ page import="java.util.List" %>
<%@ page import="java.sql.Date" %>
<%@ page import="dao.MaterialDAO" %>
<%@ page import="vo.MaterialVO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/BeansPaM/css/vmi.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <title>자재 관리 현황 ERP 시스템</title>
</head>
<body>
    <main class="content">
        <section class="material-section">
            <h2>자재 관리 현황</h2>
            <div class="controls">
                <button id="add-row-btn">작성하기</button>
                <button id="edit-row-btn">수정하기</button>
                <button id="delete-row-btn">삭제하기</button>
                <button id="save-row-btn">저장하기</button>
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
                        MaterialDAO dao = new MaterialDAO();
                        List<MaterialVO> materials = dao.getAllMaterials();
                        int count = 1;

                        for (MaterialVO material : materials) {
                    %>
                    <tr>
                        <td><%= count++ %></td>
                        <td><%= material.getT_corr() %></td>
                        <td><%= material.getS_incom() %></td>
                        <td><%= material.getS_date() %></td>
                        <td><%= material.getT_value() %></td>
                        <td><%= material.getP_no() %></td>
                        <td><%= material.getM_no() %></td>
                        <td><%= material.getM_dept() %></td>
                        <td><%= material.getS_amount() %></td>
                        <td><%= material.getW_no() %></td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </section>
    </main>
    
    <script>
        let isEditMode = false;
        let isDeleteMode = false;
        let isWritable = false;
        let selectedIds = [];

        document.getElementById('add-row-btn').addEventListener('click', function() {
            isWritable = true;
            addRow();
            enableEditing();
        });

        document.getElementById('edit-row-btn').addEventListener('click', function() {
            isEditMode = true;
            isWritable = true;
            enableEditing();
        });

        document.getElementById('delete-row-btn').addEventListener('click', function() {
            isDeleteMode = true;
            disableEditing();
            document.querySelectorAll('#material-table tbody tr').forEach(row => {
                row.classList.add('deletable-row');
            });
        });

        document.getElementById('save-row-btn').addEventListener('click', function() {
            if (isWritable) {
                // Add new material
                const newRow = getRowData();
                $.post('saveMaterial.jsp', { 
                    t_corr: newRow.t_corr,
                    s_incom: newRow.s_incom,
                    s_date: newRow.s_date,
                    t_value: newRow.t_value,
                    p_no: newRow.p_no,
                    m_no: newRow.m_no,
                    m_dept: newRow.m_dept,
                    s_amount: newRow.s_amount,
                    w_no: newRow.w_no 
                }, function() {
                    alert('새로운 자재가 추가되었습니다.');
                    location.reload();
                });
            } else if (isEditMode) {
                // Update existing material
                const updatedRow = getRowData();
                $.post('updateMaterial.jsp', { 
                    id: updatedRow.id,
                    t_corr: updatedRow.t_corr,
                    s_incom: updatedRow.s_incom,
                    s_date: updatedRow.s_date,
                    t_value: updatedRow.t_value,
                    p_no: updatedRow.p_no,
                    m_no: updatedRow.m_no,
                    m_dept: updatedRow.m_dept,
                    s_amount: updatedRow.s_amount,
                    w_no: updatedRow.w_no 
                }, function() {
                    alert('자재가 수정되었습니다.');
                    location.reload();
                });
            } else if (isDeleteMode) {
                // Delete selected materials
                $.post('deleteMaterials.jsp', { ids: JSON.stringify(selectedIds) }, function() {
                    alert('자재가 삭제되었습니다.');
                    location.reload();
                });
            }
            resetModes();
        });

        function addRow() {
            const tableBody = document.querySelector('#material-table tbody');
            const newRow = document.createElement('tr');

            const noCell = document.createElement('td');
            noCell.innerText = tableBody.rows.length + 1; // 자동으로 번호 증가
            newRow.appendChild(noCell);

            for (let i = 0; i < 9; i++) {
                const newCell = document.createElement('td');
                newCell.contentEditable = true;
                newCell.innerText = '';
                newRow.appendChild(newCell);
            }

            tableBody.appendChild(newRow);
        }

        function enableEditing() {
            if (isWritable) {
                document.querySelectorAll('#material-table tbody tr td').forEach(cell => {
                    cell.contentEditable = true;
                });
            }
        }

        function disableEditing() {
            document.querySelectorAll('#material-table tbody tr td').forEach(cell => {
                cell.contentEditable = false;
            });
        }

        document.querySelector('#material-table tbody').addEventListener('click', function(event) {
            if (isDeleteMode && event.target.closest('tr')) {
                const row = event.target.closest('tr');
                const id = row.cells[0].innerText; // Assume first cell is ID
                if (!selectedIds.includes(id)) {
                    selectedIds.push(id);
                    row.classList.add('selected-row');
                } else {
                    selectedIds = selectedIds.filter(selectedId => selectedId !== id);
                    row.classList.remove('selected-row');
                }
            }
        });

        function getRowData() {
            // Gather data from the selected row
            // This is a placeholder; you need to implement this properly.
            return {}; 
        }

        function resetModes() {
            isEditMode = false;
            isDeleteMode = false;
            isWritable = false;
            selectedIds = [];
            disableEditing();
            document.querySelectorAll('#material-table tbody tr').forEach(row => {
                row.classList.remove('deletable-row');
            });
        }
        
        function saveData() {
            const data = []; // 저장할 데이터 배열

            document.querySelectorAll('#material-table tbody tr').forEach(row => {
                const rowData = {
                    t_corr: row.cells[1].innerText,
                    s_incom: row.cells[2].innerText,
                    s_date: row.cells[3].innerText,
                    t_value: parseInt(row.cells[4].innerText),
                    p_no: row.cells[5].innerText,
                    m_no: parseInt(row.cells[6].innerText),
                    m_dept: row.cells[7].innerText,
                    s_amount: parseInt(row.cells[8].innerText),
                    w_no: parseInt(row.cells[9].innerText)
                };
                data.push(rowData);
            });

            // AJAX 요청
            const params = new URLSearchParams();
            data.forEach((item, index) => {
                for (const key in item) {
                    params.append(`${index}[${key}]`, item[key]);
                }
            });

            fetch("saveData.jsp", {
                method: "POST",
                body: params
            })
            .then(response => {
                if (response.ok) {
                    alert("저장 완료");
                    location.reload(); // 페이지 새로 고침
                } else {
                    throw new Error("저장 실패");
                }
            })
            .catch(error => {
                console.error(error);
            });
        }


    </script>
</body>
</html>
