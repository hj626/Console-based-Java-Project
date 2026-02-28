package shop.product;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import shop.review.ReviewDTO;

public class Product {

	// public static void main(String[] args) {
	// 리턴값 받을려고 맨위로 올려서 run넣었어용
	// run();
	// }

	public static void run() { // 이렇게 수정했습니다.

		Scanner sc = new Scanner(System.in);
		ProductDAO dao = new ProductDAO();

		while (true) {
			System.out.println("\n[ 상품 관리 시스템 ]");
			System.out.println("  [1] 상품 등록");
			System.out.println("  [2] 상품 전체 조회");
			System.out.println("  [3] 상품 검색 (상품번호)");
			System.out.println("  [4] 상품 수정");
			System.out.println("  [5] 상품 삭제");
			System.out.println("  [6] 상품명 키워드 검색");
			System.out.println("  [7] 품절 임박 상품 보기");
			System.out.println("  [0] 초기 메뉴 복귀");
			System.out.print("선택: ");

			// 상품 아이디
			String choice = sc.nextLine();

			if (choice.equals("1")) {

				System.out.println();
				System.out.println("[신규 상품 등록]");
				System.out.println();
				System.out.print("상품명: ");
				String name = sc.nextLine();
				System.out.print("상품설명: ");
				String desc = sc.nextLine();
				System.out.print("재고: ");
				int stock = Integer.parseInt(sc.nextLine());
				System.out.print("가격: ");
				int price = Integer.parseInt(sc.nextLine());

				// 등록일 자동 입력
				java.util.Date today = new java.util.Date();
				ProductDTO dto = new ProductDTO();
//				dto.setProduct_id(id);
				dto.setName(name);
				dto.setDescription(desc);
				dto.setStock(stock);
				dto.setPrice(price);
				dto.setReg_date(new Date(today.getTime()));

				int result = dao.insertProduct(dto);
				System.out.println(result > 0 ? "상품 등록 성공" : "상품 등록 실패");

				// 전체상품 목록
			} else if (choice.equals("2")) {
				List<ProductDTO> list = dao.getAllProducts();
				System.out.println("[ 전체 상품 목록 ]");
				for (ProductDTO dto : list) {
					System.out.printf("ID: %d | 이름: %s | 설명: %s | 재고: %d | 정가: %d | 할인율: %d%% | 할인가: %d | 등록일: %s\n",
							dto.getProduct_id(), dto.getName(), dto.getDescription(), dto.getStock(), dto.getPrice(),
							dto.getDiscountRate(), dto.getDiscountedPrice(), dto.getReg_date().toString());
				}
				// 검색할 아이디
			} else if (choice.equals("3")) {
				System.out.print("검색할 상품 ID: ");
				int id = Integer.parseInt(sc.nextLine());

				// ProductDAO에서 상품 + 리뷰 같이 조회하는 메서드 사용추가합니다!
				Map<String, Object> data = dao.getProductWithReviews(id);
				ProductDTO product = (ProductDTO) data.get("product");
				@SuppressWarnings("unchecked")
				List<ReviewDTO> reviews = (List<ReviewDTO>) data.get("reviews");

				if (product != null) {
					System.out.printf("ID: %d | 이름: %s | 설명: %s | 재고: %d | 정가: %d | 할인율: %d%% | 할인가: %d | 등록일: %s\n",
							product.getProduct_id(), product.getName(), product.getDescription(), product.getStock(),
							product.getPrice(), product.getDiscountRate(), product.getDiscountedPrice(),
							product.getReg_date().toString());

					System.out.println("MD 리뷰: ");
					if (reviews.isEmpty()) {
						System.out.println("리뷰가 없습니다.");
					} else {
						for (ReviewDTO r : reviews) {
							System.out.println("  - " + r.getReview());
						}
					}
				} else {
					System.out.println("해당 상품이 없습니다.");
				}
				// ------ 끝 -----

				// 수정할 상품
			} else if (choice.equals("4")) {
				System.out.print("수정할 상품 ID: ");
				int id = Integer.parseInt(sc.nextLine());
				ProductDTO old = dao.getProductById(id);

				if (old == null) {
					System.out.println("존재하지 않는 상품입니다.");
					continue;
				}
				System.out.println(old.toString()); // 검색상품 출력

//--여기서부터 기능 추가 수정목록 골라서 진행
				System.out.println("수정할 항목을 골라주세요\n" + "  [1] 상품명\n  [2] 상품설명\n  [3] 재고\n  [4] 정가\n  [0] 이전메뉴");
				int num = Integer.parseInt(sc.nextLine());

				switch (num) {
				case 1:
					System.out.print("수정할 이름: ");
					String name = sc.nextLine();
					old.setName(name);
					break;

				case 2:
					System.out.print("수정할 설명: ");
					String desc = sc.nextLine();
					old.setDescription(desc);
					break;

				case 3:
					System.out.print("수정 후 재고: ");
					int stock = Integer.parseInt(sc.nextLine());
					old.setStock(stock);
					break;

				case 4:
					System.out.print("수정 후 가격: ");
					int pri = Integer.parseInt(sc.nextLine());
					old.setPrice(pri);
					break;

				default:

					continue;

				}
				int result = dao.updateProduct(old, num);
				System.out.println(result > 0 ? "수정 성공" : "수정 실패");

				// - 삭제할 상품
			} else if (choice.equals("5")) {
				System.out.print("삭제할 상품 ID: ");
				int id = Integer.parseInt(sc.nextLine());
				int result = dao.deleteProduct(id);
				System.out.println(result > 0 ? "삭제 성공" : "삭제 실패");
				// -- 키워드 입력
			} else if (choice.equals("6")) {
				System.out.print("검색할 키워드 입력: ");
				String keyword = sc.nextLine();
				List<ProductDTO> list = dao.searchProductsByName(keyword);

				if (list.isEmpty()) {
					System.out.println("해당 키워드를 포함하는 상품이 없습니다.");
				} else {
					System.out.println("[ 키워드 검색 결과 ]");
					for (ProductDTO dto : list) {
						System.out.printf(
								"ID: %d | 이름: %s | 설명: %s | 재고: %d | 정가: %d | 할인율: %d%% | 할인가: %d | 등록일: %s\n",
								dto.getProduct_id(), dto.getName(), dto.getDescription(), dto.getStock(),
								dto.getPrice(), dto.getDiscountRate(), dto.getDiscountedPrice(),
								dto.getReg_date().toString());
					}

				}

				// ------------------------------------------
			} else if (choice.equals("7")) {

				List<ProductDTO> list = dao.getRowStock();

				if (list.isEmpty()) {
					System.out.println("품절 임박 상품이 없습니다.");

				} else {
					System.out.println("[품절 임박] 상품목록 ");
					for (ProductDTO dto : list) {
						System.out.printf(
								"ID: %d | 이름: %s | 설명: %s | 재고: %d | 정가: %d | 할인율: %d%% | 할인가: %d | 등록일: %s\n",
								dto.getProduct_id(), dto.getName(), dto.getDescription(), dto.getStock(),
								dto.getPrice(), dto.getDiscountRate(), dto.getDiscountedPrice(),
								dto.getReg_date().toString());

					}
				}
				// --------------------------- 초기메뉴로 돌아가는~
			} else if (choice.equals("0")) {
				System.out.println("상위 메뉴로 돌아갑니다.");
				return; // 상위메뉴(우리 큰 메인 초기)로 돌아가게 break -> return으로 수정함.
			} else {
				System.out.println("잘못된 입력입니다.");

			}
		}
	}
}

//}
