/**
 * 최초 생성일: 2024-09-28
 * @author 임성현
 * 
 * 최종 수정일: 2024-09-29
 * @author 임성현
 * 
 * 주요 수정 내용: 검색 액션 전체 수정
 */
package action;

import java.util.List;
import javax.servlet.http.*;
import svc.ApprovalService;
import vo.*;

public class ApprovalSearchAction implements Action {

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ApprovalService approvalService = new ApprovalService();
		ActionForward forward = null;

		int pageNum = 1;
		int amount = 10;
		int mNo = 0;
		int nfind = 0;
		String sfind = null;
		String find = null;

		Cookie[] cookies = request.getCookies();

		// 쿠키에서 사원번호 mNO 가져오기
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("mem_info".equals(cookie.getName())) {
					mNo = Integer.parseInt(cookie.getValue().split("\\+")[0]);
					break;
				}
			}
		}

		// mNo가 안 바뀌고 0일시 실행
		if (mNo == 0) {
			forward = new ActionForward(true, "loginMenu.l?failedLogin=failedLogin");
			return forward;
		}

		// 페이지 번호 클릭
		if (request.getParameter("pageNum") != null && request.getParameter("amount") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
			amount = Integer.parseInt(request.getParameter("amount"));
		}

		// select 태그의 값을 가져오기 (검색종류)
		String sel = request.getParameter("sel");

		// input 태그의 값을 가져오기 (검색값)
		find = request.getParameter("find");

		// 검색 후에도 페이징 유지하기 위해 사용하는 코드
		if (find == null) {
			sfind = request.getParameter("sfind");
			if (request.getParameter("nfind") != null) {
				nfind = Integer.parseInt(request.getParameter("nfind"));
			}
		}

		if (find != null && find.equals("")) {
			forward = new ActionForward("approval_main_action");
			return forward;
		}

		if (sel != null && sel.equals("D_NO")) {
			if (find != null && find.matches("\\d+")) {
				nfind = Integer.parseInt(find);
			}

		} else {
			if (find != null && !find.equals("")) {
				sfind = find;
			}
		}

		List<DocumentVO> getPageList = approvalService.getfListAction(mNo, pageNum, amount, sel, sfind, nfind); // 검색
																												// 게시글
																												// 가져오기
		int getTotalPage = approvalService.getfCountAction(sel, sfind, nfind, mNo); // 검색 게시글의 총 갯수 가져오기

		PageVO pageVO = new PageVO(pageNum, amount, getTotalPage);

		request.setAttribute("pageVO", pageVO);
		request.setAttribute("getPageList", getPageList);
		request.setAttribute("searchCheck", "searchCheck");
		request.setAttribute("sel", sel);
		request.setAttribute("sfind", sfind);
		request.setAttribute("nfind", nfind);
		request.setAttribute("find", find);

		forward = new ActionForward("approval_main");
		return forward;
	}
}
