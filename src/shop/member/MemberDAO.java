package shop.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import shop.db.DBConn;

public class MemberDAO {
	// --인서트 - 회원추가
	// - 회원 등급 수정
	// - 회원탈퇴
	// - "회원검색 : 회원정보, 구매상품"

	public int insertData(MemberDTO dto) {

		int result = 0;
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;

		String sql;

		try {
			sql = "INSERT INTO MEMBER VALUES (?,?, ?,?,SYSDATE)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getId());
			pstmt.setString(2, dto.getPw());
			pstmt.setString(3, dto.getName());
			pstmt.setString(4, dto.getTel());

			result = pstmt.executeUpdate();
			pstmt.close();

		} catch (Exception e) {
			// System.out.println(e.toString());
		}
		return result;
	}

	// -------
	public int updateData(MemberDTO dto) {

		int result = 0;
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;

		String sql;

		try {
			sql = "UPDATE member SET PW=?, NAME=?, TEL=? where ID=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getPw());
			pstmt.setString(2, dto.getName());
			pstmt.setString(3, dto.getTel());
			pstmt.setString(4, dto.getId());

			result = pstmt.executeUpdate();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}

	// ------셀렉트
	public MemberDTO selectData(String id) {

		MemberDTO dto = null;
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql;
		try {
			sql = "SELECT id,pw,name,tel,reg_date FROM MEMBER WHERE ID=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, id);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new MemberDTO();

				dto.setId(rs.getString("id"));
				dto.setPw(rs.getString("pw"));
				dto.setName(rs.getString("name"));
				dto.setTel(rs.getString("tel"));
				dto.setReg_date(rs.getDate("reg_date"));
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());

		}
		return dto;
	}

	// ------셀렉트 +추가(회원전체명단보기)
	public List<MemberDTO> getAllMember() {

		List<MemberDTO> lists = new ArrayList<>();
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql;
		try {
			sql = "SELECT id,pw,name,tel,reg_date FROM MEMBER";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				dto = new MemberDTO();

				dto.setId(rs.getString("id"));
				dto.setPw(rs.getString("pw"));
				dto.setName(rs.getString("name"));
				dto.setTel(rs.getString("tel"));
				dto.setReg_date(rs.getDate("reg_date"));

				lists.add(dto);
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());

		}
		return lists;
	}
//------------------------여기까지 추가된부분

//------딜리트
	public int deleteData(String id) {

		int result = 0;
		Connection conn = DBConn.getConnection();
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM MEMBER WHERE ID=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			result = pstmt.executeUpdate();
			pstmt.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return result;
	}
}