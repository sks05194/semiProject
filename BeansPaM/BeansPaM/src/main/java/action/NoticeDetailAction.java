/**
 * 최초 생성일: 2024-09-20
 * @author 설보라
 * 
 * 수정일: 2024-09-23
 * @author 설보라
 * 
 * 주요 수정 내용: 캐시 이름 정상화 및 변수 이름 변경
 */
package action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.NoticeService;
import vo.ActionForward;
import vo.NoticeVO;

/* 공지사항 상세페이지 Action 클래스 */
public class NoticeDetailAction implements Action{
	/**
	 * @author 설보라
	 */
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		NoticeVO noticeVO = null;
		NoticeVO noticeInfo = null;
		HttpSession session = request.getSession();
		
		String loginName = "";
		
		Cookie[] cookies = request.getCookies(); // 모든 쿠키 가져오기

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();   // 쿠키 이름 가져오기
                //String value = cookie.getValue(); // 쿠키 값 가져오기
                
                if("mem_info".equals(name)) {
                	loginName = cookie.getValue().split("\\+")[1];
                }
            }
        }

		// 입력한 제목, 내용을  NoticeVO 객체에 저장
		noticeVO = new NoticeVO(); 
		noticeVO.setN_no(Integer.parseInt(request.getParameter("n_no")));
		
		// SQL 쿼리문을 반환 받을 SVC 객체 생성
		NoticeService noticeService = new NoticeService();
		noticeInfo = noticeService.noticeDetailAction(noticeVO);
		
		// 리스트를 request 객체에 담음
        request.setAttribute("title", noticeInfo.getN_title());
        request.setAttribute("content", noticeInfo.getN_content());
        request.setAttribute("n_no", noticeInfo.getN_no());
        request.setAttribute("loginName", loginName);
        
//        System.out.println("loginId>>" + m_name);

        // ActionForward 객체 생성, JSP로 포워딩
        forward = new ActionForward();
        forward.setPath("/pages/notice_detail.jsp");
        forward.setRedirect(false); // 포워딩 방식으로 이동 (Redirect는 false)
		
		return forward;	
	}
	
}