package shop.discount;

import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Discount {

	Scanner sc = new Scanner(System.in);
	DiscountDAO dao = new DiscountDAO();

	// 추가
	public void insert() {

		DiscountDTO dto = new DiscountDTO();

		int pid;
		try {
			System.out.print("할인을 적용할 상품 ID는? ");
			pid = sc.nextInt();
		} catch (InputMismatchException e) { // product_id 컬럼의 자료형과 다른 값을 입력했을 때 발생하는 예외
			System.out.println("숫자로 입력해주세요. 할인 관리 페이지로 돌아갑니다.");
			sc.nextLine(); // 버퍼 비우기
			return;
		}

		if (dao.isProductExists(pid) == false) {
			System.out.println("존재하지 않은 상품 ID입니다. 할인 관리 페이지로 돌아갑니다.");
			return;
		}

		if (dao.isDiscountExists(pid)) {
			System.out.println("이미 할인율이 등록된 상품입니다. 수정하시겠습니까? (1: 예, 2: 아니오)");
			int sel = sc.nextInt();
			if (sel == 1) {
				System.out.print("수정할 할인율은? ");
				int newRate = sc.nextInt();
				if (newRate < 0 || newRate > 100) {
					System.out.println("0에서 100 사이의 값을 입력해주세요. ");
					return;
				}

				dto.setProduct_id(pid);
				dto.setDiscount_rate(newRate);

				int result = dao.updateDiscountRate(dto);

				if (result != 0) {
					System.out.println("할인율 수정 완료!");
				} else {
					System.out.println("수정 실패!");
				}
			} else {
				System.out.println("등록을 취소합니다.");
			}
		} else {
			dto.setProduct_id(pid);
			System.out.print("적용할 할인율은? ");
			dto.setDiscount_rate(sc.nextInt());
			if (dto.getDiscount_rate() < 0 || dto.getDiscount_rate() > 100) {
				System.out.println("0에서 100 사이의 값을 입력해주세요. ");
				return;
			}
			int result = dao.insertDiscountRate(dto);
			if (result > 0) {
				System.out.println("할인율 등록 성공!");
			} else {
				System.out.println("할인율 등록 실패!");
			}
		}
	}

	// 수정
	public void update() {

		DiscountDTO dto = new DiscountDTO();

//		System.out.print("할인율 변경 시킬 상품 ID 입력. ");
//		dto.setProduct_id(sc.nextInt());

		try {
			System.out.print("할인을 수정할 상품 ID는? ");
			dto.setProduct_id(sc.nextInt());
		} catch (InputMismatchException e) { // product_id 컬럼의 자료형과 다른 값을 입력했을 때 발생하는 예외
			System.out.println("숫자로 입력해주세요. 할인 관리 페이지로 돌아갑니다.");
			sc.nextLine(); // 버퍼 비우기
			return;
		}

		if (dao.isProductExists(dto.getProduct_id()) == false) {
			System.out.println("존재하지 않은 상품 ID입니다. 할인 관리 페이지로 돌아갑니다.");
			return;
		}

		System.out.print("바뀐 할인율 입력. ");
		dto.setDiscount_rate(sc.nextInt());
		if (dto.getDiscount_rate() < 0 || dto.getDiscount_rate() > 100) {
			System.out.println("0에서 100 사이의 값을 입력해주세요. ");
			return;
		}

		int result = dao.updateDiscountRate(dto);

		if (result != 0) {
			System.out.println("할인율 수정 성공!!");
		} else {
			System.out.println("할인율 수정 실패!!");
		}

	}

	// 삭제
	public void delete() {

//		int product_id;
		DiscountDTO dto = new DiscountDTO();

//		System.out.print("할인 행사를 취소할 상품.");
//		product_id = sc.nextInt();

		try {
			System.out.print("할인을 삭제할 상품 ID는? ");
			dto.setProduct_id(sc.nextInt());
		} catch (InputMismatchException e) { // product_id 컬럼의 자료형과 다른 값을 입력했을 때 발생하는 예외
			System.out.println("숫자로 입력해주세요. 할인 관리 페이지로 돌아갑니다.");
			sc.nextLine(); // 버퍼 비우기
			return;
		}

		if (dao.isProductExists(dto.getProduct_id()) == false) {
			System.out.println("존재하지 않은 상품 ID입니다. 할인 관리 페이지로 돌아갑니다.");
			return;
		}

		int result = dao.deleteDiscountRate(dto.getProduct_id());

		if (result != 0) {
			System.out.println("할인율 삭제 성공!!");
		} else {
			System.out.println("할인율 삭제 실패!!");
		}
	}

	// 전체 출력
	public void selectAll() {

		List<DiscountDTO> lists = dao.getLists();

		// 할인 목록이 비어 있으면 안내 메시지 출력
		if (lists == null || lists.isEmpty()) {
			System.out.println("현재 할인하는 상품이 없습니다.");
			return;
		}

		Iterator<DiscountDTO> it = lists.iterator();

		while (it.hasNext()) {
			DiscountDTO dto = it.next();

			System.out.println(dto.toString()); // dto 안에는 출력기가 있기 때문에 dto만 써도 됨
		}

	}
}
