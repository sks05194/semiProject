/**
 * 최초 생성일: 2024-09-12
 * @author 임성현
 * 
 * 수정일: 2024-09-19
 * @author 임성현
 * 
 * 주요 수정 내용: 1. SVC 객체들 하나로 통합
 *             2. 주석 작업 
 */

package svc;

import dao.MemberDAO;
import vo.MemberVO;

/* 로그인을 위한 SVC 클래스 */
public class LoginService {
	
	/* 로그인 메소드 */
	public MemberVO loginAction(MemberVO memberVO) throws Exception {
		MemberDAO memberDAO = new MemberDAO();
		return memberDAO.Login(memberVO); // MEMBER 테이블 관련 데이터 반환
	}
	
	/* 사용자 신청 메소드 */
	public int registerAction(MemberVO memberVO) throws Exception{	
		MemberDAO memberDAO = new MemberDAO();
		memberDAO.setConnection(); 
		int registerCount = memberDAO.memberRegister(memberVO); // 0: 사용자 신청 실패, 1: 사용자 신청 성공, 2: 잘못된 사원 번호 입력, 3: 이미 아이디를 생성함, 4: 아이디 중복	
		return registerCount; 
	}
	
	/* 아이디/비밀번호 찾기 메소드 */
	public MemberVO findIdPwAction(MemberVO memberVO) throws Exception{		
		MemberDAO memberDAO = new MemberDAO(); 
		memberDAO.setConnection(); 	
		memberVO = memberDAO.findIdPw(memberVO); // M_ID, M_PW 반환	 
		return memberVO;
	}
	
}