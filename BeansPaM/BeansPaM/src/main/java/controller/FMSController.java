/**
 * 최초 생성일: 2024-09-11
 * @author 강동준
 * 
 * 기여자
 * @author 민기홍
 * 
 * 최종 수정일: 2024-09-24
 * @author 한지수
 * 
 * 주요 수정 내용: /inform 경로 수정
 */
package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import action.Action;
import action.AdminAction;
import action.AdminDelAction;
import action.AdminDetailAction;
import action.MemberRegistAction;
import action.MypageAction;
import action.SalaryAction;
import action.WorkdayAction;
import vo.ActionForward;

public class FMSController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String pathInfo = request.getPathInfo();
		ActionForward forward = null;
		Action action = null;

		Cookie[] cookies = request.getCookies();
		Cookie mem_info_cookie = null;

		if (cookies != null)
			for (Cookie c : cookies) {
				if (c.getName().equals("mem_info")) {
					mem_info_cookie = c;
					break;
				}
			}

		if (mem_info_cookie == null) {
			response.sendRedirect("/BeansPaM");
			return;
		}

		/** 관리자 @author 강동준 */
		if (pathInfo.equals("/admin")) {
			action = new AdminAction();

			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/** 사원 정보 상세보기 @author 강동준 */
		else if (pathInfo.equals("/admin_detail")) {
			action = new AdminDetailAction();

			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/** 사원 추가 페이지 @author 강동준 */
		else if (pathInfo.equals("/admin_regist")) {
			forward = new ActionForward("/pages/admin_regist.jsp");
		}

		/** 사원 추가 @author 강동준 */
		else if (pathInfo.equals("/regist_member")) {
			action = new MemberRegistAction();

			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/** 사원 삭제 @author 강동준 */
		else if (pathInfo.equals("/admin_del")) {
			action = new AdminDelAction();

			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 마이페이지 - 민기홍
		else if (pathInfo.equals("/mypage")) {
			action = new MypageAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/*
		 * 2024-09-23 민기홍 paied 이름변경 salary
		 */
		// 급여
		else if (pathInfo.equals("/salary")) {
			action = new SalaryAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 근태
		else if (pathInfo.equals("/workday")) {
			action = new WorkdayAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 시설 관리
		else if (pathInfo.equals("/stock")) {
			forward = new ActionForward("/pages/stock.html");
		} else if (pathInfo.equals("/stock1")) {
			forward = new ActionForward("/pages/stock_1.html");
		} else if (pathInfo.equals("/stock2")) {
			forward = new ActionForward("/pages/stock_2.html");
		}

		// 자재관리
		else if (pathInfo.equals("/vmi")) {
			forward = new ActionForward("/pages/vmi.html");
		}

		// 결재 상신
		else if (pathInfo.equals("/report")) {
			forward = new ActionForward("/pages/report.html");
		}

		/** 사원 검색 @author 한지수 */
		else if (pathInfo.equals("/inform")) {
			forward = new ActionForward("/pages/search_emp.jsp");
		}

		// 이하 페이지 에러
		else {
			System.out.println("page error. pathInfo = " + pathInfo);
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}
}