//package shop.product;
//
//import java.sql.Date;
//import java.util.List;
//import java.util.Scanner;
//
//public class Product1 {
//
//	public static void run() {
//
////		public static void main(String[] args) {
//
//			Scanner sc = new Scanner(System.in);
//			ProductDAO dao = new ProductDAO();
//
//			while (true) {
//				System.out.println("\n[ 상품 관리 시스템 ]");
//				System.out.println("1. 상품 등록");
//				System.out.println("2. 상품 전체 조회");
//				System.out.println("3. 상품 검색 (상품번호)");
//				System.out.println("4. 상품 수정");
//				System.out.println("5. 상품 삭제");
//				System.out.println("0. 초기메뉴");
//				System.out.print("선택: ");
//
//				String choice = sc.nextLine();
//
//				if (choice.equals("1")) {
//					System.out.print("상품 ID: ");
//					int id = Integer.parseInt(sc.nextLine());
//					System.out.print("상품명: ");
//					String name = sc.nextLine();
//					System.out.print("상품설명: ");
//					String desc = sc.nextLine();
//					System.out.print("재고: ");
//					int stock = Integer.parseInt(sc.nextLine());
//
//					// 등록일 자동 입력
//					java.util.Date today = new java.util.Date();
//					ProductDTO dto = new ProductDTO();
//					dto.setProduct_id(id);
//					dto.setName(name);
//					dto.setDescription(desc);
//					dto.setStock(stock);
//					dto.setReg_date(new Date(today.getTime()));
//
//					int result = dao.insertProduct(dto);
//					System.out.println(result > 0 ? "상품 등록 성공" : "상품 등록 실패");
//
//				} else if (choice.equals("2")) {
//					List<ProductDTO> list = dao.getDiscountedProductList();
//					System.out.println("[ 전체 상품 목록 ]");
//					for (ProductDTO dto : list) {
//						System.out.printf("ID: %d | 이름: %s | 설명: %s | 재고: %d | 정가: %d | 할인율: %d%% | 할인가: %d | 등록일: %s\n",
//								dto.getProduct_id(), dto.getName(), dto.getDescription(),
//								dto.getStock(), dto.getPrice(), dto.getDiscountRate(), dto.getDiscountedPrice(), dto.getReg_date().toString());
//					}
//
//				} else if (choice.equals("3")) {
//					System.out.print("검색할 상품 ID: ");
//					int id = Integer.parseInt(sc.nextLine());
//					ProductDTO dto = dao.getProductById(id);
//					if (dto != null) {
//						System.out.printf("ID: %d | 이름: %s | 설명: %s | 재고: %d | 정가: %d | 할인율: %d%% | 할인가: %d | 등록일: %s\n",
//								dto.getProduct_id(), dto.getName(), dto.getDescription(),
//								dto.getStock(), dto.getPrice(), dto.getDiscountRate(), dto.getDiscountedPrice(), dto.getReg_date().toString());
//					} else {
//						System.out.println("해당 상품이 없습니다.");
//					}
//
//				} else if (choice.equals("4")) {
//					System.out.print("수정할 상품 ID: ");
//					int id = Integer.parseInt(sc.nextLine());
//					ProductDTO old = dao.getProductById(id);
//					if (old == null) {
//						System.out.println("존재하지 않는 상품입니다.");
//						continue;
//					}
//
//					System.out.print("새 이름: ");
//					String name = sc.nextLine();
//					System.out.print("새 설명: ");
//					String desc = sc.nextLine();
//					System.out.print("새 재고: ");
//					int stock = Integer.parseInt(sc.nextLine());
//
//					old.setName(name);
//					old.setDescription(desc);
//					old.setStock(stock);
//
//					int result = dao.updateProduct(old);
//					System.out.println(result > 0 ? "수정 성공" : "수정 실패");
//
//				} else if (choice.equals("5")) {
//					System.out.print("삭제할 상품 ID: ");
//					int id = Integer.parseInt(sc.nextLine());
//					int result = dao.deleteProduct(id);
//					System.out.println(result > 0 ? "삭제 성공" : "삭제 실패");
//
//				} else if (choice.equals("0")) {
//					System.out.println("초기 메뉴로 돌아갑니다.");
//					return;
//				} else {
//					System.out.println("잘못된 입력입니다.");
//				}
//			}
//		}
//	}
////}
