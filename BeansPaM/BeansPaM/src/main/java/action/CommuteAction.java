/**
 * @since 09.26
 * @author 민기홍
 * @see controller.FMSController
 */
package action;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AttendanceDAO;
import dao.MemberDAO;
import vo.ActionForward;
import vo.AttendanceVO;
import vo.MemberVO;

public class CommuteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 쿠키에서 m_no 값 추출하기
		String memInfo = null;
		Cookie[] cookies = request.getCookies();
		MemberDAO memberDAO = new MemberDAO();
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
				m_no = Integer.parseInt(infoParts[0]); // M_no 값을 정수로 변환
			}
		}

		// m_no가 유효할 때 처리
		if (m_no > 0) {
			AttendanceDAO attendanceDAO = new AttendanceDAO();

			// 오늘 날짜 가져오기
			LocalDate today = LocalDate.now();
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String todayStr = today.format(dateFormatter);

			// 출근/퇴근 버튼에 따라 분기 처리
			String actionType = request.getParameter("actionType"); // 출근/퇴근 구분

			if ("checkin".equals(actionType)) {
				// 해당 사원의 출근 기록이 오늘 이미 존재하는지 확인
				boolean alreadyCheckedIn = attendanceDAO.isAlreadyCheckedIn(m_no, todayStr);

				if (!alreadyCheckedIn) {
					// 출근 기록이 없으면 a_checkin 기록
					LocalDateTime now = LocalDateTime.now();
					String checkInTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

					// 출근 시간 기록
					attendanceDAO.checkIn(m_no, todayStr, checkInTime);

					// 출근 시간이 09:00 이후일 경우 지각 처리
					if (now.getHour() > 9 || (now.getHour() == 9 && now.getMinute() > 0)) {
						attendanceDAO.updateIssue(m_no, todayStr, "지각");
						MemberVO updatedMember = memberDAO.getMemberByNo(m_no);
						request.setAttribute("member", updatedMember);
					}
				}

			} else if ("checkout".equals(actionType)) {
				// 퇴근 기록 처리
				LocalDateTime now = LocalDateTime.now();
				String checkOutTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

				// 퇴근 시간 기록
				attendanceDAO.checkOut(m_no, todayStr, checkOutTime);

				// 퇴근 시간이 18:00 이전일 경우 조퇴 처리
				if (now.getHour() < 18 || (now.getHour() == 18 && now.getMinute() < 0)) {
					attendanceDAO.updateIssue(m_no, todayStr, "조퇴");
				}
			}
		}

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
