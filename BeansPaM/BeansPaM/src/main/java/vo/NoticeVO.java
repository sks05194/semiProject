/**
 * 최초 생성일: 2024-09-19
 * @author 설보라
 * 
 * 마지막 수정일: 2024-09-21
 * @author 설보라
 * 
 * 주요 수정 내용: DB 컬럼 수정으로 재생성 */
package vo;

public class NoticeVO {
	private int n_no = 0;
	private String n_title = null;
	private String n_content = null;
	private int n_views = 0;
	private String n_delete_yn = null;
	private String n_c_date = null;
	private String n_r_date = null;
	private String n_c_writer = null;
	private String n_r_writer = null;
	private String n_important_yn = null;

	public int getN_no() { return n_no; }
	public void setN_no(int n_no) { this.n_no = n_no; }

	public String getN_title() { return n_title; }
	public void setN_title(String n_title) { this.n_title = n_title; }

	public String getN_content() { return n_content; }
	public void setN_content(String n_content) { this.n_content = n_content; }

	public int getN_views() { return n_views; }
	public void setN_views(int n_views) { this.n_views = n_views; }
	
	public String getN_delete_yn() { return n_delete_yn; }
	public void setN_delete_yn(String n_delete_yn) { this.n_delete_yn = n_delete_yn; }

	public String getN_c_date() { return n_c_date; }
	public void setN_c_date(String n_c_date) { this.n_c_date = n_c_date; }

	public String getN_r_date() { return n_r_date; }
	public void setN_r_date(String n_r_date) { this.n_r_date = n_r_date; }

	public String getN_c_writer() { return n_c_writer; }
	public void setN_c_writer(String n_c_writer) { this.n_c_writer = n_c_writer; }

	public String getN_r_writer() { return n_r_writer; }
	public void setN_r_writer(String n_r_writer) { this.n_r_writer = n_r_writer; }
	
	public String getN_important_yn() { return n_important_yn; } 
	public void setN_important_yn(String n_important_yn) { this.n_important_yn = n_important_yn; }
}