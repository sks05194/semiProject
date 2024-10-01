<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="vo.SalaryVO"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html lang="kr">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>salary</title>
    <link rel="stylesheet" href="/BeansPaM/css/workday.css">
</head>

<body>
    <script src="/BeansPaM/js/menu.js"></script>
    <%
        new DecimalFormat("#,###");
        ArrayList<SalaryVO> salaryList = (ArrayList<SalaryVO>) request.getAttribute("salaryList");
        List<SalaryVO> filteredSalaryList = new ArrayList<>();

        // 날짜 필터링 로직
        String startDateParam = request.getParameter("start-date");
        String endDateParam = request.getParameter("end-date");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null, endDate = null;

        if (startDateParam != null && endDateParam != null) {
            try {
                startDate = sdf.parse(startDateParam);
                endDate = sdf.parse(endDateParam);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (salaryList != null && startDate != null && endDate != null) {
                for (SalaryVO salary : salaryList) {
                    Date salDate = salary.getSal_date();
                    if (!salDate.before(startDate) && !salDate.after(endDate)) {
                        filteredSalaryList.add(salary);
                    }
                }
            }
        } else {
            filteredSalaryList = salaryList;
        }

        // 페이지 당 항목 수 설정
        int pageSize = 8;
        int totalRecords = filteredSalaryList != null ? filteredSalaryList.size() : 0;
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // 현재 페이지 번호 가져오기 (최소값 1, 최대값 totalPages로 제한)
        int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        if (currentPage < 1)
            currentPage = 1;
        if (currentPage > totalPages)
            currentPage = totalPages;

        // 표시할 데이터의 시작 인덱스와 끝 인덱스 계산
        int startRecord = (currentPage - 1) * pageSize;
        int endRecord = Math.min(startRecord + pageSize, totalRecords);
    %>

    <div class="chart"></div>
    <main>
        <h1>급여표</h1>
        <div class="scheduler">
            <form method="get" action="">
                <label for="start-date">기간 선택: </label>
                <input type="date" id="start-date" name="start-date" title="검색할 시작 날짜를 선택하세요." max="<%= new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>">
                <span> ~ </span>
                <input type="date" id="end-date" name="end-date" title="검색할 종료 날짜를 선택하세요." max="<%= new SimpleDateFormat("yyyy-MM-dd").format(new Date()) %>">
                <button type="submit" class="dateaction">조회</button>
            </form>
        </div>
        <table class="mTable">
            <tr class="mbclose">
               
                <th scope="col">지급일</th>
                <th scope="col">급여액</th>
                <th scope="col">공제액</th>
                <th scope="col">총 지급액</th>
            </tr>

            <!-- 급여 데이터 출력 -->
            <% if (filteredSalaryList != null && totalRecords > 0) {
                for (int i = startRecord; i < endRecord; i++) {
                    SalaryVO salary = filteredSalaryList.get(i);
            %>
            <tr>
              
                <td scope="row" data-label="지급일"><%=sdf.format(salary.getSal_date())%></td>
                <!-- 지급일 -->
                <td data-label="급여액"><%=new DecimalFormat("#,###").format(salary.getSal_salary())%>원</td>
                <!-- 급여액 -->
                <td data-label="공제액"><%=new DecimalFormat("#,###").format(salary.getSal_salary() * 0.1)%>원</td>
                <!-- 공제액 (급여의 10%) -->
                <td data-label="총 지급액"><%=new DecimalFormat("#,###").format(salary.getSal_salary() * 0.9)%>원</td>
                <!-- 총 지급액 -->
            </tr>
            <% }
            } else { %>
            <tr>
                <td colspan="5">데이터가 없습니다.</td>
            </tr>
            <% } %>
        </table>

        <!-- 페이지 전환 버튼 -->
        <div class="pagination">
            <!-- 이전 페이지 버튼 -->
            <% if (currentPage > 1) { %>
            <a href="?page=<%=currentPage - 1%>&start-date=<%=startDateParam%>&end-date=<%=endDateParam%>">이전</a>
            <% } %>

            <!-- 페이지 번호 링크 -->
            <% for (int i = 1; i <= totalPages; i++) { %>
            <a href="?page=<%=i%>&start-date=<%=startDateParam%>&end-date=<%=endDateParam%>" <%=i == currentPage ? "class='active'" : ""%>><%=i%></a>
            <% } %>

            <!-- 다음 페이지 버튼 -->
            <% if (currentPage < totalPages) { %>
            <a href="?page=<%=currentPage + 1%>&start-date=<%=startDateParam%>&end-date=<%=endDateParam%>">다음</a>
            <% } %>
        </div>
    </main>

    <script>
        // 오늘 날짜 가져오기
        const today = new Date().toISOString().split('T')[0];

        // start-date와 end-date의 최대값을 오늘 날짜로 설정
        document.getElementById('start-date').setAttribute('max', today);
        document.getElementById('end-date').setAttribute('max', today);

        // start-date 변경 시 end-date의 최소 날짜 설정
        document.getElementById('start-date').addEventListener('change', function () {
            const startDate = this.value;
            document.getElementById('end-date').setAttribute('min', startDate);
        });
    </script>

</body>

</html>
