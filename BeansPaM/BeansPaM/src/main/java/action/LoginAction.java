/**
 * 최초 생성일: 2024-09-12
 * @author 임성현
 * 
 * 마지막 수정일: 2024-09-21
 * @author 강동준
 * 
 * 주요 수정 내용: 쿠키 유효기간 변경
 */
package action;

import javax.servlet.http.*;
import svc.LoginService;
import vo.*;

/* 로그인을 위한 Action 클래스 */
public class LoginAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		MemberVO memberVO = null;
		MemberVO memberInfo = null;

		// 입력한 아이디, 비밀번호를 memberVO 객체에 저장
		memberVO = new MemberVO();
		memberVO.setM_id(request.getParameter("inputId").toLowerCase());
		memberVO.setM_pw(request.getParameter("inputPw"));

		// SQL 쿼리문을 반환 받을 SVC 객체 생성
		LoginService loginService = new LoginService();
		memberInfo = loginService.loginAction(memberVO);

		// 로그인 성공
		if (!memberInfo.getM_id().equals("false")) {
			Cookie cookie = new Cookie("mem_info", memberInfo.getM_no() + "+" + memberInfo.getM_name());
			cookie.setPath("/BeansPaM");
			cookie.setMaxAge(60 * 60 * 9); // 9시간
			response.addCookie(cookie);

			forward = new ActionForward(true, request.getContextPath());
			return forward;
		}

		// 로그인 실패
		forward = new ActionForward();
		forward.setRedirect(true);
		forward.setPath("loginMenu.l?failedLogin=failedLogin");
		return forward;
	}
}