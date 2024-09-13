/**
 * 최초 생성일: 2024-09-12
 * @author 임성현
 * 
 * 마지막 수정일: 2024-09-12
 * @author 임성현
 * 
 * 주요 수정 내용: 로그인 메뉴 SVC 작업
 */

package svc;

import dao.MemberDAO;
import vo.MemberVO;

public class LoginService {
	
	public boolean checkAction(MemberVO memberVO) throws Exception{
		boolean check = false; // 반환해줄 boolean 변수 생성
		MemberDAO memberDAO = new MemberDAO(); // MemberDAO 객체 생성
		memberDAO.setConnection(); // MemberDAO에 연결 객체 생성
		int loginCheck = memberDAO.checkingLogin(memberVO); // MemberDAO에서 int 값을 가져온다.
		                                                    // 아이디,비밀번호가 DB와 일치하면 1의 값을 가져오고 아니면 0의 값을 가져온다.
		if (loginCheck > 0) { // 아이디, 비밀번호가 DB와 일치하면 boolean값을 true로 반환해서
			check = true;      // LoginAction에 돌려준다.
		} else {
			check = false;
		}
		
		return check;
	}
}
