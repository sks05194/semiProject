/**
 * @since 09.26
 * @author 민기홍
 * @see controller.FMSController
 */
package action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDAO;
import vo.ActionForward;
import vo.MemberVO;

public class ChangePasswordAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		  // 쿠키에서 m_no 값 추출하기
	    String memInfo = null;
	    Cookie[] cookies = request.getCookies();
	    if (cookies != null) {
	        for (Cookie cookie : cookies) {
	            if ("mem_info".equals(cookie.getName())) {
	                memInfo = cookie.getValue();
	                break;
	            }
	        }
	    }

	    int m_no = 0;
	    if (memInfo != null) {
	        String[] infoParts = memInfo.split("\\+");
	        if (infoParts.length > 0) {
	            m_no = Integer.parseInt(infoParts[0]);
	        }
	    }

	    // 사용자로부터 받은 정보 (현재 비밀번호, 새로운 비밀번호)
	    String oldPwd = request.getParameter("oldPwd");
	    String newPwd = request.getParameter("newPwd");

	    if (m_no > 0 && newPwd != null && !newPwd.equals(oldPwd)) {
	        MemberDAO memberDAO = new MemberDAO();
	        // 데이터베이스에서 현재 비밀번호 확인
	        MemberVO member = memberDAO.getMemberByNo(m_no);
	        
	        // 현재 비밀번호가 올바른지 확인
	        if (member != null && member.getM_pw().equals(oldPwd)) {
	            // 비밀번호 변경 로직
	            boolean changePW = memberDAO.updatePasswdInfo(m_no, newPwd);
	            if (changePW) {
	                // 비밀번호 변경 성공, 마이페이지로 이동
	                MemberVO updatedMember = memberDAO.getMemberByNo(m_no);
	                request.setAttribute("member", updatedMember);
	                return new ActionForward("/pages/mypage.jsp");
	            } else {
	                // 비밀번호 변경 실패
	                request.setAttribute("errorMsg", "비밀번호 변경에 실패했습니다.");
	          
	            }
	        } else {
	            // 현재 비밀번호가 틀린 경우
	            request.setAttribute("errorMsg", "현재 비밀번호가 일치하지 않습니다.");
	       
	        }
	    } else {
	        // 입력값 검증 실패 (새 비밀번호가 현재 비밀번호와 동일하거나 비정상적인 값)
	        request.setAttribute("errorMsg", "비밀번호 변경 요청이 유효하지 않습니다.");
	        // 마이페이지 초기 값으로 돌려주기 위해 사용자 정보 다시 로드
	        
	    }


	    // mypage.jsp로 포워딩
	    return new ActionForward("/pages/mypage.jsp");
	}
}
