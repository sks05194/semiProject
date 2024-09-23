/**
 * 최초 생성일: 2024-09-18
 * @author 강동준
 * 
 * 수정일: 2024-09-24
 * @author 송상훈
 * 주요 수정 내용: QNA 관련 if문 수정
 * 
 * 최종 수정일: 2024-09-24
 * @author 설보라
 * 주요 수정 내용: 각 페이지 연결 경로 수정
 */

package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import action.Action;
import action.NoticeDeleteAction;
import action.NoticeDetailAction;
import action.NoticeListAction;
import action.NoticeUpdateAction;
import action.NoticeWriteAction;
import dao.QnaDAO;
import vo.ActionForward;
import vo.QnaVO;

public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		String[] pathInfo = request.getPathInfo().split("/");
		ActionForward forward = null;
		QnaDAO qnaDAO = new QnaDAO(); // QnaDAO 인스턴스 생성

		Action action = null;

		// 추후 테스트 후 제거
		System.out.println(command);
		System.out.println(String.join("/", pathInfo));

		switch (pathInfo[1]) {
		/**
		 * @author 설보라
		 */
		case "notice":
			if (pathInfo.length == 2) {
//				forward = new ActionForward("/pages/notice_list.jsp");
				action = new NoticeListAction();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (pathInfo[2].equals("write")) {
				forward = new ActionForward("/pages/notice_write.jsp");

			} else if (pathInfo[2].equals("register")) {

				action = new NoticeWriteAction();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (pathInfo[2].equals("detail")) {

				action = new NoticeDetailAction();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} else if (pathInfo[2].equals("update")) {	
				
				action = new NoticeUpdateAction();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} else if (pathInfo[2].equals("delete")) {	
							
				action = new NoticeDeleteAction();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else if (pathInfo[2].matches("\\d+")) {
				// 임시적으로 링크로 이동하게 만듭니다.
				// TODO 추후 pathInfo[2](=글번호)로 글을 불러올 수 있는 메소드를 만들어주세요.
				forward = new ActionForward("/pages/notice_detail.jsp");
			}
			break;

		/**
		 * @author 송상훈
		 */
		case "qna":
			if (pathInfo.length == 2) {
				// Q&A 리스트 페이지로 이동 및 게시글 불러오기
				List<QnaVO> qnaList = qnaDAO.getQnaList(); // 게시글 목록 가져오기
				request.setAttribute("qnaList", qnaList); // request에 게시글 목록 저장
				forward = new ActionForward("/pages/qna.jsp");

			} else if (pathInfo[2].equals("write")) {
				// 글 작성 페이지로 이동
				forward = new ActionForward("/pages/qna_write.jsp");
			} else if (pathInfo[2].equals("submit")) {
				// 글 등록 처리
				String title = request.getParameter("title");
				String writer = request.getParameter("writer");
				String content = request.getParameter("content");

				qnaDAO.insertQna(title, writer, content);

				// 글 등록 후 Q&A 리스트로 리다이렉트
				forward = new ActionForward(true, "/BeansPaM/b/qna");
			} else if (pathInfo[2].equals("search")) {
				// 검색 처리
				String searchType = request.getParameter("searchType");
				String keyword = request.getParameter("keyword");

				// 검색된 게시글 목록을 가져옴
				List<QnaVO> qnaList = qnaDAO.searchQnaList(searchType, keyword);
				request.setAttribute("qnaList", qnaList);

				// 검색 결과를 qna.jsp로 전달
				forward = new ActionForward("/pages/qna.jsp");
			} else if (pathInfo[2].equals("detail.do")) {
				// 게시글 상세보기 처리
				String q_no = request.getParameter("q_no");
				QnaVO qna = qnaDAO.getQnaDetail(Integer.parseInt(q_no)); // q_no로 게시글 상세 정보 조회
				if (qna != null) {
					request.setAttribute("qna", qna); // request에 게시글 정보 저장
					forward = new ActionForward("/pages/qna_detail.jsp"); // 상세보기 페이지로 이동 (forward 방식)
				} else {
					// 게시글을 찾지 못한 경우 에러 처리
					forward = new ActionForward("/pages/error.jsp");
				}
			}
			break;

		default:
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			break;
		}

		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
}