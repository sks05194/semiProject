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

/* 공지사항 Action 클래스 */
public class NoticeListAction implements Action {
	/**
	 * @author 설보라
	 */
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		ArrayList<Map<String, Object>> noticeList = new ArrayList<>();
		
		String loginName = "";
		String loginId = "";
		
		// 쿠키에서 로그인 정보를 가져오기
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				if("mem_info".equals(name)) {
					loginName = cookie.getValue().split("\\+")[1];
				}
			}
		}

		String keyword = request.getParameter("keyword");
		
		// SQL 쿼리문을 반환 받을 SVC 객체 생성
		NoticeService noticeService = new NoticeService();
		
		// 검색어가 있을 경우 검색, 없을 경우 전체 목록 조회
		if (keyword != null && !keyword.trim().isEmpty()) {
			// 검색어가 있을 때 공지사항 검색
			noticeList = noticeService.searchNotices(keyword);
		} else {
			// 검색어가 없을 때 전체 공지사항 조회
			noticeList = noticeService.noticeListAction();
		}
		
		// 결과를 request 객체에 담음
		request.setAttribute("loginId", loginId);
		request.setAttribute("noticeList", noticeList);
		request.setAttribute("loginName", loginName);
		request.setAttribute("keyword", keyword);
		
		// ActionForward 객체 생성, JSP로 포워딩
		forward.setPath("/pages/notice_list.jsp");
		forward.setRedirect(false); // 포워딩 방식으로 이동 (Redirect는 false)

		return forward;
	}
}