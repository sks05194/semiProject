/**
 * 문서 생성일 09.25
 * @author 강동준
 * @see FMSController
 */
package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDAO;
import vo.ActionForward;
import vo.MemberVO;

public class MemberRegistAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MemberVO vo = new MemberVO();

		vo.setM_name(request.getParameter("m_name"));
		vo.setM_position(request.getParameter("m_position"));
		vo.setM_phone(request.getParameter("m_phone"));
		vo.setM_dept(request.getParameter("m_dept"));
		vo.setM_email(request.getParameter("m_email"));

		if (new MemberDAO().InsertEmp(vo)) {
			System.out.println("MemberRegistAction: 정상적으로 삽입되었습니다.");
		} else {
			System.out.println("MemberRegistAction: 데이터 삽입에 실패했습니다.");
		}

		return new ActionForward(true, "/BeansPaM/fms/admin");
	}
}