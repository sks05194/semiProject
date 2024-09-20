/**
 * 최초 생성일: 2024-09-15
 * @author 임성현
 * 
 * 마지막 수정일: 2024-09-19
 * @author 임성현
 * 
 * 주요 수정 내용: 1. 주석 작업
 *             2. 아이디, 비밀번호 찾기 성공 여부 판단하는 조건문 수정
 *             3. 접근성을 위한 .toLowerCase(); 추가
 */
package action;

import javax.servlet.http.*;
import svc.LoginService;
import vo.*;

/* 아이디/비밀번호 찾기를 위한 Action 클래스 */
public class FindIdPwAction implements Action {
	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null; 
		MemberVO memberVO = null; 
		HttpSession session = request.getSession(); 
		
		// 입력한 사원번호나 아이디를 memberVO 객체에 저장
		memberVO = new MemberVO();
		String checkString = request.getParameter("findIdPw").toLowerCase();
	
		// 숫자만 인식하는 정규식, 사원번호 값을 인식하기 위해 사용함
		if (checkString.matches("\\d+")) { 
			int checkInt = Integer.parseInt(checkString);
			memberVO.setM_no(checkInt);
		} else {
			memberVO.setM_id(checkString);	
		}				
		
		// SQL 쿼리문을 반환 받을 SVC 객체 생성
		LoginService loginService = new LoginService(); 
		memberVO = loginService.findIdPwAction(memberVO); 
		
		forward = new ActionForward();
		forward.setRedirect(true);
		
		// 아이디, 비밀번호 찾기 성공 여부 판단하는 조건문
		if(memberVO.getM_pw() != null) { 
			forward.setPath("findIdPwMenu.l?findId=" + memberVO.getM_id() + "&findPw=" + memberVO.getM_pw());
		} else { 
			forward.setPath("findIdPwMenu.l?notExistIdPw=notExistIdPw");
		}		
		return forward;
	}
	
}
