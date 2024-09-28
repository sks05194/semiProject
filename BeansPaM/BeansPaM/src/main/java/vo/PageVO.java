/**
 * @since 09.27(추정)
 * @author 임성현(추정)
 * 
 * ps. 이게 뭐에요?
 */

package vo;

public class PageVO {
	private int startPage; // 게시글 첫 번째 번호
	private int endPage; // 게시글 마지막 번호
	private boolean prev, next; // 이전, 다음 버튼 활성화 여부

	private int pageNum; // 현재 조회하는 페이지 번호
	private int amount = 10; // 화면에 보여줄 게시글 수
	private int total; // 전체게시글 수

	public PageVO(int pageNum, int amount, int total) {
		this.pageNum = pageNum;
		this.amount = amount;
		this.total = total;

		this.endPage = (int)Math.ceil(this.pageNum * 0.1) * 10;

		this.startPage = this.endPage - 10 + 1;

		int realEnd = (int) Math.ceil(this.total / (double) this.amount);

		if (this.endPage > realEnd) {
			this.endPage = realEnd;
		}

		this.prev = this.startPage > 1;

		this.next = this.endPage < realEnd;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
