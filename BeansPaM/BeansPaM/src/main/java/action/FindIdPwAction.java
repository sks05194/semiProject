/**
 * 최초 생성일: 2024-09-15
 * @author 임성현
 * 
 * 마지막 수정일: 2024-09-15
 * @author 임성현
 * 
 * 주요 수정 내용: 전반적인 수정 및 주석 작업
 */
package action;

import javax.servlet.http.*;
import svc.FindIdPwService;
import svc.LoginService;
import vo.*;

// 로그인을 위한 Action 객체
public class FindIdPwAction implements Action {
	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null; 
		MemberVO memberVO = null; 
		HttpSession session = request.getSession(); 
		
		memberVO = new MemberVO(); // 입력한 사원번호나 아이디를 저장할 VO 객체
		String checkString = request.getParameter("findIdPw"); // 입력한 사원번호나 아이디값을 가져온다.
	
		// 숫자만 인식하는 정규식임. 사원번호 값을 인식하기 위해 사용한다.
		if (checkString.matches("\\d+")) { 
			int checkInt = Integer.parseInt(checkString);
			memberVO.setM_no(checkInt);
		} else {
			memberVO.setM_id(checkString); // 아이디는 접근성을 향상하기 위해 다 소문자로 변환한다.		
		}				
		
		FindIdPwService findIdPwService = new FindIdPwService(); // VO 객체에서 전송할 SVC 객체를 만든다	
		memberVO = findIdPwService.findIdPwAction(memberVO); // M_ID, M_PW 반환
		
		if(memberVO.getM_pw() != null) { // 정상적으로 아이디, 비밀번호를 반환 받았다면(비밀번호가 null이 아니면)
			session.setAttribute("findId", memberVO.getM_id()); 
			session.setAttribute("findPw", memberVO.getM_pw()); 
			
			forward = new ActionForward();
			forward.setPath("/findIdPwMenu.l"); 
		} else { // 아이디, 비밀번호를 반환 받지 못했다면 (비밀번호가 null이면)
			session.setAttribute("notExistIdPw", "notExistIdPw"); 
			
			forward = new ActionForward();  
			forward.setPath("/findIdPwMenu.l"); 
		}		
		return forward;
	}
	
}
