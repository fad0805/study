package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import exception.EmptyProductException;
import exception.EmptyStockException;
import exception.WrongInputException;
import exception.WrongOrderNoException;
import model.AccessaryList;
import model.Code;
import model.OrderList;
import model.UserList;

/*
 * 작성일 : 2022년 07월 09-15일
 * 작성자 : 김아람
 * 설명  : 메인 클래스 - 버퍼드 리더 및 선택지 입력 메서드 보유
 * 		- 시작할 때 회원 정보, 상품 정보 읽어드림
 * 		- 시작과 동시에 장바구니로 쓸 오더 인터페이스 생성
 */
public class Main implements Code {
	private static int orderNo;
	
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws NumberFormatException, IOException {		
		AccessaryList.getInstance().start();
		UserList.getInstance().start();
		
		orderNo = OrderList.getInstance().addOrder(UserList.getInstance().getUser(null));
		MenuImpl.getInstance().setUser(UserList.getInstance().getUser(null));
		
		System.out.println("-----------------------------------------"
				+ "\n\t잃어버린 섬에 오신 것을 환영합니다"
				+ "\n-----------------------------------------\n");
		
		try {
			System.out.println("-----------------------------------------"
				+ "\n\t    제일 먼저 어디로 갈까요?"
				+ "\n   1. 광장으로 가기 2. 로그인 3. 섬을 빠져나가기"
				+ "\n-----------------------------------------");
			
			int answer = answer(3);
			
			switch(answer) {
			case 1 : // 상점 구경하기
				MenuImpl.getInstance().commonMenu(GUEST_MENU);
				break;
			case 2 : // 로그인
				MenuImpl.getInstance().commonMenu(SHOP_LOGIN);
				break;
			case 3 : // 종료
				MenuImpl.getInstance().commonMenu(SHOP_EXIT);
				break;
			}
		} catch(WrongInputException | EmptyProductException | WrongOrderNoException | EmptyStockException | CloneNotSupportedException e) {
			System.out.println("\n     ▶️ " + e.getMessage());
		} finally {
			if(br != null) br.close();
		}
		
	}
	
	public static int getOrderNo() {
		return orderNo;
	}
	
	public static void setOrderNo(int orderNo) {
		Main.orderNo = orderNo;
	}
	
	// 선택지 입력 메서드. null, 문자, 범위 외 숫자 입력이 발생하면 예외 처리
	public static int answer(int menu) throws NumberFormatException, IOException, WrongInputException {
		String answer = br.readLine();
		if(answer.isEmpty()) {
			System.out.println("-----------------------------------------"
					+ "\n\t    길을 잘못 들었습니다."
					+ "\n 섬의 미아가 된 당신, 영원한 미궁에 빠지고 말았네요."
					+ "\n\t  당신의 내일 심심한 위로를."
					+ "\n-----------------------------------------");
			throw new WrongInputException("메뉴는 비워둘 수 없습니다.");
		}
		else if(Character.isDigit(answer.charAt(0)) != true) {
			System.out.println("-----------------------------------------"
					+ "\n\t    길을 잘못 들었습니다."
					+ "\n 섬의 미아가 된 당신, 영원한 미궁에 빠지고 말았네요."
					+ "\n\t  당신의 내일 심심한 위로를."
					+ "\n-----------------------------------------");
			throw new WrongInputException("메뉴는 숫자로 입력해야 합니다.");
		}
		else if(answer.length() > 1) {
			System.out.println("-----------------------------------------"
					+ "\n\t    길을 잘못 들었습니다."
					+ "\n 섬의 미아가 된 당신, 영원한 미궁에 빠지고 말았네요."
					+ "\n\t  당신의 내일 심심한 위로를."
					+ "\n-----------------------------------------");
			throw new WrongInputException("메뉴는 한 자 이상 입력할 수 없습니다.");
		}
		else if(Integer.parseInt(answer)<1 || Integer.parseInt(answer)>menu) {
			System.out.println("-----------------------------------------"
					+ "\n\t    길을 잘못 들었습니다."
					+ "\n 섬의 미아가 된 당신, 영원한 미궁에 빠지고 말았네요."
					+ "\n\t  당신의 내일에 심심한 위로를."
					+ "\n-----------------------------------------");
			throw new WrongInputException("입력한 메뉴가 존재하지 않습니다.");
		}
		return Integer.parseInt(answer);
	}
}
