/**
 * 최초 생성일: 2024-09-11
 * @author 강동준
 * 
 * 마지막 수정일: 2024-09-12
 * @author 임성현
 * 
 * 주요 수정 내용: 로그인 메뉴에 사용하기 위해 doProcess 메소드 수정
 */

package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import action.*;
import vo.ActionForward;

public class LoginController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ActionForward forward = null;
		Action action = null;

//임성현
//================================================================================================================================
		if (command.equals("/login.l")) {
			action = new LoginAction();

			// 페이지 이동을 위한 임시 코드(3줄) 시험 끝나고 삭제할것.
			forward = new ActionForward();
			forward.setRedirect(true);
			forward.setPath("/BeansPaM/pof/stock/index.html");
			try {
//				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
//================================================================================================================================
		} else {

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