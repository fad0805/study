package view;

import java.io.IOException;

import controller.AdminImpl;
import controller.CustomerImpl;
import exception.EmptyProductException;
import exception.EmptyStockException;
import exception.WrongInputException;
import exception.WrongOrderNoException;
import model.Code;
import model.OrderList;
import model.User;
import model.UserList;

/*
 * 작성일 : 2022년 07월 09-15일
 * 작성자 : 김아람
 * 설명  : 메뉴 - 로그인 상태일 때 유저 정보를 보유
 */
public class MenuImpl implements Menu, Code {
	private User user;
	
	private MenuImpl() {}
	private static MenuImpl menu = new MenuImpl();
	
	public static MenuImpl getInstance() {
		if(menu == null)
			menu = new MenuImpl();
		return menu;
	}

	// 모든 클래스에서 메뉴를 이동할 때 부르는 메서드
	@Override
	public void commonMenu(int num) throws IOException, NumberFormatException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		switch(num) {
		case SHOP_LOGIN : login();
			break;
		case HOST_MENU : adminMenu();
			break;
		case HOST_STOCK_MENU : adminStockMenu();
			break;
		case HOST_GOODS_LIST : AdminImpl.getInstance().productList();
			break;
		case HOST_GOODS_ADD : AdminImpl.getInstance().productAdd();
			break;
		case HOST_GOODS_UPDATE : AdminImpl.getInstance().productUpdate();
			break;
		case HOST_GOODS_DEL : AdminImpl.getInstance().productRemove();
			break;
		case HOST_ORDER_MENU : adminOrderMenu();
			break;
		case HOST_ORDER_LIST : AdminImpl.getInstance().orderList();
			break;
		case HOST_ORDER_CONFIRM : AdminImpl.getInstance().orderConfirm();
			break;
		case HOST_ORDER_CANCEL : AdminImpl.getInstance().orderCancel();
			break;
		case HOST_ORDER_REFUND : AdminImpl.getInstance().orderRefund();
			break;
		case HOST_ORDER_TOTAL : adminTotalMenu();
			break;
		case GUEST_MENU : customerMenu();
			break;
		case GUEST_GOODS : CustomerImpl.getInstance().productList();// 상품리스트
			break;
		case GUEST_NOW_BUY : CustomerImpl.getInstance().nowBuy();// 상품 바로 구매
			break;
		case GUEST_REFUND : CustomerImpl.getInstance().refund();// 환불
			break;
		case GUEST_CART_LIST : customerCart();
			break;
		case GUEST_CART_ADD : CustomerImpl.getInstance().cartAdd(); // 장바구니 담기
			break;
		case GUEST_CART_DEL : CustomerImpl.getInstance().cartRemove(); // 장바구니 삭제
			break;
		case GUEST_CART_BUY : CustomerImpl.getInstance().cartBuy(); // 장바구니 구매
			break;
		case GUEST_ORDER_LIST : showMyOrder();
			break;
		case HOST_LOGOUT : AdminLogout();
			break;
		case GUEST_LOGOUT : UserLogout();
			break;
		case SHOP_EXIT : exit();
			break;
		}
	}

	// 로그인 - 회원이 아닐 시 회원가입 가능
	@Override
	public void login() throws IOException, NumberFormatException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		System.out.println("-----------------------------------------"
				+ "\n\t 잃어버린 섬의 주민인가요?"
				+ "\n 1. 주민입니다 2. 아니요. 새로 등록해주세요."
				+ "\n-----------------------------------------");
		int answer = view.Main.answer(2);
		switch(answer) {
		case 1 : 
			System.out.println("-----------------------------------------"
					+ "\n\t\t로그인"
					+ "\n-----------------------------------------");
			System.out.print("  이메일 : ");
			String id = view.Main.br.readLine();
			boolean loginNow = UserList.getInstance().login(id);
			if(loginNow == false) {
				System.out.println("      잃어버린 섬에 주민으로 등록하시겠습니까?"
						+ "\n     1. 주민으로 등록하기 2. 손님으로 둘러보기"
						+ "\n-----------------------------------------");
				switch(view.Main.answer(2)) {
				case 1 : join();
					return;
				case 2 : commonMenu(GUEST_MENU);
					return;
				}
			}
			break;
		case 2 : 
			System.out.println("-----------------------------------------"
					+ "\n     잃어버린 섬에 주민으로 등록하시겠습니까?"
					+ "\n    1. 주민으로 등록하기 2. 손님으로 둘러보기"
					+ "\n-----------------------------------------");
			switch(view.Main.answer(2)) {
			case 1 : join();
				return;
			case 2 : commonMenu(GUEST_MENU);
				return;
			}
			break;
		}
	}
	
	// 회원가입
	@Override
	public void join() throws IOException, NumberFormatException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		UserList.getInstance().add();
		commonMenu(GUEST_MENU);
	}

	// 관리자 메뉴
	@Override
	public void adminMenu() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		System.out.println("-----------------------------------------"
				+ "\n\t\t 관리자 메뉴"
				+ "\n 1. 재고 관리 2. 주문 관리 3. 수익 조회 4. 로그아웃"
				+ "\n-----------------------------------------");
		switch(view.Main.answer(4)) {
		case 1 : commonMenu(HOST_STOCK_MENU);
			break;
		case 2 : commonMenu(HOST_ORDER_MENU);
			break;
		case 3 : commonMenu(HOST_ORDER_TOTAL);
			break;
		case 4 : commonMenu(HOST_LOGOUT);
			break;
		}
	}

	// 관리자 재고 관리 메뉴
	@Override
	public void adminStockMenu() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		System.out.println("-----------------------------------------"
				+ "\n\t\t재고 관리"
				+ "\n     1. 상품 목록 2. 상품 추가 3. 상품 수정"
				+ "\n     4. 상품 삭제 5. 관리자 메뉴 6. 로그아웃"
				+ "\n-----------------------------------------");
		switch(view.Main.answer(5)) {
		case 1 : commonMenu(HOST_GOODS_LIST);
			break;
		case 2 : commonMenu(HOST_GOODS_ADD);
			break;
		case 3 : commonMenu(HOST_GOODS_UPDATE);
			break;
		case 4 : commonMenu(HOST_GOODS_DEL);
			break;
		case 5 : commonMenu(HOST_MENU);
			break;
		case 6 : commonMenu(HOST_LOGOUT);
			break;
		}
	}

	// 관리자 주문 관리 메뉴
	@Override
	public void adminOrderMenu() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		System.out.println("-----------------------------------------"
				+ "\n\t\t 주문 관리"
				+ "\n 1. 전체 주문 목록 2. 승인된 주문 목록 3. 승인되지 않은 주문 목록"
				+ "\n\t 4. 환불 요청 목록 5. 관리자 메뉴 6. 로그아웃"
				+ "\n-----------------------------------------");
		int answer = view.Main.answer(6);
		switch(answer) {
		case 1 : commonMenu(HOST_ORDER_LIST);
			break;
		case 2 : commonMenu(HOST_ORDER_CONFIRM);
			break;
		case 3 : commonMenu(HOST_ORDER_CANCEL);
			break;
		case 4 : commonMenu(HOST_ORDER_REFUND);
			break;
		case 5 : commonMenu(HOST_MENU);
			break;
		case 6 : commonMenu(HOST_LOGOUT);
			break;
		}
	}

	// 관리자 결산 메뉴
	@Override
	public void adminTotalMenu() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		AdminImpl.getInstance().saleTotal();
	}

	// 고객 메뉴
	@Override
	public void customerMenu() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		if(user.getId() != null) {
			System.out.println("-----------------------------------------"
					+ "\n     광장에 도착했습니다. 어디로 갈까요?"
					+ "\n    1. 상점 구경하기 2. 장바구니 확인하기"
					+ "\n 3. 구매한 물건 확인하기 4. 로그아웃 5. 섬을 빠져나가기"
					+ "\n-----------------------------------------");
			int answer = view.Main.answer(5);
			switch(answer) {
			case 1 : commonMenu(GUEST_GOODS);
				break;
			case 2 : commonMenu(GUEST_CART_LIST);
				break;
			case 3 : commonMenu(GUEST_ORDER_LIST);
				break;
			case 4 : commonMenu(GUEST_LOGOUT);
				break;
			case 5 : commonMenu(SHOP_EXIT);
				break;
			}
		} else {
			System.out.println("-----------------------------------------"
					+ "\n\t광장에 도착했습니다. 어디로 갈까요?"
					+ "\n    1. 상점 구경하기 2. 장바구니 확인하기"
					+ "\n 3. 구매한 물건 확인하기 4. 로그인 5. 섬을 빠져나가기"
					+ "\n-----------------------------------------");
			int answer = view.Main.answer(5);
			switch(answer) {
			case 1 : commonMenu(GUEST_GOODS);
				break;
			case 2 : commonMenu(GUEST_CART_LIST);
				break;
			case 3 : commonMenu(GUEST_ORDER_LIST);
				break;
			case 4 : commonMenu(SHOP_LOGIN);
				break;
			case 5 : commonMenu(SHOP_EXIT);
				break;
			}
		}
	}

	// 고객 카트 메뉴
	@Override
	public void customerCart() throws NumberFormatException, WrongOrderNoException, EmptyProductException, IOException, WrongInputException, CloneNotSupportedException, EmptyStockException {
		System.out.println("\n-----------------------------------------"
				+ "\n\t\t 장바구니 안을 살펴봅니다."
				+ "\n-----------------------------------------");
		CustomerImpl.getInstance().cartList();
		System.out.println("\n-----------------------------------------"
				+ "\n 1. 물건을 구매하기 2. 수량을 변경하기 3. 물건을 삭제하기 4. 상점으로 돌아가기 5. 광장으로 돌아가기"
				+ "\n-----------------------------------------");
		int answer = view.Main.answer(5);
		switch(answer) {
		case 1 : MenuImpl.getInstance().commonMenu(GUEST_CART_BUY);
			break;
		case 2 : CustomerImpl.getInstance().cartMod();
			break;
		case 3 : MenuImpl.getInstance().commonMenu(GUEST_CART_DEL);
			break;
		case 4 : MenuImpl.getInstance().commonMenu(GUEST_GOODS);
			break;
		case 5 : MenuImpl.getInstance().commonMenu(GUEST_MENU);
			break;
		}
	}
	
	// 샵 종료
	public void exit() {
		System.out.println("-----------------------------------------"
						+ "\n     들러주셔서 감사합니다. 즐거운 항해 되세요."
						+ "\n-----------------------------------------");

		System.exit(0);
	}
	
	// 관리자 로그아웃
	private void AdminLogout() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		System.out.println("-----------------------------------------"
				+ "\n 관리자 계정에서 로그아웃하였습니다."
				+ "\n 관리자 메뉴에 다시 접속하기 위해서는 새 로그인이 필요합니다."
				+ "\n-----------------------------------------");
		commonMenu(GUEST_MENU);
	}
	
	// 회원 로그아웃. 로그인 상태로 입력된 회원 정보를 삭제
	private void UserLogout() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		System.out.println("-----------------------------------------"
				+ "\n\t로그아웃 완료"
				+ "\n-----------------------------------------");
		user = UserList.getInstance().getUser(null);
		OrderList.getInstance().remove(view.Main.getOrderNo());
		view.Main.setOrderNo(OrderList.getInstance().addOrder(user));
		commonMenu(GUEST_MENU);
	}

	// 내 주문 조회
	public void showMyOrder() throws IOException, NumberFormatException, WrongOrderNoException, EmptyProductException, WrongInputException, CloneNotSupportedException, EmptyStockException {
		if(user.getId() == null) {
			commonMenu(GUEST_REFUND);
		} else {
			OrderList.getInstance().showOnesList(user);
			System.out.println("-----------------------------------------"
					+ "\n 1. 광장으로 돌아가기 2. 물건 환불하기"
					+ "\n-----------------------------------------");
			int answer = view.Main.answer(2);
			switch(answer) {
			case 1 : commonMenu(GUEST_MENU);
				break;
			case 2 : commonMenu(GUEST_REFUND);
				break;
			}
		}
	}
	
	// 숫자만 넣어야할 경우 체크해서 경고문을 띄움
	public boolean onlyNum(String num) {
		if(num.isEmpty()) {
			System.out.println("-----------------------------------------"
					+ "\n\t 비워둘 수 없습니다. 다시 시도해주세요."
					+ "\n-----------------------------------------");
			return false;
		}
		
		try {
			Double.parseDouble(num);
			return true;
		} catch(NumberFormatException e) {
			System.out.println("-----------------------------------------"
					+ "\n\t 숫자만 입력해야 합니다."
					+ "\n-----------------------------------------");
			return false;
		}
	}
	
	// 숫자면 참, 문자면 거짓을 반환하고, 입력이 없을 때 경고문을 띄움
	public boolean checkNum(String num) {
		if(num.isEmpty()) {
			System.out.println("-----------------------------------------"
					+ "\n\t 비워둘 수 없습니다. 다시 시도해주세요."
					+ "\n-----------------------------------------");
			return false;
		}
		
		try {
			Double.parseDouble(num);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
}
