/**
 * 최초 생성일: 2024-09-11
 * @author 강동준
 * 
 * 최종 수정일: 2024-09-20
 * @author 민기홍
 * 
 * 주요 수정 내용: 실수 수정
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
import action.MypageAction;
import vo.ActionForward;

public class FMSController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String RequestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = RequestURI.substring(contextPath.length());
		String pathInfo = request.getPathInfo();
		ActionForward forward = null;
		Action action = null;
//		HttpSession session = request.getSession();
		
		System.out.println(command);
		
		Cookie mem_info_cookie = null;
		
		for (Cookie c : request.getCookies()) {
			if (c.getName().equals("mem_info")) {
				mem_info_cookie = c;
				break;
			}
		}

		if (mem_info_cookie == null) {
			System.out.println("쿠키 없음");
			response.sendRedirect("/BeansPaM");
		} else {
			System.out.println("쿠키: " + mem_info_cookie.getValue());
		}
		
		
		// 관리자
		if (pathInfo.equals("/admin")) {
			forward = new ActionForward("/pages/admin.html");
		}
		
		// 마이페이지 - 민기홍
		else if (pathInfo.equals("/mypage")) {
			action = new MypageAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
//			forward = new ActionForward("/pages/mypage.jsp");
		}
		
		// 급여
		else if (pathInfo.equals("/paied")) {
			forward = new ActionForward("/pages/paied.html");
		}
		// 근태
		else if (pathInfo.equals("/workday")) {
			forward = new ActionForward("/pages/workday.html");
		}
		
		// 자재 현황
		else if (pathInfo.equals("/stock")) {
			forward = new ActionForward("/pages/stock.html");
		}
		else if (pathInfo.equals("/stock1")) {
			forward = new ActionForward("/pages/stock_1.html");
		}
		else if (pathInfo.equals("/stock2")) {
			forward = new ActionForward("/pages/stock_2.html");
		}
		
		
		// 결재 상신
		else if (pathInfo.equals("/report")) {
			forward = new ActionForward("/pages/report.html");
		}
		
		
		
		// 사원 검색
		else if (pathInfo.equals("/inform")) {
			forward = new ActionForward("/pages/search_emp.html");
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