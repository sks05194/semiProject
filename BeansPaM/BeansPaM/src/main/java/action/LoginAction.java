/**
 * 최초 생성일: 2024-09-12
 * @author 임성현
 * 
 * 마지막 수정일: 2024-09-12
 * @author 강동준
 * 
 * 주요 수정 내용: 일부 경로명 수정
 */
package action;

import javax.servlet.http.*;
import svc.LoginService;
import vo.*;

public class LoginAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String check = null; // 아이디, 비밀번호가 DB와 일치하지 않을 때에 사용하기 위한 String 변수 초기화
		ActionForward forward = null; // 리다이렉트를 위한 forward 객체 초기화
		MemberVO memberVO = null; // 입력한 아이디와 비밀번호를 저장할 VO 객체 초기화
		HttpSession session = request.getSession(); // 아이디 값을 저장할 Session 객체 생성

		memberVO = new MemberVO(); // 입력한 아이디와 비밀번호를 저장할 VO 객체 생성
		memberVO.setM_id(request.getParameter("inputId")); // 입력한 아이디 값 memberVO 객체에 저장
		memberVO.setM_pw(request.getParameter("inputPw")); // 입력한 비밀번호 값 memberVO 객체에 저장

		LoginService loginService = new LoginService(); // DB에서 아이디, 비밀번호가 일치하는지를 받아올 SVC 객체 생성
		
		boolean loginCheck = loginService.checkAction(memberVO); // SVC객체에서 boolean 값을 받는다. true면 아이디와 비밀번호가 DB와 같다는 의미이다.
		
		if (!loginCheck) { // boolean 값이 false일때 (아이디와 비밀번호가 DB와 일치하지 않음)
			check = "check"; // 아이디, 비밀번호가 DB와 일치하지 않을 때를 위한 String 변수 초기화
			session.setAttribute("checkLogin", check); // Session에 로그인 메뉴로 보낼 속성 값(check) 설정
			response.sendRedirect("/BeansPaM/login/login_menu.jsp"); // 리다이렉트를 통해 로그인 메뉴로 돌아온다.
			check = null; // check 값 null로 다시 초기화
		} else { // boolean 값이 true일때 (아이디와 비밀번호가 DB와 일치함)
			String check2 = (String) session.getAttribute("userid"); // Session 값 검사하기를 위한 String 변수 선언
			if (check2 != null) { // Session에 userid 값이 있다면
				session.removeAttribute("userid"); // Session에 있는 userid 값을 삭제한다
			}
			session.setAttribute("userid", memberVO.getM_id()); // 사용자 정보를 위한 세션 생성
			forward = new ActionForward(); // 리다이렉트를 통해 서블릿으로 돌아갈 forward 객체 생성
			forward.setRedirect(true); // true면 서블릿에서 리다이렉트를 if문을 통해 허락한다.
			forward.setPath("/container1.fms"); // index_scren 페이지로 이동한다.
		}
		
		return forward;
	}
}