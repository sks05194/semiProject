<%@ page import="java.util.List" %>
<%@ page import="dao.MaterialDAO" %>
<%@ page import="vo.MaterialVO" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String tCorrParam = request.getParameter("t_corrs");

    if (tCorrParam != null) {
        String[] tCorrs = tCorrParam.replace("[", "").replace("]", "").split(",");
        
        MaterialDAO dao = new MaterialDAO();
        dao.deleteMaterials(tCorrs);
        
        response.setStatus(HttpServletResponse.SC_OK); // 성공 시 상태 코드
    } else {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No t_corrs provided"); // 오류 처리
    }
%>
