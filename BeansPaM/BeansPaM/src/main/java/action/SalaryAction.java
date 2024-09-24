package action;

import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SalaryDAO;
import vo.ActionForward;
import vo.SalaryVO;

public class SalaryAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 쿠키에서 m_no 값 추출하기
		String memInfo = null;
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("mem_info".equals(cookie.getName())) {
					memInfo = cookie.getValue();
					System.out.println("memInfo" + memInfo);
					break;

				}
			}
		}

		int m_no = 0;
		if (memInfo != null) {
			// mem_info에서 "+"를 기준으로 M_no와 M_name을 분리
			String[] infoParts = memInfo.split("\\+");
			if (infoParts.length > 0) {
				m_no = Integer.parseInt(infoParts[0]); // M_no 값을 정수로 변환
				System.out.println("m_no: " + m_no);
			}
		}

		// m_no가 유효할 때 처리
		if (m_no > 0) {
			SalaryDAO salaryDAO = new SalaryDAO();
			ArrayList<SalaryVO> salaryList = salaryDAO.getMemberByNo(m_no);
			double salarySum = salaryDAO.salarysum(m_no); // 합계를 반환하는 메소드라고 가정
		    ArrayList<Double> salaryValList = salaryDAO.salaryval(m_no); // 여러 값을 받아오기

		    // salaryList와 salarySum, salaryValList를 request에 저장
		    request.setAttribute("salaryList", salaryList);
		    request.setAttribute("salarySum", salarySum);
		    request.setAttribute("salaryValList", salaryValList); // List를 JSP로 전달
		}
		// salary.jsp로 포워딩

		return new ActionForward("/pages/salary.jsp");
	}

}
