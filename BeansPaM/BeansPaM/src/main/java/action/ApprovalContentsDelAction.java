package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.ApprovalService;
import vo.ActionForward;

public class ApprovalContentsDelAction implements Action {
	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ApprovalService approvalService = new ApprovalService();
		ActionForward forward = null;
		
		int d_no = 0;
		
		if(request.getParameter("d_no") != null && !request.getParameter("d_no").trim().isEmpty()) {
			d_no = Integer.parseInt(request.getParameter("d_no"));
			approvalService.delPageAction(d_no);
		}
		
		forward = new ActionForward("approval_main_action");
		
		return forward;
	}
}
