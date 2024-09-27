<%@ page import="java.util.List" %>
<%@ page import="vo.WarehouseVO" %>
<%@ page import="dao.WarehouseDAO" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>창고 관리 현황</title>
    <link rel="stylesheet" href="/BeansPaM/css/warehouse.css">
<!--     <script src="/BeansPaM/js/menu.js"></script> -->
</head>
<body>
    <main class="content">
        <section class="material-section">
            <div class="header-container">
                <h2>창고 관리 현황</h2>
            </div>

            <table class="status-table">
                <thead>
                    <tr>
                        <th>창고 No</th>
                        <th>창고 위치</th>
                        <th>기준 온도(˚C)</th>
                        <th>현재 온도</th>
                        <th>기준 습도(%)</th>
                        <th>현재 습도</th>
                        <th>문제 여부</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        WarehouseDAO warehouseDAO = new WarehouseDAO();
                        List<WarehouseVO> warehouses = warehouseDAO.getAllWarehouses();
                        
                        if (warehouses != null && warehouses.size() > 0) {
                            for (WarehouseVO warehouse : warehouses) {
                                String issue = warehouse.getW_issue(); // 문제 여부 확인
                    %>
                    <tr>
                        <td><%= warehouse.getW_no() %></td>
                        <td><%= warehouse.getW_loc() %></td>
                        <td>23~27°C</td> <!-- 기준 온도 -->
                        <td><%= warehouse.getW_temp() %>°C</td> <!-- 실제 온도 -->
                        <td>62~68%</td> <!-- 기준 습도 -->
                        <td><%= warehouse.getW_humi() %>%</td> <!-- 실제 습도 -->
                        <td>
                            <!-- 문제 여부에 따른 경광등 아이콘 출력 -->
                            <span class="blinking-light <%= "Y".equals(issue) ? "red-light" : "green-light" %>"></span>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="7">데이터가 없습니다.</td>
                    </tr>
                    <%
                        }
                    %>
                </tbody>
            </table>
        </section>
    </main>
	<script src="/BeansPaM/js/menu.js"></script>
</body>
</html>
