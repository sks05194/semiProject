/**
 * 최초 생성일: 2024-09-11
 * @author 강동준
 * 
 * 마지막 수정일: 2024-09-11
 * @author 강동준
 * 
 * 주요 수정 내용: 문서 생성
 */

package vo;

public class ActionForward {
	private boolean isRedirect = false;	// 리다이렉트 여부
	private String path = null;			// 이동되는 페이지
	
	public boolean isRedirect() { return isRedirect; }
	public void setRedirect(boolean isRedirect) { this.isRedirect = isRedirect; }
	
	public String getPath() { return path; }
	public void setPath(String path) { this.path = path; }
}