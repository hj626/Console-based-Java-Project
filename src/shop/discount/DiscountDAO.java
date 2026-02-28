package shop.discount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import shop.db.DBConn;

public class DiscountDAO {

	// 할인율 등록
	public int insertDiscountRate(DiscountDTO dto) {

		int result = 0;

		Connection conn = DBConn.getConnection();

		PreparedStatement pstmt = null;
		String sql;

		try {

			sql = "insert into discount (product_id, discount_rate) ";
			sql += "values (?,?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, dto.getProduct_id());
			pstmt.setInt(2, dto.getDiscount_rate());

			result = pstmt.executeUpdate();

			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return result;

	}

	// update
	public int updateDiscountRate(DiscountDTO dto) {

		int result = 0;

		Connection conn = DBConn.getConnection();

		PreparedStatement pstmt = null;
		String sql;

		try {

			sql = "update Discount set discount_rate=?"; // ?로 데이터가 들어갈 자리를 표시
			sql += "where product_id=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, dto.getDiscount_rate());
			pstmt.setInt(2, dto.getProduct_id());

			result = pstmt.executeUpdate();

			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return result;

	}

	// delete
	public int deleteDiscountRate(int product_id) {

		int result = 0;

		Connection conn = DBConn.getConnection();

		PreparedStatement pstmt = null;
		String sql;

		try {

			sql = "delete discount where product_id=?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, product_id);

			result = pstmt.executeUpdate();

			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return result;

	}

	// selectAll
	public List<DiscountDTO> getLists() {

		List<DiscountDTO> lists = new ArrayList<DiscountDTO>();

		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "select d.product_id, p.name, d.discount_rate ";
			sql += "from discount d ";
			sql += "join product p ON d.product_id = p.product_id";
//			sql = "select product_id, discount_rate ";
//			sql += "from discount";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {

				DiscountDTO dto = new DiscountDTO();

				dto.setProduct_id(rs.getInt("product_id"));
				dto.setName(rs.getString("name"));
				dto.setDiscount_rate(rs.getInt("discount_rate"));
				// rs에 들어가있는 테이블의 첫번째 데이터를 하나씩 dto에 넣음

				lists.add(dto);
				// lists에 dto 한 번 씩 넣음

			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return lists; // dto가 담긴 lists
	}

	public boolean isDiscountExists(int productId) {
		boolean exists = false;
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "SELECT COUNT(*) FROM discount WHERE product_id = ?";

		try {
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, productId);

			rs = pstmt.executeQuery();

			if (rs.next() && rs.getInt(1) != 0) {
				exists = true;
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return exists;
	}

	public DiscountDTO getListProductId(int id) {

		DiscountDTO dto = null; // 검색한 값이 없을 수도 있기 때문에 null로 해둠
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {

			sql = "select product_id";
			sql += "from product";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {

				dto = new DiscountDTO(); // 객체 생성

				dto.setProduct_id(rs.getInt("product_id"));

			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {

		}
		return dto;
	}

	public boolean isProductExists(int productId) {
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT PRODUCT_ID,NAME,DESCRIPTION,STOCK,REG_DATE FROM product WHERE product_id = ?";
		boolean exists = false;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, productId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				exists = true; // 존재하면 true
			}
		} catch (Exception e) {
			e.printStackTrace(); // 예외 출력
		}
//	    if (rs != null) rs.close();
//	    if (pstmt != null) pstmt.close();

		return exists;
	}

}
