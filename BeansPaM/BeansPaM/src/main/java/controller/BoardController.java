/**
 * 최초 생성일: 2024-09-18
 * @author 강동준
 * 
 * 수정일: 2024-09-21
 * @author 설보라
 * 주요 수정 내용: 각 페이지 연결 경로 수정
 * 
 * 수정일: 2024-09-22
 * @author 송상훈
 * 주요 수정 내용: QNA 관련 if문 수정
 */

package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.QnaDAO;
import action.Action;
import action.NoticeDetailAction;
import action.NoticeListAction;
import action.NoticeWriteAction;
import vo.ActionForward;

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
				
			} else if (pathInfo[2].matches("\\d+")) {
				// 임시적으로 링크로 이동하게 만듭니다.
				// TODO 추후 pathInfo[2](=글번호)로 글을 불러올 수 있는 메소드를 만들어주세요.
				forward = new ActionForward("/pages/notice_detail.jsp");
			}
			break;
		/**
		 * @author 송상순
		 */
		case "qna":
			if (pathInfo.length == 2) {
				// Q&A 리스트 페이지로 이동
				forward = new ActionForward(false, "/pages/qna.jsp");
			} else if (pathInfo[2].equals("write")) {
				// 글 작성 페이지로 이동
				forward = new ActionForward(false, "/pages/qna_write.jsp");
			} else if (pathInfo[2].equals("submit")) {
				// 글 등록 처리
				String title = request.getParameter("title");
				String writer = request.getParameter("writer");
				String content = request.getParameter("content");
				String category = request.getParameter("category");

				qnaDAO.insertQna(title, writer, content, category);

				// 글 등록 후 Q&A 리스트로 리다이렉트
				forward = new ActionForward(true, "/b/qna");
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