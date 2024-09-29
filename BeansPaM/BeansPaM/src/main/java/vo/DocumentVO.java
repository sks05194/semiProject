/**
 * 최초 생성일: 2024-09-11
 * @author 강동준
 * 
 * 마지막 수정일: 2024-09-29
 * @author 임성현
 * 
 * 주요 수정 내용: m_name, d_m_name, m_position, filename 추가
 */

package vo;

/* 결재 문서를 담는 VO 클래스입니다. */
public class DocumentVO {
	private int d_no = 0;               // 결재 번호
	private String d_title = null;      // 결재 제목
	private String d_class = null;      // 결재 종류: 사직서, 휴직서 등...
	private int m_no = 0;               // 결재 작성자 사번
	private int d_m_no = 0;             // 결재 승인자 사번
	private String d_content = null;    // 결재 내용
	private String d_request = null;    // 결재 작성일
	private String d_response = null;   // 결재 승인일
	private String m_name = null;       // 결재 작성자 이름 - 새로추가
	private String d_m_name = null;     // 결재 승인자 이름 - 새로추가
	private String m_position = null;   // 직급 - 새로추가
	private String filename = null;     // 파일명 - 새로추가 및 DB 컬럼에도 추가

	public int getD_no() { return d_no; }
	public void setD_no(int d_no) { this.d_no = d_no; }
	
	public String getD_title() { return d_title; }
	public void setD_title(String d_title) { this.d_title = d_title; }
	
	public String getD_class() { return d_class; }
	public void setD_class(String d_class) { this.d_class = d_class; }
	
	public int getM_no() { return m_no; }
	public void setM_no(int m_no) { this.m_no = m_no; }
	
	public int getD_m_no() { return d_m_no; }
	public void setD_m_no(int d_m_no) { this.d_m_no = d_m_no; }
	
	public String getD_content() { return d_content; }
	public void setD_content(String d_content) { this.d_content = d_content; }
	
	public String getD_request() { return d_request; }
	public void setD_request(String d_request) { this.d_request = d_request; }
	
	public String getD_response() { return d_response; }
	public void setD_response(String d_response) { this.d_response = d_response; }
	
	public String getM_name() { return m_name; }
	public void setM_name(String m_name) { this.m_name = m_name; }
	
	public String getD_m_name() { return d_m_name; }
	public void setD_m_name(String d_m_name) { this.d_m_name = d_m_name; }
	
	public String getM_position() { return m_position; }
	public void setM_position(String m_position) { this.m_position = m_position; }
	
	public String getFilename() { return filename; }
	public void setFilename(String filename) {this.filename = filename; }
}