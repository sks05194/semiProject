/**
 * 최초 생성일: 2024-09-20
 * @author 설보라
 * 
 * 수정일: 2024-09-21
 * @author 설보라
 * 
 * 주요 수정 내용: 공지사항 목록 보기 및 상세페이지 연결
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
	
//	public NoticeVO noticeListAction(NoticeVO noticeVO) throws Exception {
//		NoticeDAO noticeDAO = new NoticeDAO();
//		return NoticeDAO.Login(noticeVO); // Notice 테이블 관련 데이터 반환
//	}
	
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
//	public NoticeVO noticeUpdateAction(NoticeVO noticeVO) throws Exception{		
//		NoticeDAO2 noticeDAO = new NoticeDAO2(); 
//		noticeDAO.setConnection(); 	
//		noticeVO = NoticeDAO2.findIdPw(noticeVO); // M_ID, M_PW 반환	 
//		return noticeVO;
//	}
	
	
	
}