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

public class NoticeVO {
	int n_no = 0;				// 글번호
	String n_title = null;		// 제목
	int m_no = 0;				// 작성자(member 테이블 외래키)
	Date n_cdate = null;		// 글 작성일
	Date n_rdate = null;		// 글 수정일
	int n_views = 0;			// 조회수
	String n_content = null;	// 내용
	String n_filepath = null;	// 파일 경로

	public int getN_no() { return n_no; }
	public void setN_no(int n_no) { this.n_no = n_no; }

	public String getN_title() { return n_title; }
	public void setN_title(String n_title) { this.n_title = n_title; }

	public int getM_no() { return m_no; }
	public void setM_no(int m_no) { this.m_no = m_no; }

	public Date getN_cdate() { return n_cdate; }
	public void setN_cdate(Date n_cdate) { this.n_cdate = n_cdate; }

	public Date getN_rdate() { return n_rdate; }
	public void setN_rdate(Date n_rdate) { this.n_rdate = n_rdate; }

	public int getN_views() { return n_views; }
	public void setN_views(int n_views) { this.n_views = n_views; }

	public String getN_content() { return n_content; }
	public void setN_content(String n_content) { this.n_content = n_content; }

	public String getN_filepath() { return n_filepath; }
	public void setN_filepath(String n_filepath) { this.n_filepath = n_filepath; }
}