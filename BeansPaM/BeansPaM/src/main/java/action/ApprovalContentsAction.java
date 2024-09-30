/**
 * 최초 생성일: 2024-09-25
 * @author 임성현
 * 
 * 최종 수정일: 2024-09-29
 * @author 임성현
 * 
 * 주요 수정 내용: 조건문 수정
 */
package action;

import java.util.List;
import javax.servlet.http.*;
import svc.ApprovalService;
import vo.*;

public class ApprovalContentsAction implements Action {

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ApprovalService approvalService = new ApprovalService();
		ActionForward forward = null;

		int mNo = 0;
		int d_no = 0;
		int d_m_no = 0;
		String m_name = "";

		Cookie[] cookies = request.getCookies();

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

		if (request.getParameter("d_no") != null && !request.getParameter("d_no").trim().isEmpty()) {
			d_no = Integer.parseInt(request.getParameter("d_no"));
		}

		List<DocumentVO> getPageContents = approvalService.getPageContentsAction(d_no); // 선택된 게시글 가져오기
		List<DocumentVO> getName = approvalService.getNameAction(mNo); // 이름, 직급 가져오기

		for (DocumentVO documentVO : getPageContents) {
			d_m_no = documentVO.getD_m_no();
		}

		if (d_m_no > 0) {
			m_name = approvalService.getDNameAction(d_m_no);
			request.setAttribute("m_name", m_name);
		}

		if (!getName.isEmpty()) {
			request.setAttribute("getName", getName);
		}

		request.setAttribute("getPageContents", getPageContents);
		
		// 출장 content 분리
		for (DocumentVO document1 : getPageContents) {
		    String content = document1.getD_content(); 
		    
    		String tripStartDateOutput = ""; 
    		String tripEndDateOutput = ""; 
    		String tripDestinationOutput = ""; 
    		String tripPurposeOutput = "";
    		
    		String tripStartDate = ""; 
    		String tripEndDate = ""; 
    		String tripDestination = ""; 
    		String tripPurpose = "";
    		String tripReason = "";

		    if (content.contains("출장 시작 날짜 :")) {
		    	
		    	for (DocumentVO document2 : getPageContents) {
		    		String dContent = document2.getD_content(); 
		    		
		    		String[] parts = dContent.split("<br>");
		    		
		    		for (String part : parts) {
		    			String[] keyValue = part.split(":");
		    			if (keyValue.length > 1) {
		    				String key = keyValue[0].trim(); 
		    				String value = keyValue[1].trim(); 
		    				
		    				if (key.equals("출장 시작 날짜")) {
		    					tripStartDate = value; 
		    					tripStartDateOutput = key; 
		    				} else if (key.equals("출장 종료 날짜")) {
		    					tripEndDate = value; 
		    					tripEndDateOutput = key; 
		    				} else if (key.equals("출장 목적지")) {
		    					tripDestination = value; 
		    					tripDestinationOutput = key; 
		    				} else if (key.equals("출장 목적")) {
		    					tripPurpose = value;
		    					tripPurposeOutput = key;
		    				} else if (key.equals("출장 세부사항")) {
		    					tripReason = value;
		    				}
		    			}
		    		}
		    		
		    	}
		    	request.setAttribute("tripStartDate", tripStartDate);
		    	request.setAttribute("tripEndDate", tripEndDate);
		    	request.setAttribute("tripDestination", tripDestination);
		    	request.setAttribute("tripPurpose", tripPurpose);
		    	request.setAttribute("tripReason", tripReason);
		    	
		    	request.setAttribute("tripStartDateOutput", tripStartDateOutput);
		    	request.setAttribute("tripEndDateOutput", tripEndDateOutput);
		    	request.setAttribute("tripDestinationOutput", tripDestinationOutput);
		    	request.setAttribute("tripPurposeOutput", tripPurposeOutput);
		    	
		    	return forward = new ActionForward("/fms/approval_contents");
		    } else {
		    	break;
		    }
		}
		
		// 견적 content 분리
		for (DocumentVO document1 : getPageContents) {
		    String content = document1.getD_content(); 
		    
    		String estimateDateOutput = "";
    		String estimateCompanyOutput = "";
    		String estimateAmountOutput = "";
    		
    		String estimateDate = ""; 
    		String estimateCompany = ""; 
    		String estimateAmount =""; 
    		String estimateReason = "";

		    if (content.contains("견적서 발행 날짜 :")) {
		    	
		    	for (DocumentVO document2 : getPageContents) {
		    		String dContent = document2.getD_content(); 
		    		
		    		String[] parts = dContent.split("<br>");
		    		
		    		for (String part : parts) {
		    			String[] keyValue = part.split(":");
		    			if (keyValue.length > 1) {
		    				String key = keyValue[0].trim(); 
		    				String value = keyValue[1].trim(); 
		    				
		    				if (key.equals("견적서 발행 날짜")) {
		    					estimateDate = value; 
		    					estimateDateOutput = key; 
		    				} else if (key.equals("견적서 발행 회사")) {
		    					estimateCompany = value; 
		    					estimateCompanyOutput = key; 
		    				} else if (key.equals("견적 금액")) {
		    					estimateAmount = value; 
		    					estimateAmountOutput = key; 
		    				} else if (key.equals("세부 사항")) {
		    					estimateReason = value;
		    				}
		    			}
		    		}
		    		
		    	}
		    	request.setAttribute("estimateDate", estimateDate);
		    	request.setAttribute("estimateCompany", estimateCompany);
		    	request.setAttribute("estimateAmount", estimateAmount);
		    	request.setAttribute("estimateReason", estimateReason);
		    	
		    	request.setAttribute("estimateDateOutput", estimateDateOutput);
		    	request.setAttribute("estimateCompanyOutput", estimateCompanyOutput);
		    	request.setAttribute("estimateAmountOutput", estimateAmountOutput);
		    	return forward = new ActionForward("/fms/approval_contents");
		    } else {
		    	break;
		    }
		}
		
		// 근태 content 분리
		for (DocumentVO document1 : getPageContents) {
		    String content = document1.getD_content(); 
		    
    		String startDateOutput = "";
    		String endDateOutput = "";
    		
    		String startDate = ""; 
    		String endDate = ""; 
    		String reason = ""; 

		    if (content.contains("시작 날짜 :")) {
		    	
		    	for (DocumentVO document2 : getPageContents) {
		    		String dContent = document2.getD_content(); 
		    		
		    		String[] parts = dContent.split("<br>");
		    		
		    		for (String part : parts) {
		    			String[] keyValue = part.split(":");
		    			if (keyValue.length > 1) {
		    				String key = keyValue[0].trim(); 
		    				String value = keyValue[1].trim(); 
		    				
		    				if (key.equals("시작 날짜")) {
		    					startDate = value; 
		    					startDateOutput = key; 
		    				} else if (key.equals("종료 날짜")) {
		    					endDate = value; 
		    					endDateOutput = key; 
		    				} else if (key.equals("사유")) {
		    					reason = value; 
		    				}
		    			}
		    		}
		    		
		    	}
		    	request.setAttribute("startDate", startDate);
		    	request.setAttribute("endDate", endDate);
		    	request.setAttribute("reason", reason);
		    	
		    	request.setAttribute("startDateOutput", startDateOutput);
		    	request.setAttribute("endDateOutput", endDateOutput);
		    	return forward = new ActionForward("/fms/approval_contents");
		    } else {
		    	break;
		    }
		}
		

		forward = new ActionForward("/fms/approval_contents");
		return forward;
	}
}