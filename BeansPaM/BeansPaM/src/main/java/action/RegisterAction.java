/**
 * 최초 생성일: 2024-09-12
 * @author 임성현
 * 
 * 마지막 수정일: 2024-09-19
 * @author 임성현
 * 
 * 주요 수정 내용: 1. 주석 작업
 *             2. registerCheck 변수 선언
 */
package action;

import javax.servlet.http.*;
import svc.LoginService;
import vo.*;

/* 사용자 신청을 위한 Action 클래스 */
public class RegisterAction implements Action {
	/**
	 * @author 임성현
	 */
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		MemberVO memberVO = null;
		int registerCheck = 0;

		// 사원번호 String 자료형으로 형변환
		String registerNoStr = request.getParameter("registerNo");
		int registerNo = Integer.parseInt(registerNoStr);

		// 입력한 사원번호, 아이디, 비밀번호를 memberVO 객체에 저장
		memberVO = new MemberVO();
		memberVO.setM_no(registerNo);
		memberVO.setM_id(request.getParameter("registerId").toLowerCase());
		memberVO.setM_pw(request.getParameter("registerPw"));

		// SQL 쿼리문을 반환 받을 SVC 객체 생성
		LoginService loginService = new LoginService();
		registerCheck = loginService.registerAction(memberVO);

		// 사용자 신청 성공 여부 판단하는 조건문
		if (registerCheck >= 0) {
			forward = new ActionForward();
			forward.setRedirect(true);
			switch (registerCheck) {
				case 1 : forward.setRedirect(false); forward.setPath("/loginMenu.l"); break; // 1: 사용자 신청 성공    
			    case 2 : forward.setPath("registerMenu.l?invalidNo=invalidNo"); break; // 2: 잘못된 사원 번호 입력 
			    case 3 : forward.setPath("registerMenu.l?idExists=idExists"); break; // 3: 이미 아이디를 생성함 
			    case 4 : forward.setPath("registerMenu.l?duplicateId=duplicateId"); break; // 4: 아이디 중복 
			    default : forward.setPath("registerMenu.l?registerFailure=registerFailure"); break; // 0: 사용자 신청 실패 
			}
		}	
		return forward;
	}
}