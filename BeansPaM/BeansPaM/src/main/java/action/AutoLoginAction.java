/**
 *  문서 생성일 09.21
 *  @author 강동준
 *  
 *  변경일 09.21
 *  @author 강동준
 *  
 *  변경 사유: 문서 생성
 *  
 *  @version 0.1
 */
package action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vo.ActionForward;

public class AutoLoginAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("mem_info")) {
					// TODO 유효성 검사(쿠키의 m_no가 0 초과일 경우 return 해주기)
					return new ActionForward(true, "fms/mypage");
				}
			}
		}

		return new ActionForward("/pages/login_loginMenu.jsp");
	}
}