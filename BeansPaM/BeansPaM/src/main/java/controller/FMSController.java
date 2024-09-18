/**
 * 최초 생성일: 2024-09-11
 * @author 강동준
 * 
 * 수정일: 2024-09-18
 * @author 강동준
 * 
 * 주요 수정 내용: 조건 변경 및 추가
 */

package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import action.Action;
import vo.ActionForward;

public class FMSController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
//		String RequestURI = request.getRequestURI();
//		String contextPath = request.getContextPath();
//		String command = RequestURI.substring(contextPath.length());
		String pathInfo = request.getPathInfo();
		ActionForward forward = null;
//		Action action = null;

		// 마이페이지
		if (pathInfo.equals("/mypage")) {
			forward = new ActionForward("/mypage/mypage.html");
		}
		// 급여
		else if (pathInfo.equals("/paied")) {
			forward = new ActionForward("/mypage/paied/paied.html");
		}
		// 자재 현황
		else if (pathInfo.equals("/stock")) {
			forward = new ActionForward("/stock/stock.html");
		}
		// 사원 검색
		else if (pathInfo.equals("/inform")) {
//			forward = new ActionForward("/emp/emp_inform.html");
			forward = new ActionForward("/emp/ee.html");
		}
		
		// 이하 페이지 에러
		else {
			System.out.println("page error");
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
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