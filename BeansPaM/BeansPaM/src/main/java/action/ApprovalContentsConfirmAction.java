package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.ApprovalService;
import vo.*;

public class ApprovalContentsConfirmAction implements Action {
	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ApprovalService approvalService = new ApprovalService();
		ActionForward forward = new ActionForward();
		
		String m_name = request.getParameter("m_name");
		String m_position = request.getParameter("m_position");
		int d_no = 0;
		
		if(request.getParameter("d_no") != null && !request.getParameter("d_no").trim().isEmpty()) {
			d_no = Integer.parseInt(request.getParameter("d_no"));
		}
		
		String confirmResponse = approvalService.ConfirmAction(m_name, m_position, d_no);
		
		request.setAttribute("ConfirmResponse", confirmResponse);
		request.setAttribute("ConfirmName", m_name);
		
		forward = new ActionForward("approval_contents_action");
		return forward;
	}
}
