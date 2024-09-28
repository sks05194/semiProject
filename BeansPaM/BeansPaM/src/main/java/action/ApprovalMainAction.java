package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.ApprovalService;
import vo.*;

public class ApprovalMainAction implements Action {

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ApprovalService approvalService = new ApprovalService();
		ActionForward forward = null;

		int pageNum = 1;
		int amount = 10;

		// 페이지 번호 클릭
		if (request.getParameter("pageNum") != null && request.getParameter("amount") != null) {
			pageNum = Integer.parseInt(request.getParameter("pageNum"));
			amount = Integer.parseInt(request.getParameter("amount"));
		}

		List<DocumentVO> getPageList = approvalService.getPageListAction(pageNum, amount);
		int getTotalPage = approvalService.getTotalPageAction();

		PageVO pageVO = new PageVO(pageNum, amount, getTotalPage);

		request.setAttribute("pageVO", pageVO);
		request.setAttribute("getPageList", getPageList);

		forward = new ActionForward("/fms/approval_main");
		return forward;
	}
}
