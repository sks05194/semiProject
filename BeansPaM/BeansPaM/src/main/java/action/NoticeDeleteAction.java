/**
 * 최초 생성일: 2024-09-20
 * @author 설보라
 * 
 * 수정일: 2024-09-21
 * @author 설보라
 * 
 * 주요 수정 내용: 파일 생성 
 */
package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import svc.NoticeService;
import vo.ActionForward;
import vo.NoticeVO;

/* 공지사항 등록을 위한 Action 클래스 */
public class NoticeDeleteAction implements Action {
	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null; 
		NoticeVO noticeVO = null;
		int deleteCheck = 0;
		HttpSession session = request.getSession(); 
		
		// 글 번호를 noticeVO 객체에 저장
		noticeVO = new NoticeVO(); 
//		noticeVO.setN_title(request.getParameter("title"));
//		noticeVO.setN_content(request.getParameter("content"));
		noticeVO.setN_delete_yn(request.getParameter("n_delete_yn"));
		noticeVO.setN_no(Integer.parseInt(request.getParameter("n_no")));
		
		System.out.println(request.getParameter("n_delete_yn"));
		
		
		// SQL 쿼리문을 반환 받을 SVC 객체 생성
		NoticeService noticeService = new NoticeService();
		
		deleteCheck = noticeService.noticeDeleteAction(noticeVO); 
				
		// 공지사항 등록 성공 여부 판단하는 조건문
		if(deleteCheck >= 0) {
			forward = new ActionForward();
			forward.setRedirect(true);
			forward.setPath("/BeansPaM/b/notice");
		}	
		return forward;
	}
	
}
