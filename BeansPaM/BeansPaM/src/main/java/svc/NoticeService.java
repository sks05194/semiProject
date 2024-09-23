/**
 * 최초 생성일: 2024-09-20
 * @author 설보라
 * 
 * 수정일: 2024-09-24
 * @author 설보라
 * 
 * 주요 수정 내용: noticeUpdateAction, noticeDeleteAction 메소드 생성
 */

package svc;

import java.util.ArrayList;
import java.util.Map;

import dao.NoticeDAO;
import vo.NoticeVO;

/* 공지사항 관리를 위한 SVC 클래스 */
public class NoticeService {
	
	/* 공지사항 조회 */
	public ArrayList<Map<String, Object>> noticeListAction() throws Exception {
		NoticeDAO noticeDAO = new NoticeDAO();
		ArrayList<Map<String, Object>> noticeList = new ArrayList<>();
		noticeList = noticeDAO.noticeList();

		return noticeList; 
	}
	
	/* 공지사항 등록 */
	public int noticeWriteAction(NoticeVO noticeVO) throws Exception{	
		NoticeDAO noticeDAO = new NoticeDAO();
		int registerCount = noticeDAO.noticeRegister(noticeVO); 
		
		return registerCount;
	}
	
	/* 공지사항 상세 */
	public NoticeVO noticeDetailAction(NoticeVO noticeVO) throws Exception {
		NoticeDAO noticeDAO = new NoticeDAO();
		
		return noticeDAO.noticeDetail(noticeVO); 
	}
	
	/* 공지사항 수정 */
	public int noticeUpdateAction(NoticeVO noticeVO) throws Exception{	
		NoticeDAO noticeDAO = new NoticeDAO();
		int updateCount = noticeDAO.noticeUpdate(noticeVO); 
		
		return updateCount;
	}
	
	/* 공지사항 수정 */
	public int noticeDeleteAction(NoticeVO noticeVO) {
		NoticeDAO noticeDAO = new NoticeDAO();
		int deleteCount = noticeDAO.noticeDelete(noticeVO); 
		
		return deleteCount;
	}
	
	
	
}