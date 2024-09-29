/**
 * @author 강동준
 * @since 09.30
 * @see controller.FMSController
 */
package svc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDAO;
import vo.MemberVO;

public class AdminService {
	/**
	 * AJAX를 통해 요청을 받고 사원을 등록해주는 메소드
	 * 
	 * @author 강동준
	 * @since 09.30
	 * @see controller.FMSController
	 */
	public void memberRegistByAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		MemberVO vo = new MemberVO();

		vo.setM_name(request.getParameter("m_name"));
		vo.setM_position(request.getParameter("m_position"));
		vo.setM_phone(request.getParameter("m_phone"));
		vo.setM_dept(request.getParameter("m_dept"));
		vo.setM_email(request.getParameter("m_email"));

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
		response.getWriter().write(new MemberDAO().InsertEmp(vo));
	}
}