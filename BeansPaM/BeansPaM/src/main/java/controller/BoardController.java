/**
 * 최초 생성일: 2024-09-18
 * @author 강동준
 * 
 * 수정일: 2024-09-19
 * @author 강동준
 * 
 * 주요 수정 내용: 페이지 문서 경로 이동에 따른 이동 경로 수정
 */

package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		// 추후 테스트 후 제거
		System.out.println(command);
		System.out.println(String.join("/", pathInfo));

		switch (pathInfo[1]) {
		case "notice":
			if (pathInfo.length == 2) {
				forward = new ActionForward("/pages/notice.html");
			} else if (pathInfo[2].equals("write")) {
				forward = new ActionForward("/pages/notice_write.html");
			} else if (pathInfo[2].matches("\\d+")) {
				// 임시적으로 링크로 이동하게 만듭니다.
				// TODO 추후 pathInfo[2](=글번호)로 글을 불러올 수 있는 메소드를 만들어주세요.
				forward = new ActionForward("/pages/notice_detail.html");
			}
			break;
		case "qna":
			if (pathInfo.length == 2) {
				forward = new ActionForward("/pages/qna.html");
			} else if (pathInfo[2].equals("write")) {
				forward = new ActionForward("/pages/qna_write.html");
			} else if (pathInfo[2].matches("\\d+")) {
				// 임시적으로 링크로 이동하게 만듭니다.
				// TODO 추후 pathInfo[2](=글번호)로 글을 불러올 수 있는 메소드를 만들어주세요.
				forward = new ActionForward("/pages/qna_look.html");
			}
			break;
		default:
			System.out.println("page error");
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