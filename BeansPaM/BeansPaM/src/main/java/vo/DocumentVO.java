/**
 * 최초 생성일: 2024-09-11
 * @author 강동준
 * 
 * 마지막 수정일: 2024-09-11
 * @author 강동준
 * 
 * 주요 수정 내용: 문서 생성
 */

package vo;

public class DocumentVO {
	String d_no = null;			// 문서번호
	String m_no = null;			// 사번(요청자)
	String d_m_no = null;		// 사번(작성자)
	String d_content = null;	// 내용
	
	public String getD_no() { return d_no; }
	public void setD_no(String d_no) { this.d_no = d_no; }
	
	public String getM_no() { return m_no; }
	public void setM_no(String m_no) { this.m_no = m_no; }
	
	public String getD_m_no() { return d_m_no; }
	public void setD_m_no(String d_m_no) { this.d_m_no = d_m_no; }
	
	public String getD_content() { return d_content; }
	public void setD_content(String d_content) { this.d_content = d_content; }
}