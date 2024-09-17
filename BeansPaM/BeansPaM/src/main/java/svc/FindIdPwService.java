/**
 * 최초 생성일: 2024-09-15
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

// 로그인을 위한 SVC 객체
public class FindIdPwService {
	
	public MemberVO findIdPwAction(MemberVO memberVO) throws Exception{		
		MemberDAO memberDAO = new MemberDAO(); 
		memberDAO.setConnection(); 	
		memberVO = memberDAO.findIdPw(memberVO); // M_ID, M_PW 반환
		 
		return memberVO;
	}
	
}
