package shop.review;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Review {

	Scanner sc = new Scanner(System.in);
	ReviewDAO dao = new ReviewDAO(); // ReviewDAO 객체 생성

	// 1. 리뷰 추가
	public void insert() {
		ReviewDTO dto = new ReviewDTO();

		System.out.print("아이디를 입력하세요.");
		String inputId = sc.next();

//		// 아이디 존재 여부 체크
//		if (!dao.isMemberIdExist(inputId)) {
//			System.out.println("존재하지 않는 아이디입니다. 초기화면으로 돌아갑니다.");
//			return; // 존재하지 않으면 종료
//		}
		// 어차피 비밀번호까지 입력 받은 후에 일치하는지 확인하므로 필요 없음
		dto.setReview_id(inputId);

		System.out.print("비밀번호를 입력하세요.");
		String inputPw = sc.next();

		// id와 pw 일치하는지 확인
		boolean isValid = dao.checkLogin(inputId, inputPw);

		if (!isValid) {
			System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
		} else {
			System.out.print("상품명 또는 상품코드를 입력하세요.");
			String input = sc.next(); // 코드(숫자)나 문자(이름) 중 아무거나 입력할 수 있게 자료형을 String로 바꿈.
			sc.nextLine(); // 버퍼는 꼭 비우고요.(내용 비운거임)

			try { // 숫자인 경우 → 상품코드로 간주

				int product_id = Integer.parseInt(input);
				dto.setProduct_id(product_id);
				dto.setProduct(dao.getProductNameById(product_id));

				if (dto.getProduct() == null) {
					System.out.println("해당 상품 또는 상품코드가 존재하지 않습니다.");
					return; // 존재하지 않을 시 다음으로 넘어가지 않고 종료함.
				}

			} catch (NumberFormatException e) {

				// 문자인 경우 → 상품명으로 간주
				String product = input;
				dto.setProduct(product);
				dto.setProduct_id(dao.getProductIdByName(product));

				if (dto.getProduct_id() == 0) {
					System.out.println("해당 상품명이 존재하지 않습니다.");
					return; // 존재하지 않을 시 다음으로 넘어가지 않고 종료함.
				}

			}

			// 동일 id와 상품에 작성한 리뷰가 있으면 수정
			if (dao.existsReview(dto.getReview_id(), dto.getProduct_id())) {
				System.out.print("이미 이 상품에 리뷰를 작성한 적이 있습니다. 수정하시겠습니까? [Y/N]: ");
				String answer = sc.nextLine();
				if (answer.equalsIgnoreCase("Y")) {
					System.out.print("새 리뷰 내용을 입력하세요: ");
					dto.setReview(sc.nextLine());

					int result = dao.updateReview(dto); // 수정
					if (result != 0) {
						System.out.println("리뷰가 수정되었습니다.");
					} else {
						System.out.println("리뷰 수정에 실패했습니다.");
					}
				} else {
					System.out.println("리뷰 작성이 취소되었습니다.");
				}
				return;
			}

			System.out.print("리뷰 내용을 입력하세요.");
			dto.setReview(sc.nextLine());

			int result = dao.insertReview(dto);

			if (result != 0) {
				System.out.println("리뷰가 등록되었습니다.");
			} else {
				System.out.println("리뷰가 등록되지 않았습니다.");
			}
		}

	}

	// 2. 리뷰 수정
	public void update() {
		ReviewDTO dto = new ReviewDTO();

		System.out.print("아이디를 입력하세요.");
		dto.setReview_id(sc.next());

		System.out.print("비밀번호를 입력하세요.");
		String inputPw = sc.next();

		boolean isValid = dao.checkLogin(dto.getReview_id(), inputPw);

		if (!isValid) {
			System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
		} else {
			System.out.print("상품명 또는 상품코드를 입력하세요.");
			String input = sc.next();
			sc.nextLine();

			try {

				int product_id = Integer.parseInt(input);
				dto.setProduct_id(product_id);
				dto.setProduct(dao.getProductNameById(product_id));

				if (dto.getProduct() == null) {
					System.out.println("해당 상품코드가 존재하지 않습니다.");
					return; // 예외 처리 후 메서드 종료
				}

			} catch (NumberFormatException e) {

				String product = input;
				dto.setProduct(product);
				dto.setProduct_id(dao.getProductIdByName(product));

				if (dto.getProduct_id() == 0) {
					System.out.println("해당 상품명이 존재하지 않습니다.");
					return; // 예외 처리 후 메서드 종료
				}

			}

			System.out.print("수정할 리뷰를 작성하세요. ");
			dto.setReview(sc.nextLine());

			int result = dao.updateReview(dto);

			if (result != 0) {
				System.out.println("리뷰가 수정되었습니다.");
			} else {
				System.out.println("리뷰 수정에 실패하였습니다.");
			}
		}
	}

	// 3. 리뷰 삭제
	public void delete() {

		ReviewDTO dto = new ReviewDTO();

		System.out.print("아이디를 입력하세요.");
		String review_id = sc.next();

		System.out.print("비밀번호를 입력하세요.");
		String inputPw = sc.next();

		// id와 pw 일치하는지 확인
		boolean isValid = dao.checkLogin(review_id, inputPw);

		System.out.print("삭제할 상품명 또는 상품코드를 입력하세요.");
		String product_id = sc.next(); // 코드(숫자)나 문자(이름) 중 아무거나 입력할 수 있게 자료형을 String로 바꿈.
		sc.nextLine(); // 버퍼는 꼭 비우고요.(내용 비운거임)

		try { // 숫자인 경우 → 상품코드로 간주

			int product_id1 = Integer.parseInt(product_id);
			dto.setProduct_id(product_id1);
			dto.setProduct(dao.getProductNameById(product_id1));

			if (dto.getProduct() == null) {
				System.out.println("해당 상품 또는 상품코드가 존재하지 않습니다.");
				return; // 존재하지 않을 시 다음으로 넘어가지 않고 종료함.
			}

			if (!isValid) {
				System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
			} else {
				int result = dao.deleteReview(review_id, product_id1);

				if (result != 0) {
					System.out.println("리뷰가 삭제되었습니다.");
				} else {
					System.out.println("해당아이디로 삭제할 리뷰가 없습니다.");
				}
			}

		} catch (NumberFormatException e) {

			// 문자인 경우 → 상품명으로 간주
			String product = product_id;
			dto.setProduct(product);
			dto.setProduct_id(dao.getProductIdByName(product));

			if (dto.getProduct_id() == 0) {
				System.out.println("해당 상품명이 존재하지 않습니다.");
				return; // 존재하지 않을 시 다음으로 넘어가지 않고 종료함.
			}

			boolean isValid1 = dao.checkLogin(review_id, inputPw);

			if (!isValid1) {
				System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
			} else {
				int result = dao.deleteReview(review_id, product_id);

				if (result != 0) {
					System.out.println("리뷰가 삭제되었습니다.");
				} else {
					System.out.println("해당아이디로 삭제할 리뷰가 없습니다.");
				}
			}
		}
	}

	// 4. 전체 리뷰 조회
	public void selectAll() {
		List<ReviewDTO> lists = dao.getAllReviews();
		Iterator<ReviewDTO> it = lists.iterator();

		if (!it.hasNext()) {
			System.out.println("현재 등록된 리뷰가 없습니다.");
			return;
		}

		while (it.hasNext()) {
			ReviewDTO dto = it.next();
			System.out.println(dto.toString());
		}
	}

	// 5. 상품 코드로 리뷰 조회
	public void selectByProductId() {
		System.out.print("조회할 상품명 또는 상품코드를 입력하세요.");
		String input = sc.next();

		int product_id = 0;

		try {
			product_id = Integer.parseInt(input);

		} catch (NumberFormatException e) {

			product_id = dao.getProductIdByName(input);
		}

		if (product_id == 0) {
			System.out.println("해당 상품에 대한 정보가 없습니다.");
			return; // 예외 처리 후 메서드 종료
		}

		List<ReviewDTO> lists = dao.getReviewsByProductId(product_id);
		Iterator<ReviewDTO> it = lists.iterator();

		if (!it.hasNext()) {
			System.out.println("해당 상품에 리뷰가 없습니다.");
			return;
		}

		while (it.hasNext()) {
			ReviewDTO dto = it.next();
			System.out.println(dto.toString());
		}
	}
}
