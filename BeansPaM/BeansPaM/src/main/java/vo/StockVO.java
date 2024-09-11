/*
 * 최초 생성일: 2024-09-11
 * 문서 생성자: 강동준
 * 
 * 마지막 수정일: 2024-09-11
 * 문서 수정자: 강동준
 * */

package vo;

import java.sql.Date;

/* 재고 정보를 담는 VO 클래스 입니다. */
public class StockVO {
	String s_no = null;
	Date s_day = null;
	String p_no = null;
	String l_no = null;
	String s_amount = null;
	
	public String getS_no() { return s_no; }
	public void setS_no(String s_no) { this.s_no = s_no; }
	
	public Date getS_day() { return s_day; }
	public void setS_day(Date s_day) { this.s_day = s_day; }
	
	public String getP_no() { return p_no; }
	public void setP_no(String p_no) { this.p_no = p_no; }
	
	public String getL_no() { return l_no; }
	public void setL_no(String l_no) { this.l_no = l_no; }
	
	public String getS_amount() { return s_amount; }
	public void setS_amount(String s_amount) { this.s_amount = s_amount; }
}