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
import svc.RegisterService;
import vo.*;

// 사용자 신청을 위한 Action 객체
public class RegisterAction implements Action {
	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null; 
		MemberVO memberVO = null; 
		HttpSession session = request.getSession(); 
		
		// 사원번호는 Int 자료형이기에 형변환 하는 작업
		String registerNoStr = request.getParameter("registerNo");
		int registerNo = Integer.parseInt(registerNoStr);
		
		// 입력한 정보들을 VO 객체에 저장한다
		memberVO = new MemberVO(); 
		memberVO.setM_no(registerNo);
		memberVO.setM_id(request.getParameter("registerId").toLowerCase()); // 아이디는 접근성을 향상하기 위해 다 소문자로 변환한다.
		memberVO.setM_pw(request.getParameter("registerPw")); 
		
		RegisterService registerService = new RegisterService(); // VO 객체에서 전송할 SVC 객체를 만든다	 	
		int registerCheck = registerService.registerAction(memberVO); // 0: 사용자 신청 실패, 1: 사용자 신청 성공, 2: 잘못된 사원 번호 입력, 3: 이미 아이디를 생성함, 4: 아이디 중복
				
		if(registerCheck >= 0) {
			forward = new ActionForward();
			forward.setPath("/registerMenu.l");
			switch(registerCheck) {
				case 1 : forward.setPath("/loginMenu.l"); break; // 1: 사용자 신청 성공    
			    case 2 : session.setAttribute("invalidNo", "invalidNo"); break; // 2: 잘못된 사원 번호 입력
			    case 3 : session.setAttribute("idExists", "idExists"); break; // 3: 이미 아이디를 생성함
			    case 4 : session.setAttribute("duplicateId", "duplicateId"); break; // 4: 아이디 중복
			    default : session.setAttribute("registerFailure", "registerFailure"); break; // 0: 사용자 신청 실패
			}
		}	
		return forward;
	}
	
}
