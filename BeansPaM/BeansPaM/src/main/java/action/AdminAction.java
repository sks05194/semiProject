/**
 * 문서 생성일: 09.23
 * @author 강동준
 * @see FMSController
 * 
 * 변경일: 09.24
 * 변경 이유: sql문 페이징
 */
package action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDAO;
import vo.ActionForward;
import vo.MemberVO;

public class AdminAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MemberDAO dao = new MemberDAO();
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
		int record = 10;

		String sql = "SELECT * FROM (SELECT a.*, ROWNUM rnum FROM  (SELECT * FROM member";
		String countSql = "SELECT count(*) c FROM member";
		
		String target = request.getParameter("target");
		String keyword = request.getParameter("keyword");
		

		if (keyword != null && !keyword.equals("")) {
			if (target.equals("m_no") && !isInteger(keyword)) {
				return new ActionForward("/pages/admin.jsp");
			}
			
			String whereSql = " WHERE " + target + " LIKE '%" + keyword + "%'";
			sql += whereSql;
			countSql += whereSql;
		}
		
		int pageCount = dao.getPageCount(countSql);
		
		if (pageCount < 0)
			return new ActionForward("/pages/admin.jsp");

		int pageNum = request.getParameter("p") != null ? Integer.parseInt(request.getParameter("p")) : 1;
		sql += " ORDER BY m_no desc) a WHERE ROWNUM <= " + (pageNum * record);
		sql += ") WHERE rnum > " + ((pageNum - 1) * record);

		list = dao.getAllMembersListVerAdmin(sql);

		request.setAttribute("members", list);
		request.setAttribute("memListCount", pageCount);

		return new ActionForward("/pages/admin.jsp");
	}
	
	/**
	 * 문자열 정수 변환 가능 여부
	 * @author 강동준
	 * @see AdminAction.execute
	 */
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}