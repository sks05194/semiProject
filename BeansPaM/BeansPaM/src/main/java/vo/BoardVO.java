/**
 * 최초 생성일: 2024-09-11
 * @author 강동준
 * 
 * 마지막 수정일: 2024-09-12
 * @author 강동준
 * 
 * 주요 수정 내용: 멤버변수 생성 및 getter, setter 선언
 */

package vo;

import java.sql.Date;

/* 게시판에 나타날 데이터들의 VO 클래스입니다. */
public class BoardVO {
	String b_class = null;		// 구분: Q&A 또는 공지사항 
	String b_no = null;			// 글번호
	String b_title = null;		// 제목
	String b_writer = null;		// 작성자
	String b_right = null;		// 읽기 권한
	Date b_date = null;			// 글 작성일
	int b_views = 0;			// 조회수
	String b_content = null;	// 내용
	
	public String getB_class() { return b_class; }
	public void setB_class(String b_class) { this.b_class = b_class; }
	
	public String getB_no() { return b_no; }
	public void setB_no(String b_no) { this.b_no = b_no; }
	
	public String getB_title() { return b_title; }
	public void setB_title(String b_title) { this.b_title = b_title; }
	
	public String getB_writer() { return b_writer; }
	public void setB_writer(String b_writer) { this.b_writer = b_writer; }
	
	public String getB_right() { return b_right; }
	public void setB_right(String b_right) { this.b_right = b_right; }
	
	public Date getB_date() { return b_date; }
	public void setB_date(Date b_date) { this.b_date = b_date; }
	
	public int getB_views() { return b_views; }
	public void setB_views(int b_views) { this.b_views = b_views; }
	
	public String getB_content() { return b_content; }
	public void setB_content(String b_content) { this.b_content = b_content; }
}