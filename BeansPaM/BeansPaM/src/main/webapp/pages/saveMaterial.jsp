<%@ page import="dao.MaterialDAO" %>
<%@ page import="vo.MaterialVO" %>
<%@ page import="java.sql.Date" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String t_corr = request.getParameter("t_corr");
    String s_incom = request.getParameter("s_incom");
    Date s_date = Date.valueOf(request.getParameter("s_date"));
    int t_value = Integer.parseInt(request.getParameter("t_value"));
    String p_no = request.getParameter("p_no");
    int m_no = Integer.parseInt(request.getParameter("m_no"));
    String m_dept = request.getParameter("m_dept");
    int s_amount = Integer.parseInt(request.getParameter("s_amount"));
    int w_no = Integer.parseInt(request.getParameter("w_no"));

    MaterialVO material = new MaterialVO();
    material.setT_corr(t_corr);
    material.setS_incom(s_incom);
    material.setS_date(s_date);
    material.setT_value(t_value);
    material.setP_no(p_no);
    material.setM_no(m_no);
    material.setM_dept(m_dept);
    material.setS_amount(s_amount);
    material.setW_no(w_no);

    MaterialDAO dao = new MaterialDAO();
    dao.addMaterial(material);
%>
