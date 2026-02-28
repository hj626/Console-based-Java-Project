package shop.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shop.db.DBConn;
import shop.review.ReviewDAO;
import shop.review.ReviewDTO;

public class ProductDAO {

	public int insertProduct(ProductDTO dto) {

		int result = 0;

		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "INSERT INTO PRODUCT (product_id, name, description, stock, reg_date, price) ";
			sql += "VALUES (PRODUCT_SEQ.NEXTVAL, ?, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getDescription());
			pstmt.setInt(3, dto.getStock());
			pstmt.setDate(4, new java.sql.Date(dto.getReg_date().getTime()));
			pstmt.setInt(5, dto.getPrice());

			result = pstmt.executeUpdate();
			pstmt.close();

		} catch (Exception e) {
			System.out.println("insertProduct 오류: " + e.toString());
		}

		return result;
	}

	// --상품등록 가능한 ID시작 번호 추출
	public int getMaxProductID() {
		int maxId = 0;

		String sql = "select NVL(MAX(product_id), 0) From product";

		try (Connection conn = DBConn.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			if (rs.next()) {
				maxId = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return maxId;
	}

//----상품수정			
	public int updateProduct(ProductDTO dto, int fieldNum) {
		int result = 0;
		String sql = null;
		switch (fieldNum) {

		case 1:
			sql = "UPDATE PRODUCT SET name = ? WHERE product_id = ?";
			break;
		case 2:
			sql = "UPDATE PRODUCT SET description = ? WHERE product_id = ?";
			break;
		case 3:
			sql = "UPDATE PRODUCT SET stock = ? WHERE product_id = ?";
			break;
		case 4:
			sql = "UPDATE PRODUCT SET price = ? WHERE product_id = ?";
			break;
		default:
			System.out.println("잘못된 수정항목입니다.");
			return 0;
		}

		try (Connection conn = DBConn.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			switch (fieldNum) {
			case 1:
				pstmt.setString(1, dto.getName());
				break;
			case 2:
				pstmt.setString(1, dto.getDescription());
				break;
			case 3:
				pstmt.setInt(1, dto.getStock());
				break;
			case 4:
				pstmt.setInt(1, dto.getPrice());
				break;
			}
			pstmt.setInt(2, dto.getProduct_id());
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("updateProduct 오류: " + e.toString());
		}

		return result;
	}
//--------

	public int deleteProduct(int product_id) {
		int result = 0;

		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM PRODUCT WHERE product_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product_id);

			result = pstmt.executeUpdate();
			pstmt.close();

		} catch (Exception e) {
			System.out.println("deleteProduct 오류: " + e.toString());
		}

		return result;
	}

	// 4. 상품 전체 조회
	public List<ProductDTO> getAllProducts() {

		List<ProductDTO> list = new ArrayList<>();

//	        Connection conn = DBConn.getConnection();

		Connection conn = DBConn.getConnection();//
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT P.product_id, P.name, P.description, P.stock, P.price, P.reg_date, ";
			sql += "NVL(D.discount_rate, 0) AS discount_rate ";
			sql += "FROM PRODUCT P LEFT JOIN DISCOUNT D ON P.product_id = D.product_id ";
			sql += "ORDER BY P.product_id ASC";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductDTO dto = new ProductDTO();

				dto.setProduct_id(rs.getInt("product_id"));
				dto.setName(rs.getString("name"));
				dto.setDescription(rs.getString("description"));
				dto.setStock(rs.getInt("stock"));
				dto.setPrice(rs.getInt("price")); // 김.추가
				dto.setReg_date(rs.getDate("reg_date"));

				int discountRate = rs.getInt("discount_rate");
				dto.setDiscountRate(discountRate);

				int discountedPrice = (int) (dto.getPrice() * (100 - discountRate) / 100.0);
				dto.setDiscountedPrice(discountedPrice);

				list.add(dto);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println("getAllProducts 오류: " + e.toString());
		}

		return list;
	}

	// 5. 상품 하나만 검색 (상품번호로)
	public ProductDTO getProductById(int product_id) {
		ProductDTO dto = null;

		String sql;

		try {
			Connection conn = DBConn.getConnection();
			PreparedStatement pstmt = null;
			ResultSet rs = null;

			// 김.sql문 수정
			sql = "SELECT P.product_id, P.name, P.description, P.stock, P.price, P.reg_date, ";
			sql += "NVL(D.discount_rate, 0) AS discount_rate ";
			sql += "FROM PRODUCT P LEFT JOIN DISCOUNT D ";
			sql += "ON P.product_id = D.product_id where P.product_id = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, product_id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new ProductDTO();

				dto.setProduct_id(rs.getInt("product_id"));
				dto.setName(rs.getString("name"));
				dto.setDescription(rs.getString("description"));
				dto.setStock(rs.getInt("stock"));
				dto.setPrice(rs.getInt("price")); // 김.추가
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setDiscountRate(rs.getInt("discount_rate")); // 김.추가
				int discountedPrice = (int) (dto.getPrice() * ((100 - dto.getDiscountRate()) / 100.0)); // 김.추가
				dto.setDiscountedPrice(discountedPrice); // 김.추가
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println("getProductById 오류: " + e.toString());
		}

		return dto;
	}

	public List<ProductDTO> getDiscountedProductList() {
		List<ProductDTO> list = new ArrayList<>();

		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT P.product_id, P.name, P.description, P.stock, P.reg_date, P.price, ";
		sql += "NVL(D.discount_rate, 0) AS discount_rate ";
		sql += "FROM PRODUCT P LEFT JOIN DISCOUNT D ";
		sql += "ON P.product_id = D.product_id";

		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductDTO dto = new ProductDTO();

				dto.setProduct_id(rs.getInt("product_id"));
				dto.setName(rs.getString("name"));
				dto.setDescription(rs.getString("description"));
				dto.setStock(rs.getInt("stock"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setPrice(rs.getInt("price"));
				dto.setDiscountRate(rs.getInt("discount_rate")); // 할인율 추가
				int discountedPrice = (int) (dto.getPrice() * ((100 - dto.getDiscountRate()) / 100.0));
				dto.setDiscountedPrice(discountedPrice);

				list.add(dto);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return list;
	}

	// 상품명 키워드로 검색
	public List<ProductDTO> searchProductsByName(String keyword) {
		List<ProductDTO> list = new ArrayList<>();
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT P.product_id, P.name, P.description, P.stock, P.price, P.reg_date, "
				+ "NVL(D.discount_rate, 0) AS discount_rate " + "FROM PRODUCT P LEFT JOIN DISCOUNT D "
				+ "ON P.product_id = D.product_id " + "WHERE P.name LIKE ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%"); // 부분 검색
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductDTO dto = new ProductDTO();
				dto.setProduct_id(rs.getInt("product_id"));
				dto.setName(rs.getString("name"));
				dto.setDescription(rs.getString("description"));
				dto.setStock(rs.getInt("stock"));
				dto.setPrice(rs.getInt("price"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setDiscountRate(rs.getInt("discount_rate"));
				int discountedPrice = (int) (dto.getPrice() * ((100 - dto.getDiscountRate()) / 100.0));
				dto.setDiscountedPrice(discountedPrice);
				list.add(dto);
			}

			rs.close();
			pstmt.close();
		} catch (Exception e) {
			System.out.println("searchProductsByName 오류: " + e.toString());
		}

		return list;
	}

	// 품절임박 재고 추가
	public List<ProductDTO> getRowStock() {

		List<ProductDTO> list = new ArrayList<ProductDTO>();
		Connection conn = DBConn.getConnection();

		PreparedStatement psmtm = null;
		ResultSet rs = null;

		String sql = "SELECT P.product_id, P.name, P.description, P.stock, P.reg_date,";
		sql += "NVL(P.price, 0) AS price, ";
		sql += "NVL(D.discount_rate, 0) AS discount_rate ";
		sql += "FROM PRODUCT P LEFT JOIN DISCOUNT D ";
		sql += "ON P.product_id = D.product_id ";
		sql += "WHERE P.stock <= 3 and P.stock >0 "; // 3개 이하로 재고가 남았을 때

		try {

			psmtm = conn.prepareStatement(sql);
			rs = psmtm.executeQuery();

			while (rs.next()) {

				ProductDTO dto = new ProductDTO();

				dto.setProduct_id(rs.getInt("product_id"));
				dto.setName(rs.getString("name"));
				dto.setDescription(rs.getString("description"));
				dto.setStock(rs.getInt("stock"));
				dto.setPrice(rs.getInt("price"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setDiscountRate(rs.getInt("discount_rate"));
				int discountedPrice = (int) (dto.getPrice() * ((100 - dto.getDiscountRate()) / 100.0));
				dto.setDiscountedPrice(discountedPrice);
				list.add(dto);

			}
			rs.close();
			psmtm.close();

		} catch (Exception e) {
			System.out.println("오류발생" + e.toString());
		}
		return list;

	}

	// 상품 조회하면 리뷰 보이게 하는 메서드 추가했습니다.----

	public Map<String, Object> getProductWithReviews(int productId) {
		Map<String, Object> resultMap = new HashMap<>();

		// 기존의 getProductById 메서드를 호출해 상품 조회
		ProductDTO product = getProductById(productId);

		// ReviewDAO 객체 생성 및 리뷰 조회 메서드 호출
		ReviewDAO reviewDAO = new ReviewDAO();
		List<ReviewDTO> reviews = reviewDAO.getReviewsByProductId(productId);

		// 조회된 상품과 리뷰 리스트를 Map에 저장하여 반환
		resultMap.put("product", product);
		resultMap.put("reviews", reviews);

		return resultMap;
	}

	// 여기까지---------
}
