/**
 * 최초 생성일: 2024-09-11
 * @author 강동준
 * 
 * 마지막 수정일: 2024-09-12
 * @author 강동준
 * 
 * 주요 수정 내용: 멤버변수 및 getter, setter 재선언
 */

package vo;

/* 결재 문서를 담는 VO 클래스입니다. */
public class DocumentVO {
	String d_no = null;			// 문서번호
	String d_title = null;		// 문서 제목
	String d_class = null;		// 문서 종류: 사직서, 휴직서 등...
	String m_no = null;			// 사번: 요청자
	String d_m_no = null;		// 사번: 작성자
	String d_content = null;	// 내용
	String d_request = null;	// 결재 작성일
	String d_response = null;	// 결재 완료일
	
	public String getD_no() { return d_no; }
	public void setD_no(String d_no) { this.d_no = d_no; }

	public String getD_title() { return d_title; }
	public void setD_title(String d_title) { this.d_title = d_title; }

	public String getD_class() { return d_class; }
	public void setD_class(String d_class) { this.d_class = d_class; }
	
	public String getM_no() { return m_no; }
	public void setM_no(String m_no) { this.m_no = m_no; }

	public String getD_m_no() { return d_m_no; }
	public void setD_m_no(String d_m_no) { this.d_m_no = d_m_no; }

	public String getD_content() { return d_content; }
	public void setD_content(String d_content) { this.d_content = d_content; }

	public String getD_request() { return d_request; }
	public void setD_request(String d_request) { this.d_request = d_request; }

	public String getD_response() { return d_response; }
	public void setD_response(String d_response) { this.d_response = d_response; }
}