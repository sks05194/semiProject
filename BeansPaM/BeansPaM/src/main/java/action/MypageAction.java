/**
 * 최초 생성일: 2024-09-20
 * @author 민기홍
 * 
 * 마지막 수정일: 2024-09-20
 * @author 민기홍
 * 
 * 주요 수정 내용: 클래스 생성 및 execute 메소드 생성
 */
package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

import vo.ActionForward;
import vo.MemberVO;
import dao.MemberDAO;

public class MypageAction implements Action {
    @Override
    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        MemberVO memberVO = null;

        // 쿠키에서 사번(mNo) 값 가져오기
        Cookie[] cookies = request.getCookies();
        int mNo = 0;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("mem_info".equals(cookie.getName())) {
                    mNo = Integer.parseInt(cookie.getValue().split("\\+")[0]);
                    break;
                }
            }
        }

        // 사번 쿠키가 없을 경우 처리
        if (mNo == 0) {
            System.out.println("로그인 정보가 없습니다.");
            ActionForward forward = new ActionForward();
            forward.setPath("loginMenu.l?failedLogin=failedLogin");
            forward.setRedirect(true);
            return forward;
        }

        // DAO를 통해 데이터베이스에서 사용자 정보 가져오기
        MemberDAO memberDAO = new MemberDAO();
        memberVO = memberDAO.getMemberByNo(mNo); // 사번을 기준으로 조회

        if (memberVO != null) {
            // request에 사용자 정보 저장
            request.setAttribute("member", memberVO);
        }

        // mypage.jsp로 포워딩
        ActionForward forward = new ActionForward();
        forward.setPath("/pages/mypage.jsp");
        return forward;
    }
}