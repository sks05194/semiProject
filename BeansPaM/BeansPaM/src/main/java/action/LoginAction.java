/**
 * 최초 생성일: 2024-09-12
 * @author 임성현
 * 
 * 마지막 수정일: 2024-09-15
 * @author 임성현
 * 
 * 주요 수정 내용: 전반적인 수정 및 주석 작업
 */
package action;

import javax.servlet.http.*;
import svc.LoginService;
import vo.*;

// 로그인을 위한 Action 객체
public class LoginAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		MemberVO memberVO = null;
		HttpSession session = request.getSession();

		memberVO = new MemberVO(); // 입력한 아이디와 비밀번호를 VO 객체에 저장한다
		memberVO.setM_id(request.getParameter("inputId").toLowerCase()); // 아이디는 접근성을 향상하기 위해 다 소문자로 변환한다.
		memberVO.setM_pw(request.getParameter("inputPw"));

		LoginService loginService = new LoginService(); // VO 객체에서 전송할 SVC 객체를 만든다	

		boolean loginCheck = loginService.loginAction(memberVO); // 0: 로그인 실패, 1이상: 로그인 성공 및 사원 번호 반환
		
		if(!loginCheck) { // 0: 로그인 실패
			// 로그인 실패를 출력하기 위해서 세션에 임의의 값을 저장한다
			session.setAttribute("failedLogin", "failedLogin"); 
			
			forward = new ActionForward();
			forward.setPath("/loginMenu.l"); 
		} else { // 1 이상: 로그인 성공 및 사원 번호 반환
			// 세션에 값이 저장되어있을 경우를 대비한 세션 초기화
			session.removeAttribute("mId"); 
			session.removeAttribute("mNo");
			// 세션에 사원번호와 아이디의 값을 저장한다
			session.setAttribute("mId", memberVO.getM_id());
			session.setAttribute("mNo", loginCheck);
			
			forward = new ActionForward();  
			forward.setPath("/afterLoginScreen.l"); 
		}

		return forward;
	}
}