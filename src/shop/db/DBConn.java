package shop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//jdbc
//Java Data Base Connected

public class DBConn {

	private static Connection dbConn;
	//db를 연결하는 코딩은 같으므로 연결자를 미리 static으로 만들어서 가져다 쓰기만 함
	//클래스변수 이므로 dbConn의 초기값은 null
	
	//db 연결
	public static Connection getConnection() {
		//가져다 쓰는 것이므로 get~으로 생성
		try {
			if(dbConn==null || dbConn.isClosed()) {
				
				try {
					
					String url = "jdbc:oracle:thin:@localhost:1521:xe";
					//hdbc 방식으로 oracle을 찾아가라.
					//thin : type4 형식으로
					//역시 내 ip는 172.0.0.1이나 localhost를 써도 됨
					//Oracle의 port number인 1521과 xe까지 작성
					String user = "game";
					String pwd = "maker";
							
							
					Class.forName("oracle.jdbc.driver.OracleDriver");
					//Class : Class라는 클래스는 다른 클래스의 정보를 읽어오는 역할을 함
					//forName을 통해 괄호 내부에 있는 클래스의 내용을 읽어옴
					
					dbConn = DriverManager.getConnection(url, user, pwd);
					//위의 값들을 dbConn에 넣어 합침
					//DB 연결방법의 1단계에 해당
					// ㄴ 1. DriverManager가 Connection을 생성
					
					
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dbConn;
		
	}
	
	//db 종료
	//db는 사용이 끝나면 항상 닫아줘야함
	public static void Close() {
		
		if(dbConn!=null) {
			try {
				
				if (!dbConn.isClosed()) {	//dbConn이 열려있다면
					dbConn.close();			//dbConn을 닫아라
				}
				
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
		
		dbConn = null;
		//연결을 종료하면 연결자가 갖고있던 것이 쓰레기값으로 dbConn에 남음
		//이것을 방치하면 다음 연결부터는 연결이 불가능
		//그래서 그 쓰레기값을 모두 없애는 코드가 필요함
		
	}
	
}






















