/**
 * 최초 생성일: 2024-09-25
 * @author 임성현
 * 
 * 최종 수정일: 2024-09-25
 * @author 임성현
 * 
 * 주요 수정 내용: 결재 상신 서비스 클래스 생성 및 입력
 */
package svc;

import dao.ApprovalDAO;
import vo.DocumentVO;

public class ApprovalService {
	ApprovalDAO approvalDAO = new ApprovalDAO();

	public int attendanceAction(DocumentVO documentVO) {
		return approvalDAO.approval(documentVO);
	}
	
	public int tripAction(DocumentVO documentVO) {
		return approvalDAO.approval(documentVO);
	}
	
	public int estimateAction(DocumentVO documentVO) {
		return approvalDAO.approval(documentVO);
	}
	
}
