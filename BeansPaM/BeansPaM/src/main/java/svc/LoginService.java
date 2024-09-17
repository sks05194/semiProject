/**
 * 최초 생성일: 2024-09-12
 * @author 임성현
 * 
 * 수정일: 2024-09-15
 * @author 임성현
 * 
 * 주요 수정 내용: 전반적인 수정 및 주석 작업
 * 
 * ps. 코드 최적화 했습니다.
 */

package svc;

import dao.MemberDAO;
import vo.MemberVO;

// 로그인을 위한 SVC 객체
public class LoginService {
	public boolean loginAction(MemberVO memberVO) throws Exception {
		MemberDAO memberDAO = new MemberDAO();
		return memberDAO.Login(memberVO);
	}
}