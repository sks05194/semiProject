/**
 * 최초 생성일: 2024-09-12
 * @author 강동준
 * 
 * 마지막 수정일: 2024-09-12
 * @author 강동준
 * 
 * 주요 수정 내용: 멤버변수, getter, setter 선언
 */

package vo;

import java.sql.Date;

/* 급여 내역을 담는 VO 클래스입니다. */
public class SalaryVO {
	private int m_no = 0;			// 사번
	private Date sal_date = null;	// 지급일
	private int sal_salary = 0;		// 급여액

	public int getM_no() { return m_no; }
	public void setM_no(int m_no) { this.m_no = m_no; }

	public Date getSal_date() { return sal_date; }
	public void setSal_date(Date sal_date) { this.sal_date = sal_date; }

	public int getSal_salary() { return sal_salary; }
	public void setSal_salary(int sal_salary) { this.sal_salary = sal_salary; }
}