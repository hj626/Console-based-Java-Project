package shop.discount;

public class DiscountDTO {

	private int product_id;
	private int discount_rate;
	private String name;

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public int getDiscount_rate() {
		return discount_rate;
	}

	public void setDiscount_rate(int discount_rate) {
		this.discount_rate = discount_rate;
	}

	public String getName() {
		return name;
	}

	public void setName(String product_name) {
		this.name = product_name;
	}

	@Override
	public String toString() {

		String str = String.format("상품ID: %3d, 상품명: %5s, 할인율: %3d%%", product_id, name, discount_rate);

		return str;
	}

}
