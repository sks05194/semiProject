/**
 * 최초 생성일: 2024-09-20
 * @author 강동준
 * 
 * 마지막 수정일: 2024-09-20
 * @author 강동준
 * 
 * 주요 수정 내용: VO 생성 및 멤버변수, getter, setter 선언
 */

package vo;

import java.sql.Date;

public class QnAVO {
	int q_no = 0;				// 글번호
	String q_title = null;		// 제목
	String q_writer = null;		// 작성자
	String q_right = null;		// 읽기 권한
	Date q_date = null;			// 글 작성일
	int q_views = 0;			// 조회수
	String q_content = null;	// 내용
	
	public int getQ_no() { return q_no; }
	public void setQ_no(int q_no) { this.q_no = q_no; }
	
	public String getQ_title() { return q_title; }
	public void setQ_title(String q_title) { this.q_title = q_title; }
	
	public String getQ_writer() { return q_writer; }
	public void setQ_writer(String q_writer) { this.q_writer = q_writer; }
	
	public String getQ_right() { return q_right; }
	public void setQ_right(String q_right) { this.q_right = q_right; }
	
	public Date getQ_date() { return q_date; }
	public void setQ_date(Date q_date) { this.q_date = q_date; }
	
	public int getQ_views() { return q_views; }
	public void setQ_views(int q_views) { this.q_views = q_views; }
	
	public String getQ_content() { return q_content; }
	public void setQ_content(String q_content) { this.q_content = q_content; }
}