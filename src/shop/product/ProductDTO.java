package shop.product;

import java.sql.Date;

public class ProductDTO {

	private int product_id;
	private String name;
	private String description;
	private int stock;
	private Date reg_date;
	private int price;
	private int discountRate;// 디스카운트 추가 ㅇㅅ ㅜ
	private int discountedPrice;

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public int getDiscountRate() { // 디스카운트 추가 !!!!!
		return discountRate;
	}

	public void setDiscountRate(int discountRate) {
		this.discountRate = discountRate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getDiscountedPrice() {
		return discountedPrice;
	}

	public void setDiscountedPrice(int discountedPrice) {
		this.discountedPrice = discountedPrice;
	}

	@Override
	public String toString() {
		return String.format("%5d | %-10s | %-16s | 재고: %3d개 | 가격: %10d원 | 할인율: %2d%% | 등록일: %tF", product_id, name,
				description, stock, price, discountRate, reg_date);
	}

}
