/**
 * 최초 생성일: 2024-09-11
 * @author 강동준
 * 
 * 마지막 수정일: 2024-09-19
 * @author 임성현
 * 
 * 주요 수정 내용: 1. 경로명 firstPage.l > index.l 로 수정
 *             2. 정렬을 위해서 로그인 관련 jsp 파일 앞에 login_ 로 변경 및 서블릿 경로 수정
 *             3. import에서 javax.servlet.http.*; 로 단축 수정 
 */

package controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import action.*;
import svc.LoginService;
import vo.ActionForward;

/* 로그인 관련 서블릿 클래스 */
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		ActionForward forward = null;
		Action action = null;

		if (command.equals("/index.l")) {
			forward = new ActionForward("/index.html");
		}

		/**
		 * @author 임성현
		 * 
		 * @author 강동준: 자동 로그인 기능 추가
		 */
		else if (command.equals("/loginMenu.l")) {
			LoginService svc = new LoginService();

			try {
				forward = svc.AutoLoginAction(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		else if (command.equals("/registerMenu.l")) {
			forward = new ActionForward("/pages/login_registerMenu.jsp");
		}

		else if (command.equals("/findIdPwMenu.l")) {
			forward = new ActionForward("/pages/login_findIdPwMenu.jsp");
		}

		else if (command.equals("/myPage.l")) {
			forward = new ActionForward("/pages/mypage.html");
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

		/**
		 * @author 강동준
		 */
		else if (command.equals("/logout.l")) {
			LoginService svc = new LoginService();

			try {
				svc.LogoutAction(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

			forward = new ActionForward(true, "./");
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