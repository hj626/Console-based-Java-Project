package shop.review;

// 사용자 리뷰가 아니라 쇼핑몰 제품의 MD리뷰
// 제품의 한줄평 같은 그런 리뷰 => 관리자의 리뷰라서 하나만 쓸 수 있음ㅋㅎㅋㅎ (리뷰관리)

// 데이터 저장용 (리뷰 정보 저장)

public class ReviewDTO {

	private String review_id;
	private int product_id;
	private String product;
	private String review;

	// 기본 생성자 객체생성 해놨음
	public ReviewDTO() {
	}

	// 매개변수를 사용해서 객체 생성 시 필요한 값을 바로 받을 수 있게 해놈
	public ReviewDTO(String review_id, int product_id, String product, String review) {

		this.review_id = review_id;
		this.product_id = product_id;
		this.product = product;
		this.review = review;

	}

	public String getReview_id() {
		return review_id;
	}

	public void setReview_id(String review_id) {
		this.review_id = review_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	@Override
	public String toString() {

		String str;
		str = String.format("%s %d %s %s", review_id, product_id, product, review);

		return str;
	}

}
