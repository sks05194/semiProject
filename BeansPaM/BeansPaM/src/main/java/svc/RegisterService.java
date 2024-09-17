/**
 * 최초 생성일: 2024-09-12
 * @author 임성현
 * 
 * 마지막 수정일: 2024-09-15
 * @author 임성현
 * 
 * 주요 수정 내용: 전반적인 수정 및 주석 작업
 */

package svc;

import dao.MemberDAO;
import vo.MemberVO;

// 사용자 신청을 위한 SVC 객체
public class RegisterService {
	
	public int registerAction(MemberVO memberVO) throws Exception{	
		MemberDAO memberDAO = new MemberDAO();
		memberDAO.setConnection(); 
		int registerCount = memberDAO.memberRegister(memberVO); // 0: 사용자 신청 실패, 1: 사용자 신청 성공, 2: 잘못된 사원 번호 입력, 3: 이미 아이디를 생성함, 4: 아이디 중복
		
		return registerCount; 
	}	
}