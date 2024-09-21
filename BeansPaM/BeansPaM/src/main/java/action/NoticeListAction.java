/**
 * 최초 생성일: 2024-09-21
 * @author 설보라
 * 
 * 수정일: 2024-09-21
 * @author 설보라
 * 
 * 주요 수정 내용: 파일 생성
 */
package action;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.NoticeService;
import vo.ActionForward;
import vo.NoticeVO;

/* 공지사항 Action 클래스 */
public class NoticeListAction implements Action {
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ArrayList<Map<String, Object>> noticeList = new ArrayList<>();
		
		String loginId = "";
		
		Cookie[] cookies = request.getCookies(); // 모든 쿠키 가져오기

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();   // 쿠키 이름 가져오기
                //String value = cookie.getValue(); // 쿠키 값 가져오기
                
                if("mId".equals(name)) {
                	loginId = cookie.getValue();
                }
            }
        }
		
		// SQL 쿼리문을 반환 받을 SVC 객체 생성
		NoticeService noticeService = new NoticeService();
		noticeList = noticeService.noticeListAction();
		// NoticeInfo = loginService.loginAction(NoticeVO);	
		
		// 리스트를 request 객체에 담음
        request.setAttribute("noticeList", noticeList);
        request.setAttribute("loginId", loginId);

        // ActionForward 객체 생성, JSP로 포워딩
        forward = new ActionForward();
        forward.setPath("/pages/notice_list.jsp");
        forward.setRedirect(false); // 포워딩 방식으로 이동 (Redirect는 false)
		
		return forward;	
	}
	
}