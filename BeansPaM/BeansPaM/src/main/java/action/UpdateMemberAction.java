package action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDAO;
import vo.ActionForward;
import vo.MemberVO;

public class UpdateMemberAction implements Action {

    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 쿠키에서 m_no 값 추출하기
    	System.out.println("UpdateMemberAction 실행");
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
        System.out.println("UpdateMemberAction m_no 확인:"+m_no);
        // 사용자로부터 받은 업데이트된 정보 (이메일과 전화번호)
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        System.out.println("email:"+email);
        System.out.println("phone:"+phone);
        // m_no가 유효하고 이메일과 전화번호가 존재할 때 처리
        if (m_no > 0 && email != null && phone != null) {
            MemberDAO memberDAO = new MemberDAO();
            System.out.println("if문 실행");
            // 회원 정보를 업데이트하는 메서드 호출
            boolean isUpdated = memberDAO.updateMemberInfo(m_no, email, phone);

            if (isUpdated) {
                // 업데이트된 정보를 다시 가져와서 화면에 반영
                MemberVO updatedMember = memberDAO.getMemberByNo(m_no);
                request.setAttribute("member", updatedMember);
            }
        }

        // mypage.jsp로 포워딩
        return new ActionForward("/pages/mypage.jsp");
    }
}
