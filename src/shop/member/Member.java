package shop.member;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import shop.review.ReviewDAO;

public class Member {

	Scanner sc = new Scanner(System.in);
	MemberDAO dao = new MemberDAO();

	// insert
	public void insert() {

		int result = 0;

		MemberDTO dto = new MemberDTO();

		System.out.print("ID: ");
		dto.setId(sc.next());

		System.out.print("PW: ");
		dto.setPw(sc.next());

		System.out.print("이름: ");
		dto.setName(sc.next());

		System.out.print("연락처: ");
		dto.setTel(sc.next());

		dto.setReg_date(new Date());

		result = dao.insertData(dto);

		if (result == 1) {
			System.out.println("회원 추가등록 되었습니다.");
		} else {
			System.out.println("회원 추가등록 실패. 다시 작성해주세요");
		}
	}
	// -------인서트 끝

	// ------업데이트 회원정보 수정
	public void update() {
		int result = 0;
		MemberDTO dto = new MemberDTO();
		ReviewDAO Rdao = new ReviewDAO();

		System.out.print("수정할 회원님의 ID를 입력하세요. ");
		dto.setId(sc.next());

		System.out.print("비밀번호를 입력하세요. ");
		dto.setPw(sc.next());

		// id와 pw 일치하는지 확인
		boolean isValid = Rdao.checkLogin(dto.getId(), dto.getPw());

		if (!isValid) {
			System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
			System.out.println("회원 관리 화면으로 복귀합니다. ");
		} else {
			dto = dao.selectData(dto.getId());

			Scanner sc = new Scanner(System.in);
			int ch;

			while (true) {
				do {
					System.out.println("  [1] 비밀번호 수정");
					System.out.println("  [2] 이름 수정");
					System.out.println("  [3] 전화번호 수정");
					System.out.println("  [0] 회원 관리 화면 복귀");
					System.out.print("선택: ");
					ch = sc.nextInt();

				} while (ch < 0 || ch > 5);

				switch (ch) {
				case 1:
					System.out.println("기존 비밀번호: ");
					String input = sc.next();
					if (!dto.getPw().equals(input)) {
						System.out.print("변경할 비밀번호: ");
						String input2 = sc.next();
						System.out.println("기존 비밀번호가 일치하지 않습니다. ");
					} else {
						System.out.print("변경할 비밀번호: ");
						dto.setPw(sc.next());
						dto.setReg_date(new Date());
						result = dao.updateData(dto);
						if (result == 1) {
							System.out.println("회원 정보 수정완료.");
						} else {
							System.out.println("회원 정보 수정실패. 다시 작성해주세요");
						}
					}
					break; // 비밀번호 수정
				case 2:
					System.out.print("변경할 이름: ");
					dto.setName(sc.next());
					dto.setReg_date(new Date());
					result = dao.updateData(dto);
					if (result == 1) {
						System.out.println("회원 정보 수정완료.");
					} else {
						System.out.println("회원 정보 수정실패. 다시 작성해주세요");
					}
					break; // 이름 수정
				case 3:
					System.out.print("변경할 연락처: ");
					dto.setTel(sc.next());
					dto.setReg_date(new Date());
					result = dao.updateData(dto);
					if (result == 1) {
						System.out.println("회원 정보 수정완료.");
					} else {
						System.out.println("회원 정보 수정실패. 다시 작성해주세요");
					}
					break; // 전화번호 수정
				default:
					System.out.println("초기 메뉴로 돌아갑니다.");
					return;
				// System.exit(0);
				// break;
				}
			}

		}

	}

//회원검색	
	public void select() {

		String id;
		MemberDTO dto = null;

		System.out.print("검색할 ID: ");

		id = sc.next();

		dto = dao.selectData(id);

		if (dto != null) {
			System.out.println(dto.toString());

			// --여기서부터 기능 추가 수정, 탈퇴, 이전메뉴
			System.out.println("  [1] 회원 정보 수정");
			System.out.println("  [2] 회원 탈퇴");
			System.out.println("  [0] 회원 관리 화면 복귀");
			System.out.print("선택: ");
			int su = sc.nextInt();
			int result = 0;

			switch (su) {
			case 1:
				update();
				break;

			case 2:
				while (result == 0) {
					result = dao.deleteData(id);

					if (result != 0) {
						System.out.println("회원님이 정상적으로 탈퇴되었습니다." + "\n\n회원관리 초기메뉴로 돌아갑니다.");
					} else {
						System.out.println("회원님의 탈퇴처리가 실패되었습니다." + "\n\n 다시 아이디를 입력해주세요");
					}
				}
				break;

			default:
				return;
			}

			// ---------
		} else {
			System.out.println("존재하지 않는 아이디입니다.");
		}
	}
	// ---------

//-----전체명단 검색 추가
	public void memberList() {

		List<MemberDTO> lists = dao.getAllMember();
		Iterator<MemberDTO> it = lists.iterator();

		if (!it.hasNext()) {
			System.out.println("가입된 회원이 없습니다.");
			return;
		}
		while (it.hasNext()) {
			MemberDTO dto = it.next();
			System.out.println(dto.toString());
		}
	}
//---------여기까지 추가

	public void delete() {

		String id;
		System.out.print("강퇴처리 할 ID입력: ");

		int result = 0;

		while (result == 0) {
			id = sc.next();

			System.out.print("정말로 삭제하시겠습니까? [Y/N]: ");
			String answer = sc.next();
			if (answer.equalsIgnoreCase("Y")) {
				result = dao.deleteData(id);
				if (result != 0) {
					System.out.println("회원이 정상적으로 탈퇴되었습니다");
				} else {
					System.out.println("회원의 탈퇴처리가 실패되었습니다.");
				}
			} else {
				System.out.println("회원 삭제가 취소되었습니다.");
			}
			System.out.println("회원 관리 메뉴로 돌아갑니다.");
			return;

		}

	}
}