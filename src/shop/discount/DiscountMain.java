package shop.discount;

import java.util.Scanner;

public class DiscountMain {

	public static void run() {
//	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		int ch;

		Discount ob = new Discount();

		while (true) {
			do {
				System.out.println("\n[ 할인 관리 시스템 ]");
				System.out.println("  [1] 할인율 등록");
				System.out.println("  [2] 할인율 수정");
				System.out.println("  [3] 할인 삭제");
				System.out.println("  [4] 할인 목록 열람");
				System.out.println("  [0] 초기 메뉴 복귀");
				System.out.print("선택: ");
				ch = sc.nextInt();
			} while (ch < 0 || ch > 4);

			switch (ch) {
			case 1:
				ob.insert();
				break;
			case 2:
				ob.update();
				break;
			case 3:
				ob.delete();
				break;
			case 4:
				ob.selectAll();
				break;
			default:
				// System.exit(0);
				System.out.println("초기 메뉴로 돌아갑니다.");
				return;
			}
		}

	}

}
