/**
 * 최초 생성일: 2024-09-25
 * @author 임성현
 * 
 * 최종 수정일: 2024-09-29
 * @author 임성현
 * 
 * 주요 수정 내용: 오타 수정
 */
package action;

import javax.servlet.http.*;
import svc.ApprovalService;
import vo.ActionForward;

public class ApprovalContentsDelAction implements Action {
	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ApprovalService approvalService = new ApprovalService();
		ActionForward forward = null;
		
		int d_no = 0;
		
		// 문서 번호 받아오기
		if(request.getParameter("d_no") != null && !request.getParameter("d_no").trim().isEmpty()) {
			d_no = Integer.parseInt(request.getParameter("d_no"));
			approvalService.delPageAction(d_no); // 받은 문서 번호로 게시글 삭제
		}
		
		forward = new ActionForward("approval_main_action");
		
		return forward;
	}
}
