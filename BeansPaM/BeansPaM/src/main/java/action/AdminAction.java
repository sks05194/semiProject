/**
 * 문서 생성일: 09.23
 * @author 강동준
 * @see FMSController
 */
package action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import dao.MemberDAO;
import vo.ActionForward;
import vo.MemberVO;

public class AdminAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MemberDAO dao = new MemberDAO();
		ArrayList<MemberVO> list = new ArrayList<MemberVO>();
//		HttpSession session = request.getSession();
		
		list = dao.getAllMembersListVerAdmin();
		
		if (list == null)
			return null;

		request.setAttribute("members", list);
//		session.setAttribute("members", list);
		
		return new ActionForward("/pages/admin.jsp");
	}
}