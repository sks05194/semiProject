/**
 * 최초 생성일: 2024-09-12
 * @author 강동준
 * 
 * 마지막 수정일: 2024-09-12
 * @author 강동준
 * 
 * 주요 수정 내용: 멤버변수, getter, setter 선언
 */

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

/* 창고 데이터를 담는 VO 클래스 */
public class WarehouseVO {
	private int w_no = 0;			// 창고 번호
	private String w_loc = null;	// 창고 위치
	private int m_no = 0;			// 사번: 창고 담당자
	private int w_temp = 0;			// 온도
	private int w_humi = 0;			// 습도
	
	public int getW_no() { return w_no; }
	public void setW_no(int w_no) { this.w_no = w_no; }
	
	public String getW_loc() { return w_loc; }
	public void setW_loc(String w_loc) { this.w_loc = w_loc; }
	
	public int getM_no() { return m_no; }
	public void setM_no(int m_no) { this.m_no = m_no; }
	
	public int getW_temp() { return w_temp; }
	public void setW_temp(int w_temp) { this.w_temp = w_temp; }
	
	public int getW_humi() { return w_humi; }
	public void setW_humi(int w_humi) { this.w_humi = w_humi; }
}