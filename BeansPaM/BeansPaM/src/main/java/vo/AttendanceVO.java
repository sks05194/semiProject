/**
 * 최초 생성일: 2024-09-12
 * @author 강동준
 * 
 * 마지막 수정일: 2024-09-23
 * @author 민기홍
 * 
 * 주요 수정 내용: a_checkin, a_checkout 자료형 date >String 변경
 */

package vo;

import java.sql.Date;

/* 근태 내역을 담는 VO 클래스입니다. */
public class AttendanceVO {
	private int m_no = 0;
	private Date a_workdate = null;
	private String a_checkin = null;
	private String a_checkout = null;
	private String a_issue = null;
	
	public int getM_no() { return m_no; }
	public void setM_no(int m_no) { this.m_no = m_no; }

	public Date getA_workdate() { return a_workdate; }
	public void setA_workdate(Date a_workdate) { this.a_workdate = a_workdate; }

	public String getA_checkin() { return a_checkin; }
	public void setA_checkin(String a_checkin) { this.a_checkin = a_checkin; }

	public String getA_checkout() { return a_checkout; }
	public void setA_checkout(String a_checkout) { this.a_checkout = a_checkout; }

	public String getA_issue() { return a_issue; }
	public void setA_issue(String a_issue) { this.a_issue = a_issue; }
	
	@Override
	public String toString() {
		return "AttendanceVO [m_no=" + m_no + ", a_workdate=" + a_workdate + ", a_checkin=" + a_checkin
				+ ", a_checkout=" + a_checkout + ", a_issue=" + a_issue + "]";
	}
	
}