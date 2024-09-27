package action;

import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AttendanceDAO;
import vo.ActionForward;
import vo.AttendanceVO;

public class WorkdayAction implements Action {

	 

	@Override
	    public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	        // 쿠키에서 m_no 값 추출하기
	        String memInfo = null;
	        Cookie[] cookies = request.getCookies();
	       
	        if (cookies != null) {
	            for (Cookie cookie : cookies) {
	                if ("mem_info".equals(cookie.getName())) {
	                    memInfo = cookie.getValue(); 
	                    break;
	                    
	                }
	            }
	        }

	        int m_no = 0;
	        if (memInfo != null) {
	            // mem_info에서 "+"를 기준으로 M_no와 M_name을 분리
	            String[] infoParts = memInfo.split("\\+");
	            if (infoParts.length > 0) {
	                m_no = Integer.parseInt(infoParts[0]);  // M_no 값을 정수로 변환
	            }
	        }

	        // m_no가 유효할 때 처리
	        if (m_no > 0) {
	            AttendanceDAO attendanceDAO = new AttendanceDAO();
	            ArrayList<AttendanceVO> attendanceList = attendanceDAO.getMemberByNo(m_no);
	            
	            // attendanceList를 request에 저장
	            request.setAttribute("attendanceList", attendanceList);
	        }

	        // JSP 페이지로 forward
	        return new ActionForward("/pages/workday.jsp");
	    }
	}
