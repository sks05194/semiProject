/**
 * 최초 생성일: 2024-09-11
 * @author 강동준
 * 
 * 수정일: 2024-09-15
 * @author 임성현
 * 
 * 주요 수정 내용: 전반적인 수정 및 주석 작업
 * 
 * 마지막 수정일: 2024-09-17
 * @author 강동준
 * 
 * 주요 수정 내용: 임시 추가 서블릿 if문과 일부 쓸모없는 조건 제거
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
		System.out.println(command);

		ActionForward forward = null;
		Action action = null;

		if (command.equals("/firstPage.l")) {
			forward = new ActionForward();
			forward.setPath("/index.html");
		}

		else if (command.equals("/loginMenu.l")) {
			forward = new ActionForward();
			forward.setPath("/login/loginMenu.jsp");
		}

		else if (command.equals("/registerMenu.l")) {
			forward = new ActionForward();
			forward.setPath("/login/registerMenu.jsp");
		}

		else if (command.equals("/findIdPwMenu.l")) {
			forward = new ActionForward();
			forward.setPath("/login/findIdPwMenu.jsp");
		}

		else if (command.equals("/afterLoginScreen.l")) {
			forward = new ActionForward();
			forward.setPath("/afterLoginPage.html");
		}

		else if (command.equals("/login.l")) {
			action = new LoginAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if (command.equals("/register.l")) {
			action = new RegisterAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if (command.equals("/findIdPw.l")) {
			action = new FindIdPwAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}
}