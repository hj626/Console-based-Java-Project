package shop.review;

// DB연결 및 SQL 실행 담당 (등록,조회 등)

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import shop.db.DBConn;

public class ReviewDAO {

	// 아이디가 MEMBER 테이블에 존재하는지 확인
	public boolean isMemberIdExist(String memberId) {
		boolean exists = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			conn = DBConn.getConnection();
			sql = "SELECT COUNT(*) FROM MEMBER WHERE id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				exists = rs.getInt(1) > 0;
			}
		} catch (Exception e) {
			System.out.println("[등록된 아이디가 없습니다.] " + e.toString());
		}

		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
			}
		}

		return exists;
	}

	// 1. 리뷰 추가 (insertReview)
	public int insertReview(ReviewDTO dto) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql;

		try {
			conn = DBConn.getConnection();
			sql = "insert into review (review_id, product_id, product, review) values (?,?,?,?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getReview_id());
			pstmt.setInt(2, dto.getProduct_id());
			pstmt.setString(3, dto.getProduct());
			pstmt.setString(4, dto.getReview());

			result = pstmt.executeUpdate();

			pstmt.close();

		} catch (Exception e) {
			System.out.println("[리뷰를 작성할 수 없습니다.] " + e.toString());
		}

		// 내용 없을 시 다음으로 안 넘어가는 부분 추가

		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
			}
		}

		return result;
	}

	// 2. 리뷰 수정 (updateReview)
	public int updateReview(ReviewDTO dto) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql;

		try {
			conn = DBConn.getConnection();
			sql = "UPDATE REVIEW " + "SET product = ?, review = ? " + "WHERE review_id = ? AND product_id = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getProduct()); // product명
			pstmt.setString(2, dto.getReview()); // 리뷰 내용
			pstmt.setString(3, dto.getReview_id()); // 리뷰 아이디
			pstmt.setInt(4, dto.getProduct_id()); // 상품 아이디

			result = pstmt.executeUpdate();

			pstmt.close();

		} catch (Exception e) {
			System.out.println("[리뷰를 수정할 수 없습니다.] " + e.toString());
		}

		// 내용 없을 시 다음으로 안 넘어가는 부분 추가
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
			}
		}

		return result;
	}

	// 3. 리뷰 삭제 (deleteReview)
	public int deleteReview(String review_id, int product_id) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql;

		try {
			conn = DBConn.getConnection();
			sql = "delete from review where review_id = ? and product_id=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, review_id);
			pstmt.setInt(2, product_id);

			result = pstmt.executeUpdate();

			pstmt.close();

		} catch (Exception e) {
			System.out.println("[리뷰를 삭제할 수 없습니다.] " + e.toString());
		}

		// 내용 없을 시 다음으로 안 넘어가는 부분 추가

		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
			}
		}

		return result;
	}

	// 4. 전체 리뷰 가져오기 (getAllReviews)
	public List<ReviewDTO> getAllReviews() {
		List<ReviewDTO> lists = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			conn = DBConn.getConnection();
			sql = "select review_id, product_id, product, review from review";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ReviewDTO dto = new ReviewDTO();

				dto.setReview_id(rs.getString("review_id"));
				dto.setProduct_id(rs.getInt("product_id"));
				dto.setProduct(rs.getString("product"));
				dto.setReview(rs.getString("review"));

				lists.add(dto);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println("[전체 리뷰 조회에 실패했습니다.] " + e.toString());
		}

		// 내용 없을 시 다음으로 안 넘어가는 부분 추가
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
			}
		}

		return lists;
	}

	// 5. 상품 코드로 리뷰 검색 (getReviewsByProductId)
	public List<ReviewDTO> getReviewsByProductId(int product_id) {
		List<ReviewDTO> lists = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			conn = DBConn.getConnection();
			sql = "select review_id, product_id, product, review from review where product_id = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, product_id);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ReviewDTO dto = new ReviewDTO();

				dto.setReview_id(rs.getString("review_id"));
				dto.setProduct_id(rs.getInt("product_id"));
				dto.setProduct(rs.getString("product"));
				dto.setReview(rs.getString("review"));

				lists.add(dto);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println("[상품 코드로 리뷰 조회에 실패했습니다.] " + e.toString());
		}

		// 내용 없을 시 다음으로 안 넘어가는 부분 추가
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
			}
		}

		return lists;
	}

	// 상품코드로 상품명 조회
	public String getProductNameById(int product_id) {
		String productName = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			conn = DBConn.getConnection();
			sql = "select name from product where product_id = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, product_id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				productName = rs.getString("name");
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println("[해당 이름의 상품이 없습니다.] " + e.toString());
		}

		// 내용 없을 시 다음으로 안 넘어가는 부분 추가
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
			}
		}

		return productName;
	}

	// 상품명으로 상품코드 조회
	public int getProductIdByName(String productName) {
		int productId = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			conn = DBConn.getConnection();
			sql = "select product_id from product where name = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, productName);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				productId = rs.getInt("product_id");
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println("[상품코드가 맞지 않습니다.] " + e.toString());
		}

		// 내용 없을 시 다음으로 안 넘어가는 부분 추가
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
			}
		}

		return productId;
	}

	// 동일 id, 동일 상품으로 작성한 리뷰가 있는지 확인
	public boolean existsReview(String reviewId, int productId) {
		boolean exists = false;
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM REVIEW WHERE review_id = ? AND product_id = ?";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reviewId);
			pstmt.setInt(2, productId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				exists = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return exists;
	}

	// id와 pw 일치하는지 확인
	public boolean checkLogin(String id, String pw) {
		String sql = "SELECT COUNT(*) FROM MEMBER WHERE id=? AND pw=?";

		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return false;
	}

	// 삭제 시 상품코드가 아닌 상품명을 입력했을 경우
	public int deleteReview(String review_id, String product) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql;

		try {
			conn = DBConn.getConnection();
			sql = "delete from review where review_id = ? and product=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, review_id);
			pstmt.setString(2, product);

			result = pstmt.executeUpdate();

			pstmt.close();

		} catch (Exception e) {
			System.out.println("[리뷰를 삭제할 수 없습니다.] " + e.toString());
		}

		// 내용 없을 시 다음으로 안 넘어가는 부분 추가

		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
			}
		}

		return result;
	}

}
