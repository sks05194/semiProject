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

public class CommentsVO {
	String b_class = null;		// 구분
	int b_no = 0;				// 글 번호
	String c_writer = null;		// 댓글 작성자
	Date c_date = null;			// 댓글 작성 시간
	String c_content = null;	// 댓글 내용

	public String getB_class() { return b_class; }
	public void setB_class(String b_class) { this.b_class = b_class; }

	public int getB_no() { return b_no; }
	public void setB_no(int b_no) { this.b_no = b_no; }

	public String getC_writer() { return c_writer; }
	public void setC_writer(String c_writer) { this.c_writer = c_writer; }

	public Date getC_date() { return c_date; }
	public void setC_date(Date c_date) { this.c_date = c_date; }

	public String getC_content() { return c_content; }
	public void setC_content(String c_content) { this.c_content = c_content; }
}
