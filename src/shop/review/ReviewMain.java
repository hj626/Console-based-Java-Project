package shop.review;

import java.util.Scanner;

public class ReviewMain {

	// public static void main(String[] args)
	public static void run() {

		Scanner sc = new Scanner(System.in);
		Review review = new Review();
		int ch;

		while (true) {
			System.out.println("\n[ 리뷰 관리 시스템 ]");
			System.out.println("  [1] 리뷰 작성");
			System.out.println("  [2] 리뷰 수정");
			System.out.println("  [3] 리뷰 삭제");
			System.out.println("  [4] 모든 리뷰 조회");
			System.out.println("  [5] 상품코드 혹은 상품명으로 리뷰 조회");
			System.out.println("  [0] 초기 메뉴 복귀");
			System.out.print("선택: ");
			ch = sc.nextInt();

			switch (ch) {
			case 1:
				review.insert(); // 리뷰 작성 (종료 없음, 작업 후 메뉴로)
				break;
			case 2:
				review.update();
				break;
			case 3:
				review.delete();
				break;
			case 4:
				review.selectAll();
				break;
			case 5:
				review.selectByProductId();
				break;
			case 0:
				System.out.println("초기 메뉴로 돌아갑니다.");
				return;
			}
		}
	}
}
