/**
 * 최초 생성일: 2024-09-25
 * @author 임성현
 * 
 * 최종 수정일: 2024-09-29
 * @author 임성현
 * 
 * 주요 수정 내용: 변수 수정
 */
package action;

import javax.servlet.http.*;
import svc.ApprovalService;
import vo.*;

public class ApprovalContentsConfirmAction implements Action {
	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ApprovalService approvalService = new ApprovalService();
		ActionForward forward = new ActionForward();
		
		String m_name = request.getParameter("m_name"); // 비교를 위한 사원 이름 가져오기
		String m_position = request.getParameter("m_position"); // 비교를 위한 사원 직급 가져오기
		int d_no = 0;
		
		if(request.getParameter("d_no") != null && !request.getParameter("d_no").trim().isEmpty()) {
			d_no = Integer.parseInt(request.getParameter("d_no"));
		}
		
		String confirmResponse = approvalService.ConfirmAction(m_name, m_position, d_no); // 결재 승인 헤주는 액션
		
		request.setAttribute("ConfirmResponse", confirmResponse);
		request.setAttribute("ConfirmName", m_name);
		
		forward = new ActionForward("approval_contents_action");
		return forward;
	}
}