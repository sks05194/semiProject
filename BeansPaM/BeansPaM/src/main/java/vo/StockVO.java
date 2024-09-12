/**
 * 최초 생성일: 2024-09-11
 * @author 강동준
 * 
 * 마지막 수정일: 2024-09-12
 * @author 강동준
 * 
 * 주요 수정 내용: l_no -> w_no
 */

package vo;

import java.sql.Date;

/* 재고 정보를 담는 VO 클래스 입니다. */
public class StockVO {
	String s_no = null;	// 재고번호
	Date s_day = null;	// 수입일자
	String p_no = null;	// 제품번호
	String w_no = null;	// 창고번호
	int s_amount = 0;	// 재고량(kg)
	
	public String getS_no() { return s_no; }
	public void setS_no(String s_no) { this.s_no = s_no; }
	
	public Date getS_day() { return s_day; }
	public void setS_day(Date s_day) { this.s_day = s_day; }
	
	public String getP_no() { return p_no; }
	public void setP_no(String p_no) { this.p_no = p_no; }
	
	public String getW_no() { return w_no; }
	public void setW_no(String w_no) { this.w_no = w_no; }
	
	public int getS_amount() { return s_amount; }
	public void setS_amount(int s_amount) { this.s_amount = s_amount; }
}