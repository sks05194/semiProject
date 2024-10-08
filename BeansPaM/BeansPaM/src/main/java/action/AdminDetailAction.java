/**
 * 문서 생성일: 09.23
 * @author 강동준
 * @see controller.FMSController
 */

package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDAO;
import vo.ActionForward;

public class AdminDetailAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("member_detail", new MemberDAO().getMemberByNo(Integer.parseInt(request.getParameter("m_no"))));
		return new ActionForward("/pages/admin_detail.jsp");
	}
}