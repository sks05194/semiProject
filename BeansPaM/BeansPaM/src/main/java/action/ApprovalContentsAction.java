/**
 * 최초 생성일: 2024-09-25
 * @author 임성현
 * 
 * 최종 수정일: 2024-09-29
 * @author 임성현
 * 
 * 주요 수정 내용: 조건문 수정
 */
package action;

import java.util.List;
import javax.servlet.http.*;
import svc.ApprovalService;
import vo.*;

public class ApprovalContentsAction implements Action {

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ApprovalService approvalService = new ApprovalService();
		ActionForward forward = null;

		int mNo = 0;
		int d_no = 0;
		int d_m_no = 0;
		String m_name = "";

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

		if (request.getParameter("d_no") != null && !request.getParameter("d_no").trim().isEmpty()) {
			d_no = Integer.parseInt(request.getParameter("d_no"));
		}

		List<DocumentVO> getPageContents = approvalService.getPageContentsAction(d_no); // 선택된 게시글 가져오기
		List<DocumentVO> getName = approvalService.getNameAction(mNo); // 이름, 직급 가져오기

		for (DocumentVO documentVO : getPageContents) {
			d_m_no = documentVO.getD_m_no();
		}

		if (d_m_no > 0) {
			m_name = approvalService.getDNameAction(d_m_no);
			request.setAttribute("m_name", m_name);
		}

		if (!getName.isEmpty()) {
			request.setAttribute("getName", getName);
		}

		request.setAttribute("getPageContents", getPageContents);

		forward = new ActionForward("/fms/approval_contents");
		return forward;
	}
}