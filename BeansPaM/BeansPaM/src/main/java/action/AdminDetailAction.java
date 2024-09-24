/**
 * 문서 생성일: 09.23
 * @author 강동준
 * @see FMSController
 */

package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDAO;
import vo.ActionForward;
import vo.MemberVO;

public class AdminDetailAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("member_detail", new MemberDAO().getAllDataForNo(Integer.parseInt(request.getParameter("m_no"))));
		return new ActionForward("/pages/admin_detail.jsp");
	}
}