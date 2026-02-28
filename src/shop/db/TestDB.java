package shop.db;

import java.sql.Connection;

public class TestDB {

	public static void main(String[] args) {

		Connection conn = DBConn.getConnection();
		//DBConn을 가져오는 것
		//연결이 필요할 때마다 이 코드를 쓸 것
		
		if (conn==null) {
			System.out.println("데이터베이스 연결 실패!!");
			System.exit(0);
		}else {
			System.out.println("데이터베이스 연결 성공!!");
		}
		
		DBConn.Close();
	}

}
