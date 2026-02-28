package shop.member;

import java.util.Scanner;

public class MemberMain {
//public static void main(String[] args) {
	public static void run() {

		Scanner sc = new Scanner(System.in);
		int ch;

		Member mb = new Member();

		while (true) {
			do {
				System.out.println("\n[ 회원 관리 시스템 ]");
				System.out.println("  [1] 회원 추가");
				System.out.println("  [2] 회원 정보 수정");
				System.out.println("  [3] 회원 검색 (회원ID)");
				System.out.println("  [4] 회원 전체명단 보기");// 추가
				System.out.println("  [5] 강제 탈퇴");
				System.out.println("  [0] 초기 메뉴 복귀");
				System.out.print("선택: ");
				ch = sc.nextInt();

			} while (ch < 0 || ch > 5);

			switch (ch) {
			case 1:
				mb.insert();
				break; // 회원추가
			case 2:
				mb.update();
				break; // 회원정보수정
			case 3:
				mb.select();
				break; // 회원id검색
			case 4:
				mb.memberList();
				break; // 회원전체명단보기 추가
			case 5:
				mb.delete();
				break; // 회원탈퇴 번호 수정

			default:
				System.out.println("초기 메뉴로 돌아갑니다.");
				return;
			// System.exit(0);
			// break;
			}
		}
	}
}