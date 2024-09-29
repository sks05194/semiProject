/**
 * 최초 생성일: 2024-09-25
 * @author 임성현
 * 
 * 최종 수정일: 2024-09-29
 * @author 임성현
 * 
 * 주요 수정 내용: 검색 처리 관련 메소드 추가
 */
package svc;

import java.util.List;
import dao.DocumentDAO;
import vo.DocumentVO;

public class ApprovalService {
	DocumentDAO documentDAO = new DocumentDAO();

	// 근태 폼 액션
	/** @see action.ApprovalWriteAction */
	public int attendanceAction(DocumentVO documentVO) {
		return documentDAO.approval(documentVO);
	}
	
	// 출장 폼 액션
	/** @see action.ApprovalWriteAction */
	public int tripAction(DocumentVO documentVO) {
		return documentDAO.approval(documentVO);
	}
	
	// 견적 폼 액션
	/** @see action.ApprovalWriteAction */
	public int estimateAction(DocumentVO documentVO) {
		return documentDAO.approval(documentVO);
	}
	
	// 페이지 가져오기 액션
	/** @see action.ApprovalMainAction */
	public List<DocumentVO> getPageListAction(int pageNum, int amount, int mNo) {
		return documentDAO.getPageList(pageNum, amount, mNo);
	}
	
	// 총 페이지 수 가져오기 액션
	/** @see action.ApprovalMainAction */
	public int getTotalPageAction(int mNo) {
		return documentDAO.getTotalPage(mNo);
	}
	
	// 결재글 가져오기 액션
	/** @see action.ApprovalContentsAction */
	public List<DocumentVO> getPageContentsAction(int d_no) {
		return documentDAO.getPageContents(d_no);
	}
	
	// 이름, 직급 가져오는 액션
	/** @see action.ApprovalContentsAction */
	public List<DocumentVO> getNameAction(int mNo) {
		return documentDAO.getName(mNo);
	}
	
	// 사원 이름 가져오기 액션
	/** @see action.ApprovalContentsAction */
	public String getDNameAction(int d_m_no) {
		return documentDAO.getDName(d_m_no);
	}
	
	// 결재글 삭제 액션
	/** @see action.ApprovalContentsDelAction */
	public int delPageAction(int d_no) {
		return documentDAO.delPage(d_no);
	}
	
	// 결재 승인 액션
	/** @see action.ApprovalContentsConfirmAction */
	public String ConfirmAction(String m_name, String m_position, int d_no) {
		return documentDAO.Confirm(m_name, m_position, d_no);
	}
	
	// 검색 후 페이지 가져오는 액션
	/** @see action.ApprovalSearchAction */
	public int getfCountAction(String sel, String sfind, int nfind, int mNo) {
		return documentDAO.getfCount(sel, sfind, nfind, mNo);
	}
	
	// 검색 후 총 페이지 수 가져오는 액션
	/** @see action.ApprovalSearchAction */
	public List<DocumentVO> getfListAction(int mNo, int pageNum, int amount, String sel, String sfind, int nfind) {
		return documentDAO.getfList(mNo, pageNum, amount, sel, sfind, nfind);
	}
}
