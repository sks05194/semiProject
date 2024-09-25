<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.ResultSet, java.sql.PreparedStatement, java.sql.SQLException" %>
<!DOCTYPE html>
<html lang="kr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>사원 검색</title>
    <script src="/BeansPaM/js/fontawsome.js"></script>
    <script src="/BeansPaM/js/jquery.js"></script>
	<link rel="stylesheet" href="/BeansPaM/css/search_emp.css">
</head>

<body>
    <div class="container">
        <h1>사원 검색</h1>
        <div class="search-container">
            <form action="" method="get">
                <select name="searchField" id="searchField">
                    <option value="m_no">사번</option>
                    <option value="m_name">이름</option>
                    <option value="m_dept">부서명</option>
                    <option value="m_position">직급</option>
                    <option value="m_email">이메일</option>
                    <option value="m_phone">내선번호</option>
                </select>
                <input type="text" name="searchInput" id="searchInput" placeholder="검색어를 입력하세요">
                <button type="submit">검색</button>
            </form>
        </div>
        <table id="employeeTable">
            <thead>
                <tr>
                    <th>사번</th>
                    <th>이름</th>
                    <th>부서명</th>
                    <th>직급</th>
                    <th>이메일</th>
                    <th>내선번호</th>
                </tr>
            </thead>
            <tbody>
                <%
                    String jdbcDriver = "oracle.jdbc.driver.OracleDriver";
                    String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
                    String dbUser = "BeansPaM"; 
                    String dbPassword = "1111"; 

                    Connection conn = null;
                    PreparedStatement pstmt = null;
                    ResultSet rs = null;

                    String searchField = request.getParameter("searchField");
                    String searchInput = request.getParameter("searchInput");
                    String pageParam = request.getParameter("page");

                    int currentPage = (pageParam != null && !pageParam.isEmpty()) ? Integer.parseInt(pageParam) : 1;
                    int pageSize = 10; 
                    int offset = (currentPage - 1) * pageSize; 

                    String sql = "SELECT * FROM (SELECT rownum rn, a.* FROM (SELECT * FROM member";

                    if (searchField != null && searchInput != null && !searchInput.trim().isEmpty()) {
                        sql += " WHERE " + searchField + " LIKE ?";
                    }

                    sql += " ORDER BY m_no) a) WHERE rn BETWEEN ? AND ?";

                    try {
                        Class.forName(jdbcDriver);
                        conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                        pstmt = conn.prepareStatement(sql);
                        int paramIndex = 1;

                        if (searchField != null && searchInput != null && !searchInput.trim().isEmpty()) {
                            pstmt.setString(paramIndex++, "%" + searchInput + "%");
                        }
                        pstmt.setInt(paramIndex++, offset + 1);
                        pstmt.setInt(paramIndex++, offset + pageSize);
                        rs = pstmt.executeQuery();

                        if (!rs.isBeforeFirst()) {
                            out.println("<tr><td colspan='6' class='no-results'>검색 결과가 없습니다.</td></tr>");
                        } else {
                            while (rs.next()) {
                                out.println("<tr>");
                                out.println("<td>" + rs.getString("m_no") + "</td>");
                                out.println("<td>" + rs.getString("m_name") + "</td>");
                                out.println("<td>" + rs.getString("m_dept") + "</td>");
                                out.println("<td>" + rs.getString("m_position") + "</td>");
                                out.println("<td>" + rs.getString("m_email") + "</td>");
                                out.println("<td>" + rs.getString("m_phone") + "</td>");
                                out.println("</tr>");
                            }
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (rs != null) rs.close();
                            if (pstmt != null) pstmt.close();
                        } catch (SQLException se) {
                            se.printStackTrace();
                        }
                    }

                    String countSql = "SELECT COUNT(*) FROM member";
                    if (searchField != null && searchInput != null && !searchInput.trim().isEmpty()) {
                        countSql += " WHERE " + searchField + " LIKE ?";
                    }

                    int totalPages = 0; 

                    try {
                        pstmt = conn.prepareStatement(countSql);
                        if (searchField != null && searchInput != null && !searchInput.trim().isEmpty()) {
                            pstmt.setString(1, "%" + searchInput + "%");
                        }
                        rs = pstmt.executeQuery();
                        if (rs.next()) {
                            int totalRecords = rs.getInt(1);
                            totalPages = (int) Math.ceil((double) totalRecords / pageSize); 
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (rs != null) rs.close();
                            if (pstmt != null) pstmt.close();
                        } catch (SQLException se) {
                            se.printStackTrace();
                        }
                    }
                %>
            </tbody>
        </table>
        <div class="pagination">
            <%
                for (int i = 1; i <= totalPages; i++) {
                    String activeClass = (i == currentPage) ? "active" : "";
                    String searchFieldParam = (searchField != null) ? "&searchField=" + searchField : "";
                    String searchInputParam = (searchInput != null) ? "&searchInput=" + searchInput : "";
                    out.println("<a href=\"?page=" + i + searchFieldParam + searchInputParam + "\" class=\"" + activeClass + "\">" + i + "</a>");
                }
            %>
        </div>
    </div>
    <script src="/BeansPaM/js/menu.js"></script>
</body>

</html>