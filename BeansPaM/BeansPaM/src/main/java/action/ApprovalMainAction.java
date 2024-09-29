/**
 * 최초 생성일: 2024-09-25
 * @author 임성현
 * 
 * 최종 수정일: 2024-09-29
 * @author 임성현
 * 
 * 주요 수정 내용: 경로 수정
 */
package action;

import java.util.List;
import javax.servlet.http.*;
import svc.ApprovalService;
import vo.*;

public class ApprovalMainAction implements Action {

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ApprovalService approvalService = new ApprovalService();
		ActionForward forward = null;

		int pageNum = 1;
		int amount = 10;
		int mNo = 0;

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

		List<DocumentVO> getPageList = approvalService.getPageListAction(pageNum, amount, mNo); // 게시글 가져오기
		int getTotalPage = approvalService.getTotalPageAction(mNo); // 게시글 수 가져오기

		PageVO pageVO = new PageVO(pageNum, amount, getTotalPage); // 페이징 처리 계산 VO 객체

		request.setAttribute("pageVO", pageVO);
		request.setAttribute("getPageList", getPageList);

		forward = new ActionForward("/fms/approval_main");
		return forward;
	}
}
