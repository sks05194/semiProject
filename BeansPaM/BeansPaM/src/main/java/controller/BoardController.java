/**
 * 최초 생성일: 2024-09-18
 * @author 강동준
 * 
 * 수정일: 2024-09-18
 * @author 강동준
 * 
 * 주요 수정 내용: 문서 생성
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
		ActionForward forward = null;
//		Action action = null;

		// 공지사항
		if (command.equals("/notice.b")) {
			forward = new ActionForward();
			forward.setPath("/notice/notice.html");
		}
//		else if (command.equals("/notice_detail.b")) {
//			// Do Something...
//		}
		
		// Q&A
		else if (command.equals("/qna.b")) {
			forward = new ActionForward();
			forward.setPath("/qna/qna.html");
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
		doProcess(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

}
