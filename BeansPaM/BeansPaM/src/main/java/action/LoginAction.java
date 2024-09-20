/**
 * 최초 생성일: 2024-09-12
 * @author 임성현
 * 
 * 마지막 수정일: 2024-09-19
 * @author 임성현
 * 
 * 주요 수정 내용: 1. boolean > MemberVO 로 자료형 수정
 *             2. 주석 수정
 *             3. 로그인 성공 후, 세션 대신 쿠키 사용
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
		HttpSession session = request.getSession();

		// 입력한 아이디, 비밀번호를 memberVO 객체에 저장
		memberVO = new MemberVO(); 
		memberVO.setM_id(request.getParameter("inputId").toLowerCase());
		memberVO.setM_pw(request.getParameter("inputPw"));
		
		// SQL 쿼리문을 반환 받을 SVC 객체 생성
		LoginService loginService = new LoginService();
		memberInfo = loginService.loginAction(memberVO);		
		
		// 로그인 성공
		if (!"false".equals(memberInfo.getM_id())) {
			// 쿠키 생성
			Cookie cookie1 = new Cookie("mNo", String.valueOf(memberInfo.getM_no()));
			Cookie cookie2 = new Cookie("mId", memberInfo.getM_id());
			Cookie cookie3 = new Cookie("mName", memberInfo.getM_name());
			// 쿠키 전체 경로에서 사용하도록 지정
			cookie1.setPath("/");
			cookie2.setPath("/");
			cookie3.setPath("/");
			// 쿠키를 응답객체에 추가하기
			response.addCookie(cookie1);
			response.addCookie(cookie2);
			response.addCookie(cookie3);

			forward = new ActionForward();
			forward.setPath("/myPage.l");
			return forward;
		}
		
		// 로그인 실패
		forward = new ActionForward();
		forward.setRedirect(true); 
		forward.setPath("loginMenu.l?failedLogin=failedLogin");                                                     
		return forward;	
	}
	
}