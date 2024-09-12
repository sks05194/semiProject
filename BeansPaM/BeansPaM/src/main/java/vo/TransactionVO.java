/**
 * 최초 생성일: 2024-09-12
 * @author 강동준
 * 
 * 마지막 수정일: 2024-09-12
 * @author 강동준
 * 
 * 주요 수정 내용: 멤버변수와 getter, setter 선언
 */

package vo;

import java.sql.Date;

public class TransactionVO {
	int t_no = 0;			// 거래 번호
	String t_inout = null;	// 분류: 수입/수출
	Date t_date = null;		// 거래 일자
	int s_no = 0;			// 재고 번호: 거래된 항목
	int t_value = 0;		// 수량
	String t_corr = null;	// 거래처
	int m_no = 0;			// 사번: 거래 담당자
	
	public int getT_no() { return t_no; }
	public void setT_no(int t_no) { this.t_no = t_no; }
	
	public String getT_inout() { return t_inout; }
	public void setT_inout(String t_inout) { this.t_inout = t_inout; }
	
	public Date getT_date() { return t_date; }
	public void setT_date(Date t_date) { this.t_date = t_date; }
	
	public int getS_no() { return s_no; }
	public void setS_no(int s_no) { this.s_no = s_no; }
	
	public int getT_value() { return t_value; }
	public void setT_value(int t_value) { this.t_value = t_value; }
	
	public String getT_corr() { return t_corr; }
	public void setT_corr(String t_corr) { this.t_corr = t_corr; }
	
	public int getM_no() { return m_no; }
	public void setM_no(int m_no) { this.m_no = m_no; }
}