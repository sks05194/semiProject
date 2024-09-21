/**
 * 최초 생성일: 2024-09-12
 * @author 임성현
 * 
 * 수정일: 2024-09-21
 * @author 강동준
 * 
 * 주요 수정 내용: 
 */

package svc;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDAO;
import vo.ActionForward;
import vo.MemberVO;

/* 로그인을 위한 SVC 클래스 */
public class LoginService {
	
	/**
	 * 로그인 메소드 
	 * @author 임성현
	 */
	public MemberVO loginAction(MemberVO memberVO) throws Exception {
		MemberDAO memberDAO = new MemberDAO();
		return memberDAO.Login(memberVO); // MEMBER 테이블 관련 데이터 반환
	}
	
	/**
	 * 사용자 신청 메소드
	 * @author 임성현
	 */
	public int registerAction(MemberVO memberVO) throws Exception{	
		MemberDAO memberDAO = new MemberDAO();
		memberDAO.setConnection(); 
		int registerCount = memberDAO.memberRegister(memberVO); // 0: 사용자 신청 실패, 1: 사용자 신청 성공, 2: 잘못된 사원 번호 입력, 3: 이미 아이디를 생성함, 4: 아이디 중복	
		return registerCount; 
	}
	
	/**
	 * 아이디/비밀번호 찾기 메소드
	 * @author 임성현
	 */
	public MemberVO findIdPwAction(MemberVO memberVO) throws Exception{		
		MemberDAO memberDAO = new MemberDAO(); 
		memberDAO.setConnection(); 	
		memberVO = memberDAO.findIdPw(memberVO); // M_ID, M_PW 반환	 
		return memberVO;
	}
	
	/**
	 * 자동 로그인 메서드
	 * @author 강동준
	 */
	public ActionForward AutoLoginAction(HttpServletRequest request) throws Exception {
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("mem_info")) {
					if (Integer.parseInt(c.getValue().split("\\+")[0]) > 0) {
						return new ActionForward(true, "fms/mypage");
					} else {
						break;
					}
				}
			}
		}

		return new ActionForward("/pages/login_loginMenu.jsp");
	}
	
	/**
	 * 로그아웃 메서드
	 * @author 강동준
	 */
	public void LogoutAction (HttpServletRequest request, HttpServletResponse response) throws Exception {
		Cookie[] cookies = request.getCookies();

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("mem_info")) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				break;
			}
		}
	}
}