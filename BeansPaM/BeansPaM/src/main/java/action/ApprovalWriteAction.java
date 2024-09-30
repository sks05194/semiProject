/**
 * 최초 생성일: 2024-09-25
 * @author 임성현
 * 
 * 최종 수정일: 2024-09-29
 * @author 임성현
 * 
 * 주요 수정 내용: 파일 다운로드를 위한 메소드 추가 및 수정
 */
package action;

import java.io.*;
import javax.servlet.http.*;
import vo.*;
import svc.ApprovalService;

public class ApprovalWriteAction implements Action {

	// 파일 이름이 중복되지 않도록 처리하는 메서드
	private String getUniqueFileName(String directory, String fileName) {
		File file = new File(directory, fileName);
		String baseName = fileName.substring(0, fileName.lastIndexOf('.')); // 확장자를 제외한 파일 이름
		String extension = fileName.substring(fileName.lastIndexOf('.')); // 파일 확장자
		int counter = 1; // 카운터 초기화

		// 파일이 존재할 경우 반복
		while (file.exists()) {
			file = new File(directory, baseName + "(" + counter + ")" + extension);
			counter++;
		}

		return file.getName(); // 고유한 파일 이름 반환
	}

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ApprovalService approvalService = new ApprovalService();
		DocumentVO documentVO = new DocumentVO();
		ActionForward forward = null;
		Part filePart = null;
		String fileName = null;
		String filePath = null;
		String saveFolder = "files";
		int Success = 0;

		Cookie[] cookies = request.getCookies();
		int mNo = 0;

		// 쿠키에서 사원번호 mNO 가져오기
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("mem_info".equals(cookie.getName())) {
					mNo = Integer.parseInt(cookie.getValue().split("\\+")[0]);
					break;
				}
			}
		}

		// mNo가 안 바뀌고 0일시 실행
		if (mNo == 0) {
			forward = new ActionForward(true, "loginMenu.l?failedLogin=failedLogin");
			return forward;
		}

		// 결재 상신 폼을 가져온다
		String att = request.getParameter("hiddenAttendanceForm"); // 근태 폼
		String trip = request.getParameter("hiddenTripForm"); // 출장 폼
		String esti = request.getParameter("hiddenEstimateForm"); // 견적 폼

		// 근태 폼
		if (att != null) {
			String attType = request.getParameter("attendanceType"); // 근태 종류 (휴가, 조퇴, 외출)
			String stDate = request.getParameter("startDate"); // 시작 날짜
			String endDate = request.getParameter("endDate"); // 종료 날짜
			String reason = request.getParameter("reason"); // 사유
			filePart = request.getPart("fileUpload"); // 근태 파일

			documentVO.setD_title(attType + " 신청서");
			documentVO.setD_class("휴가계");
			documentVO.setD_content("시작 날짜 : " + stDate + "<br>" + "종료 날짜 : " + endDate + "<br>" + "사유 : " + reason);
			documentVO.setM_no(mNo);

			// 파일 업로드 처리 부분에서 호출
			if (filePart != null && filePart.getSize() > 0) {
				fileName = filePart.getSubmittedFileName(); // 원래 파일 이름
				filePath = "C:\\semiProject\\BeansPaM\\BeansPaM\\src\\main\\webapp\\" + saveFolder;
				File f = new File(filePath);
				if (!f.exists()) {
					f.mkdirs();
				}

				// 고유한 파일 이름 생성
				String uniqueFileName = getUniqueFileName(filePath, fileName);

				System.out.println(filePath + File.separator + uniqueFileName);
				// 파일 저장
				filePart.write(filePath + File.separator + uniqueFileName);
				documentVO.setFilename(uniqueFileName);
			}

			Success = approvalService.attendanceAction(documentVO);
			if (Success == 0) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('등록실패');");
				out.println("history.back();");
				out.println("</script>");
			}
		}

		// 출장 폼
		if (trip != null) {
			String tripDest = request.getParameter("tripDestination"); // 출장 목적지
			String tripPurp = request.getParameter("tripPurpose"); // 출장 목적
			String tripStDate = request.getParameter("tripStartDate"); // 출장 시작 날짜
			String tripEnDate = request.getParameter("tripEndDate"); // 출장 종료 날짜
			String tripDetails = request.getParameter("tripDetails"); // 세부 사항
			filePart = request.getPart("tripFileUpload"); // 출장 파일

			documentVO.setD_title("출장 신청서");
			documentVO.setD_class("출장서");
			documentVO.setD_content("출장 시작 날짜 : " + tripStDate + "<br>" + "출장 종료 날짜 : " + tripEnDate + "<br>"
					+ "출장 목적지 : " + tripDest + "<br>" + "출장 목적 : " + tripPurp + "<br>" + "출장 세부사항 : " + tripDetails);
			documentVO.setM_no(mNo);

			// 파일 업로드 처리 부분에서 호출
			if (filePart != null && filePart.getSize() > 0) {
				fileName = filePart.getSubmittedFileName(); // 원래 파일 이름
				filePath = "C:\\semiProject\\BeansPaM\\BeansPaM\\src\\main\\webapp\\" + saveFolder;
				File f = new File(filePath);
				if (!f.exists()) {
					f.mkdirs();
				}

				// 고유한 파일 이름 생성
				String uniqueFileName = getUniqueFileName(filePath, fileName);

				// 파일 저장
				filePart.write(filePath + File.separator + uniqueFileName);
				documentVO.setFilename(uniqueFileName);
			}

			Success = approvalService.tripAction(documentVO);
			if (Success == 0) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('등록실패');");
				out.println("history.back();");
				out.println("</script>");
			}
		}

		// 견적 폼
		if (esti != null) {
			String estiComp = request.getParameter("estimateCompany"); // 견적서 발행 회사
			int estiAmou = Integer.parseInt(request.getParameter("estimateAmount")); // 견적 금액 // 숫자
			String estiDate = request.getParameter("estimateDate"); // 견적 발행 날짜
			String estiDetails = request.getParameter("estimateDetails"); // 세부 사항
			filePart = request.getPart("estimateFileUpload"); // 견적 파일

			documentVO.setD_title("견적 신청서");
			documentVO.setD_class("지출서");
			documentVO.setD_content("견적서 발행 날짜 : " + estiDate + "<br>" + "견적서 발행 회사 : " + estiComp + "<br>" + "견적 금액 : "
					+ estiAmou + "원" + "<br>" + "세부 사항 : " + estiDetails);
			documentVO.setM_no(mNo);

			// 파일 업로드 처리 부분에서 호출
			if (filePart != null && filePart.getSize() > 0) {
				fileName = filePart.getSubmittedFileName(); // 원래 파일 이름
				filePath = "C:\\semiProject\\BeansPaM\\BeansPaM\\src\\main\\webapp\\" + saveFolder;
				File f = new File(filePath);
				if (!f.exists()) {
					f.mkdirs();
				}

				// 고유한 파일 이름 생성
				String uniqueFileName = getUniqueFileName(filePath, fileName);

				// 파일 저장
				filePart.write(filePath + File.separator + uniqueFileName);
				documentVO.setFilename(uniqueFileName);
			}

			Success = approvalService.estimateAction(documentVO);
			if (Success == 0) {
				response.setContentType("text/html;charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('등록실패');");
				out.println("history.back();");
				out.println("</script>");
			}
		}
		forward = new ActionForward(true, "approval_main_action");
		return forward;
	}

}
