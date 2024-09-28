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

import java.util.List;

import dao.DocumentDAO;
import vo.DocumentVO;

public class ApprovalService {
	DocumentDAO documentDAO = new DocumentDAO();

	public int attendanceAction(DocumentVO documentVO) {
		return documentDAO.approval(documentVO);
	}
	
	public int tripAction(DocumentVO documentVO) {
		return documentDAO.approval(documentVO);
	}
	
	public int estimateAction(DocumentVO documentVO) {
		return documentDAO.approval(documentVO);
	}
	
	public List<DocumentVO> getPageListAction(int pageNum, int amount) {
		return documentDAO.getPageList(pageNum, amount);
	}
	
	public int getTotalPageAction() {
		return documentDAO.getTotalPage();
	}
	
	public List<DocumentVO> getPageContentsAction(int d_no) {
		return documentDAO.getPageContents(d_no);
	}
	
	public int delPageAction(int d_no) {
		return documentDAO.delPage(d_no);
	}
	
	public List<DocumentVO> getNameAction(int mNo) {
		return documentDAO.getName(mNo);
	}
	
	public String ConfirmAction(String m_name, String m_position, int d_no) {
		return documentDAO.Confirm(m_name, m_position, d_no);
	}
	
	public String getDNameAction(int d_m_no) {
		return documentDAO.getDName(d_m_no);
	}
}
