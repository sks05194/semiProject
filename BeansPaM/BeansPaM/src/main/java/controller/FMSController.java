/**
 * 최초 생성일: 2024-09-11
 * 
 * 기여자
 * @author 강동준
 * @author 민기홍
 * @author 임성현
 * @author 한지수
 * 
 * 주요 수정 내용: /inform 경로 수정
 */
package controller;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;

import action.*;
import svc.VMIService;
import svc.AdminService;
import vo.ActionForward;

@MultipartConfig
public class FMSController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			new AdminService().memberRegistByAdmin(request, response);
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

		// 마이페이지 - 민기홍
		else if (pathInfo.equals("/mypage")) {
			action = new MypageAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (pathInfo.equals("/updateMember")) {
			action = new UpdateMemberAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (pathInfo.equals("/changePassword")) {
			action = new ChangePasswordAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * 09-26 추가
		 * @author 민기홍
		 */
		else if (pathInfo.equals("/commute")) {
			action = new CommuteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/*2024-09-23
		 * 민기홍
		 * paied 이름변경 salary
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
		else if (pathInfo.equals("/warehouse")) {
			forward = new ActionForward("/pages/warehouse.jsp");
		}

		/** 사원 검색 @author 한지수 */
		else if (pathInfo.equals("/inform")) {
			forward = new ActionForward("/pages/search_emp.jsp");
		}

		/** 자재관리 @author 한지수 */
		else if (pathInfo.equals("/vmi")) {
			forward = new ActionForward("/pages/vmi.jsp");
		}
		
		/** 자재 반영 @author 강동준 */
		else if (pathInfo.equals("/save-vmi")) {
			VMIService svc = new VMIService(); 
			
			svc.saveChanges(request, response);
		}

		/** 결재 현황 페이지 @author 임성현 */
		else if (pathInfo.equals("/approval_main")) {
			forward = new ActionForward("/pages/approval_main.jsp");
		}
		
		/** 결재 현황 페이지의 게시글 @author 임성현 */
		else if (pathInfo.equals("/approval_contents")) {
			forward = new ActionForward("/pages/approval_contents.jsp");
		}
		
		/** 결재 상신 페이지 @author 임성현 */
		else if (pathInfo.equals("/approval_write")) {
			forward = new ActionForward("/pages/approval_write.jsp");
		}
		
		/** 결재 상신 액션 @author 임성현 */
		else if (pathInfo.equals("/approval_write_action")) {
			action = new ApprovalWriteAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/** 결재 현황 액션 @author 임성현 */
		else if (pathInfo.equals("/approval_main_action")) {
			action = new ApprovalMainAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/** 결재 현황 게시글 검색 액션 @author 임성현 */
		else if (pathInfo.equals("/approval_search_action")) {
			action = new ApprovalSearchAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/** 결재 현황 게시글 액션 @author 임성현 */
		else if (pathInfo.equals("/approval_contents_action")) {
			action = new ApprovalContentsAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/** 결재 현황 게시글 삭제 액션 @author 임성현 */
		else if (pathInfo.equals("/approval_contents_del_action")) {
			action = new ApprovalContentsDelAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/** 결재 현황 게시글 결재 확인 액션 @author 임성현 */
		else if (pathInfo.equals("/approval_contents_confirm_action")) {
			action = new ApprovalContentsConfirmAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/** 결재 현황 게시글 파일 다운로드 액션 @author 임성현 */
		else if (pathInfo.equals("/approval_contents_file_action")) {
			action = new ApprovalContentsFileAction();	
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 이하 페이지 에러
		else {
			System.out.println("FMSController: page error. pathInfo = " + pathInfo);
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