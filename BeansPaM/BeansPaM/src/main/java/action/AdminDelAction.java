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

public class AdminDelAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		new MemberDAO().deleteEmpForNo(Integer.parseInt(request.getParameter("m_no")));
		return new ActionForward(true, "admin");
	}
}