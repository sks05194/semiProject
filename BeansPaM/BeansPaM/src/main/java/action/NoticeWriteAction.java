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

import svc.NoticeService;
import vo.ActionForward;
import vo.NoticeVO;

/* 공지사항 등록을 위한 Action 클래스 */
public class NoticeWriteAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionForward forward = null;
		NoticeVO noticeVO = null;
		int registerCheck = 0;

		// 입력한 제목, 내용 noticeVO 객체에 저장
		noticeVO = new NoticeVO();
		noticeVO.setN_title(request.getParameter("title"));
		noticeVO.setN_content(request.getParameter("content"));
		noticeVO.setN_important_yn(request.getParameter("is_notice")); // 체크박스 값 저장
		
        // 체크박스 값 처리 ('Y' 또는 'N'으로 설정)
        String isNotice = request.getParameter("is_notice") != null ? "Y" : "N";
        noticeVO.setN_important_yn(isNotice);

		// SQL 쿼리문을 반환 받을 SVC 객체 생성
		NoticeService noticeService = new NoticeService();

		registerCheck = noticeService.noticeWriteAction(noticeVO);

		// 공지사항 등록 성공 여부 판단하는 조건문
		if (registerCheck >= 0) {
			forward = new ActionForward();
			forward.setRedirect(true);
			forward.setPath("/BeansPaM/b/notice");
		}
		return forward;
	}
}