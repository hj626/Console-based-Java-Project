package shop.main;

import java.util.Scanner;

import shop.discount.Discount;
import shop.discount.DiscountMain;
import shop.member.MemberMain;
import shop.product.Product;
import shop.review.ReviewMain;

public class ShopMain {

	public static void main(String[] args) {	
		
		Scanner sc = new Scanner(System.in);
		int ch1, ch2;
		
		Discount ob = new Discount();
		
		while (true) {
			do {
				printMainMenu();
				System.out.println();
				System.out.print("선택: ");
                
//				System.out.print("1.회원관리 2.상품관리 3.리뷰관리 4.할인관리 0.종료");
				ch1 = sc.nextInt();
				
//				clearConsole();
			} while(ch1<0||ch1>4);
			
			switch (ch1) {
			case 1:
				MemberMain.run();
				break;
			case 2:
				Product.run();
				break;
			case 3:
				ReviewMain.run();
				break;
			case 4:
				DiscountMain.run();
				break;
			default:
				System.exit(0);
				break;
			}
		}
	
	}

	//console 창 정리(된 것처럼 보이게 하기)
//	public static void clearConsole() {
//	    for (int i = 0; i < 50; i++) {
//	        System.out.println();
//	    }
//	}

	
	
//	public static void printMainMenu() {
//	    final String[] art = {
//	        "       @@@@@@@@@@@@&                                                             ",
//	        "     /@@(         &@@.                                                           ",
//	        "      @@@@@@@@@@.  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@&      ",
//	        "              @@@   @@@                                                 @@@     ",
//	        "               @@@   @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@     ",
//	        "               ,@@&  #@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@      ",
//	        "                @@@*  @@@                                            .@@@        ",
//	        "                 @@@   @@@  @@@@@#  *@@@@@@@   @@@@@@@,  &@@@@@*     @@@         ",
//	        "                  @@@  .@@&                                        .@@@          ",
//	        "                  ,@@&  %@@/ .@@@@*  #@@@@@@   @@@@@@#  (@@@@&    ,@@@           ",
//	        "                   &@@*  @@@                                     .@@@            ",
//	        "                    @@@   @@@                                   .@@@             ",
//	        "                     @@@   &@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@/              ",
//	        "                     ,@@@                                                        ",
//	        "                      #@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                 ",
//	        "                         .****,,**********************,****,***.                 ",
//	        "                          @@@@@@@@.                  @@@@@@@@%                   ",
//	        "                         @@@    @@@.                @@@     @@%                  ",
//	        "                         @@@%  (@@@                 *@@@   @@@.                  ",
//	        "                           @@@@@@                     /@@@@@,                   ",
//	        "                                                                                 "
//	    };
//
//	    final int maxShift = 30;  // 오른쪽으로 이동할 최대 칸 수
//	    final int delay = 50;     // 밀릴 때마다 대기 시간 (ms)
//
//	    Thread moveThread = new Thread(new Runnable() {
//	        public void run() {
//	            for (int shift = 0; shift <= maxShift; shift++) {
//	                clearConsole(); // 이전 출력 지우기 효과
//	                for (int i = 0; i < art.length; i++) {
//	                    System.out.println(spaces(shift) + art[i]);
//	                }
//
//	                // 메뉴는 한 번만 출력 (고정 위치)
//	                if (shift == maxShift) {
//	                    System.out.println("\n===========================================");
//	                    System.out.println("         WELCOME TO SHOPPING MALL ");
//	                    System.out.println("===========================================\n");
//	                    System.out.println("  [1] 회원 관리");
//	                    System.out.println("  [2] 상품 관리");
//	                    System.out.println("  [3] 리뷰 관리");
//	                    System.out.println("  [4] 할인 관리");
//	                    System.out.println("  [0] 종료");
//	                }
//
//	                try {
//	                    Thread.sleep(delay);
//	                } catch (InterruptedException e) {
//	                    System.out.println("출력 중 인터럽트 발생");
//	                }
//	            }
//	        }
//	    });
//
//	    moveThread.start();
//	    try {
//	        moveThread.join(); // 출력 끝날 때까지 대기
//	    } catch (InterruptedException e) {
//	        System.out.println("메인 스레드 인터럽트");
//	    }
//	}
//
//	// 공백 생성 함수
//	private static String spaces(int count) {
//	    StringBuilder sb = new StringBuilder();
//	    for (int i = 0; i < count; i++) {
//	        sb.append(' ');
//	    }
//	    return sb.toString();
//	}
//
//	// 콘솔 초기화 흉내 (줄바꿈으로 덮기)
//	private static void clearConsole() {
//	    for (int i = 0; i < 50; i++) {
//	        System.out.println();
//	    }
//	}


	
//	public static void printMainMenu() {
//        System.out.println("\n===========================================");
//        System.out.println("         WELCOME TO SHOPPING MALL ");
//        System.out.println("===========================================");
//        System.out.println();
//        System.out.println("  [1] 회원 관리");
//        System.out.println("  [2] 상품 관리");
//        System.out.println("  [3] 리뷰 관리");
//        System.out.println("  [4] 할인 관리");
//        System.out.println("  [0] 종료");
//        
//	}

	public static void printMainMenu() {
	    String[] lines = {
	        "\n===========================================",
	        "         WELCOME TO SHOPPING MALL ",
	        "===========================================",
	        "",
	        "  [1] 회원 관리",
	        "  [2] 상품 관리",
	        "  [3] 리뷰 관리",
	        "  [4] 할인 관리",
	        "  [0] 종료"
	    };

	    for (String line : lines) {
	        print1(line);
	    }
	}

	// 한 글자씩 출력하는 메서드
	public static void print1(String text) {
		
	    for (char ch : text.toCharArray()) {
	        System.out.print(ch);
	        try {
	            Thread.sleep(10); 
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	    System.out.println();
	}

}
