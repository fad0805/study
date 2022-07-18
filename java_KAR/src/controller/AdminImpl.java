package controller;

import java.io.IOException;
import exception.EmptyProductException;
import exception.EmptyStockException;
import exception.WrongInputException;
import exception.WrongOrderNoException;
import model.Accessary;
import model.AccessaryList;
import model.Code;
import model.Order;
import model.OrderList;
import view.MenuImpl;


/*
 * 작성일 : 2022년 07월 09-15일
 * 작성자 : 김아람
 * 설명  : 관리자 페이지 기능 구현 혹은 링크 - 싱글톤 적용
 */

enum Switch {ON, OFF}

public class AdminImpl implements Admin, Code {
	private Switch nowProduct;
	private int productIndex;
	
	// 싱글톤 적용
	private AdminImpl() {}
	private static AdminImpl admin = new AdminImpl();
	
	public static AdminImpl getInstance() {
		if(admin == null)
			admin = new AdminImpl();
		return admin;
	}
	
	// 상품 목록 -> 개별 상품 조회
	@Override
	public void productList() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		System.out.println("\n-----------------------------------------"
				+ "\n\t\t상품 조회"
				+ "\n 1. 전체 2. 귀걸이 3. 목걸이 4. 키링 5. 기타"
				+ "\n-----------------------------------------");
		int answer = view.Main.answer(5);
		int list = 0;
		switch(answer) {
		case 1 : list = AccessaryList.getInstance().showAllList();
			break;
		case 2 : list = AccessaryList.getInstance().earringList();
			break;
		case 3 : list = AccessaryList.getInstance().necklaceList();
			break;
		case 4 : list = AccessaryList.getInstance().keyringList();
			break;
		case 5 : list = AccessaryList.getInstance().etcList();
			break;
		}
		System.out.println("\n-----------------------------------------"
				+ "\n 총 " + list + "개의 상품이 있습니다."
				+ "\n 1. 개별 상품 조회 2. 다른 목록 보기 3. 재고 관리 4. 관리자 메뉴 5. 로그아웃"
				+ "\n-----------------------------------------");
		
		answer = view.Main.answer(5);
		
		switch(answer) {
		case 1 : 
			System.out.println("-----------------------------------------"
					+ "\n 각 상품의 개별 정보를 조회하려면 상품 번호나 상품명을 입력하세요."
					+ "\n-----------------------------------------");
			
			String product = view.Main.br.readLine();
			try {
				String thisProduct = null;
				if(view.MenuImpl.getInstance().checkNum(product)) {
					thisProduct = AccessaryList.getInstance().getProductInfo(Integer.parseInt(product)-1);
					nowProduct = Switch.ON;
					productIndex = Integer.parseInt(product);
				}
				else {
					thisProduct = AccessaryList.getInstance().getProductInfo(product);
					nowProduct = Switch.ON;
					productIndex = AccessaryList.getInstance().getIndex(product)+1;
				}
				System.out.println("-----------------------------------------"
						+ "\n\t" + AccessaryList.getInstance().getName(productIndex) + " 상품 정보"
						+ "\n-----------------------------------------"
						+ "\n 번호\t카테고리\t이름\t가격\t재고량"
						+ "\n " + thisProduct
						+ "\n-----------------------------------------"
						+ "\n 1. 수정 2. 삭제 3. 재고 관리 4. 관리자 메뉴 5. 로그아웃");
				
				answer = view.Main.answer(5);
				switch(answer) {
				case 1 : productUpdate();
					return;
				case 2 : productRemove();
					return;
				case 3 : 
					nowProduct = Switch.OFF; 
					MenuImpl.getInstance().commonMenu(HOST_STOCK_MENU);
					return;
				case 4 : 
					nowProduct = Switch.OFF;
					MenuImpl.getInstance().commonMenu(HOST_MENU);
					return;
				case 5 : 
					nowProduct = Switch.OFF;
					MenuImpl.getInstance().commonMenu(HOST_LOGOUT);
					return;
				}
			} catch (EmptyProductException e) {
				System.out.println(e.getMessage());
			}
			break;
		case 2 : 
			nowProduct = Switch.OFF;  
			productList();
			break;
		case 3 :  
			nowProduct = Switch.OFF;
			MenuImpl.getInstance().commonMenu(HOST_STOCK_MENU);
			break;
		case 4 : 
			nowProduct = Switch.OFF;
			MenuImpl.getInstance().commonMenu(HOST_MENU);
			break;
		case 5 : 
			nowProduct = Switch.OFF;
			MenuImpl.getInstance().commonMenu(HOST_LOGOUT);
			break;
		}
			
	}

	// 상품 추가
	@Override
	public void productAdd() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		AccessaryList.getInstance().add();
	}

	// 상품 수정
	@Override
	public void productUpdate() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		if(nowProduct == Switch.OFF) {
			System.out.println("\n-----------------------------------------"
						+ "\n 어떤 상품을 수정하시겠습니까?"
						+ "\n 상품명 혹은 상품 번호를 입력해주세요."
						+ "\n 상품 목록 조회를 원하면 '목록'을 재고 관리로 돌아가고 싶으면 '재고'를 입력하세요."
						+ "\n-----------------------------------------");
			
			String product = view.Main.br.readLine();
			if(view.MenuImpl.getInstance().checkNum(product)) {
				AccessaryList.getInstance().mod(Integer.parseInt(product)-1);
			}
			else {
				switch(product) {
				case "목록" : productList();
					break;
				case "재고" : MenuImpl.getInstance().adminStockMenu();
					break;
				default : 
					int index = AccessaryList.getInstance().getIndex(product);
					AccessaryList.getInstance().mod(index);
					break;
				}
			}
		}
		else {
			AccessaryList.getInstance().mod(productIndex);
		}
		
		System.out.printf("\n-----------------------------------------"
				+ "\n 1. 상품 조회 2. 재고 관리 3. 관리자 메뉴 4. 로그아웃"
				+ "\n-----------------------------------------\n");
		int answer = view.Main.answer(4);
		nowProduct = Switch.OFF;
		switch(answer) {
		case 1 : productList();
			break;
		case 2 : MenuImpl.getInstance().commonMenu(HOST_STOCK_MENU);
			break;
		case 3 : MenuImpl.getInstance().commonMenu(HOST_MENU);
			break;
		case 4 : MenuImpl.getInstance().commonMenu(HOST_LOGOUT);
			break;
		}
	}

	// 상품 삭제
	@Override
	public void productRemove() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		if(nowProduct == Switch.OFF) {
			System.out.println("\n-----------------------------------------"
					+ "\n 어떤 상품을 삭제하시겠습니까?"
					+ "\n 상품명 혹은 상품 번호를 입력해주세요."
					+ "\n 상품 목록 조회를 원하면 '목록'을 재고 관리로 돌아가고 싶으면 '재고'를 입력하세요."
					+ "\n-----------------------------------------");
			
			String product = view.Main.br.readLine();
			if(view.MenuImpl.getInstance().checkNum(product)) {
				AccessaryList.getInstance().remove(Integer.parseInt(product)-1);
			}
			else {
				switch(product) {
				case "목록" : productList();
					break;
				case "재고" : MenuImpl.getInstance().adminStockMenu();
					break;
				default : 
					int index = AccessaryList.getInstance().getIndex(product);
					AccessaryList.getInstance().remove(index);
					break;
				}
			}
		}
		else {
			AccessaryList.getInstance().remove(productIndex-1);
			nowProduct = Switch.OFF;
		}
		MenuImpl.getInstance().commonMenu(HOST_STOCK_MENU);
	}

	// 주문 목록
	@Override
	public void orderList() throws EmptyProductException, NumberFormatException, IOException, WrongInputException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		while(true) {
			int n = OrderList.getInstance().showAllList();
			System.out.println("\n-----------------------------------------"
					+ "\n 총 " + n + "개의 주문이 있습니다." 
					+ "\n-----------------------------------------"
					+ "\n 1. 개별 주문 조회 2. 승인한 주문 목록 3. 승인되지 않은 주문 목록 4. 전체 주문 목록 5. 주문 관리");
			int answer = view.Main.answer(5);
			switch(answer) {
			case 1 : eachOrderMenu();				
				return;
			case 2 : orderConfirm(); // 2. 승인한 주문 목록 
				return;
			case 3 : orderCancel(); // 3. 승인되지 않은 주문 목록
				return;
			case 4 : // 4. 전체 주문 목록 - 아무것도 안하고 처음으로 돌아가기
				break;
			case 5 : MenuImpl.getInstance().commonMenu(HOST_ORDER_MENU);
			}
		}
	}

	// 결제 승인한 주문 목록 
	@Override
	public void orderConfirm() throws EmptyProductException, NumberFormatException, IOException, WrongInputException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		int n = OrderList.getInstance().showPermissionList();
		System.out.println("\n-----------------------------------------"
				+ "\n 총 " + n + "개의 주문이 있습니다." 
				+ "\n-----------------------------------------"
				+ "\n 1. 개별 주문 조회 2. 승인되지 않은 주문 목록 3. 전체 주문 목록 4. 주문 관리");
		int answer = view.Main.answer(4);
		switch(answer) {
		case 1 : eachOrderMenu();
			return;
		case 2 : orderCancel(); // 2. 승인되지 않은 주문 목록
			return;
		case 3 : orderList(); // 3. 전체 주문 목록
			return;
		case 4 : MenuImpl.getInstance().commonMenu(HOST_ORDER_MENU); // 4. 주문 관리
			return;
		}
	}

	// 결제 승인되지 않은 주문 목록
	@Override
	public void orderCancel() throws EmptyProductException, NumberFormatException, IOException, WrongInputException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		int n = OrderList.getInstance().showNonPermissionList();
		System.out.println("\n-----------------------------------------"
				+ "\n 총 " + n + "개의 주문이 있습니다." 
				+ "\n-----------------------------------------"
				+ "\n 1. 개별 주문 조회 2. 승인한 주문 목록 3. 전체 주문 목록 4. 주문 관리");
		int answer = view.Main.answer(4);
		switch(answer) {
		case 1 : eachOrderMenu();
			return;
		case 2 : orderConfirm(); // 2. 승인한 주문 목록
			return;
		case 3 : orderList(); // 3. 전체 주문 목록
			return;
		case 4 : MenuImpl.getInstance().commonMenu(HOST_ORDER_MENU); // 4. 주문 관리
			return;
		}
	}

	// 환불 요청 목록
	public void orderRefund() throws NumberFormatException, WrongOrderNoException, IOException, EmptyProductException, EmptyStockException, WrongInputException, CloneNotSupportedException {
		System.out.println("-----------------------------------------"
				+ "\n\t\t 환불 요청된 주문 목록"
				+ "\n-----------------------------------------");
		OrderList.getInstance().showRefundList();
		
		System.out.println("\n-----------------------------------------"
				+ "\n 환불 처리할 주문 번호,"
				+ "\n 혹은 전체 환불 처리를 원할 시 '환불'을 입력하세요."
				+ "\n-----------------------------------------");
		String orderNo = view.Main.br.readLine();
		if(orderNo.equals("환불")) {
			OrderList.getInstance().allRefund();
			OrderList.getInstance().refundRemove();
			MenuImpl.getInstance().commonMenu(HOST_ORDER_MENU);
		}
		else if(view.MenuImpl.getInstance().onlyNum(orderNo)) {
			Order order = OrderList.getInstance().getOrder(Integer.parseInt(orderNo));
			order.refund();
			MenuImpl.getInstance().commonMenu(HOST_ORDER_MENU);
		}
		else {
			System.out.println("\n-----------------------------------------"
					+ "\n 유효하지 않은 입력입니다."
					+ "\n-----------------------------------------");
			MenuImpl.getInstance().commonMenu(HOST_ORDER_MENU);
		}
	}
	
	// 결산
	@Override
	public void saleTotal() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		System.out.println("-----------------------------------------"
				+ "\n\t\t 잃어버린 섬의 수익을 계산합니다.");
		OrderList.getInstance().total();
		System.out.println("-----------------------------------------"
				+ "\n\t 1. 관리자 메뉴 2. 로그아웃"
				+ "\n-----------------------------------------");
		switch(view.Main.answer(2)) {
		case 1 : MenuImpl.getInstance().commonMenu(HOST_MENU);
			break;
		case 2 : MenuImpl.getInstance().commonMenu(HOST_LOGOUT);
			break;
		}
	}
	
	// getter setter
	public int getProductIndex() {
		return productIndex;
	}
	
	public void setProductIndex(int productIndex) {
		this.productIndex = productIndex;
	}
	
	// 지금 조회하고 있는 개별 상품이 있는지 확인
	public Switch getNowProduct() {
		return nowProduct;
	}
	
	// 현재 조회 중인 상품이 있는지 확인하는 변수를 수정
	public void switchNowProduct(Switch now) {
		if(now == Switch.OFF) {
			this.nowProduct = Switch.ON;
		}
		else if(now == Switch.ON) {
			this.nowProduct = Switch.OFF;
		}
	}
	
	// 개별 조회해서 수정하는 기능(너무 길고 다른 곳에서도 써서 아래로 뺌)
	private void orderMod(String orderNo, Order order) throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		while(true) {
			Accessary acs;
			System.out.println("\n-----------------------------------------"
					+ "\n\t 무엇을 수정하시겠습니까?"
					+ "\n 1. 상품 추가 2. 상품 개수 변경 3. 상품 삭제 4. 주문 관리"
					+ "\n-----------------------------------------");
			int answer = view.Main.answer(4);
			switch(answer) {
			case 1 : 
				System.out.println("\n-----------------------------------------"
						+ "\n\t 어떤 상품을 추가하시겠습니까?"
						+ "\n\t 상품 번호 또는 상품명을 입력하세요."
						+ "\n-----------------------------------------");
				String prd = view.Main.br.readLine();
				if(view.MenuImpl.getInstance().checkNum(prd)) {
					acs = AccessaryList.getInstance().getProduct(Integer.parseInt(prd));
					System.out.println("-----------------------------------------"
							+ "\n 번호\t카테고리\t이름\t가격\t재고량");
					AccessaryList.getInstance().getProductInfo(Integer.parseInt(prd));
				}
				else {
					acs = AccessaryList.getInstance().getProduct(prd);
					System.out.println("-----------------------------------------"
							+ "\n 번호\t카테고리\t이름\t가격\t재고량");
					AccessaryList.getInstance().getProductInfo(prd);
				}
				
				System.out.println("-----------------------------------------"
						+ "\n 이 상품을 현재 주문에 추가하시겠습니까?"
						+ "\n\t 1. 추가 2. 취소"
						+ "\n-----------------------------------------");
				answer = view.Main.answer(2);
				switch(answer) {
				case 1 : OrderList.getInstance().addProduct(Integer.parseInt(orderNo), acs);
					return;
				case 2 : // 개별 주문 수정으로 복귀
					break;
				}
				break;
			case 2 : // 해당 상품 개수 변경
				System.out.println("\n-----------------------------------------"
						+ "\n\t 어떤 상품을 수정하시겠습니까?"
						+ "\n\t 상품 번호 또는 상품명을 입력하세요."
						+ "\n-----------------------------------------");
				prd = view.Main.br.readLine();
				if(view.MenuImpl.getInstance().checkNum(prd)) {
					acs = order.getBuyProduct(Integer.parseInt(prd)-1);
				} else {
					acs = order.getBuyProduct(prd);
				}
				System.out.println("\n-----------------------------------------"
						+ "\n\t 주문 수량을 입력하세요."
						+ "\n-----------------------------------------");
				String count = view.Main.br.readLine();
				if(view.MenuImpl.getInstance().onlyNum(count)) {
					OrderList.getInstance().modProduct(order.getOrderNo(), acs, Integer.parseInt(count));
					eachOrderMenu();
					return;
				}
			case 3 : // 해당 상품 삭제
				System.out.println("\n-----------------------------------------"
						+ "\n\t 어떤 상품을 삭제하시겠습니까?"
						+ "\n\t 상품 번호 또는 상품명을 입력하세요."
						+ "\n-----------------------------------------");
				prd = view.Main.br.readLine();
				if(view.MenuImpl.getInstance().checkNum(prd)) {
					acs = order.getBuyProduct(Integer.parseInt(prd));
				} else {
					acs = order.getBuyProduct(prd);
				}
				System.out.println("\n-----------------------------------------"
						+ "\n " + acs.getName() + "를 주문 취소하시겠습니까?"
						+ "\n\t 1. 이 상품 주문 삭제 2. 취소" 
						+ "\n-----------------------------------------");
				answer = view.Main.answer(2);
				switch(answer) {
				case 1 : OrderList.getInstance().removeProduct(Integer.parseInt(orderNo), acs);
					eachOrderMenu();
					break;
				case 2 : // 다시 앞으로
					break;
				}							
				break;
			case 4 : MenuImpl.getInstance().commonMenu(HOST_ORDER_MENU);
				return;
			}
		}
	}
	
	// 개별 주문 조회시 뜨는 메뉴
	private void eachOrderMenu() throws EmptyProductException, IOException, NumberFormatException, WrongOrderNoException, WrongInputException, CloneNotSupportedException, EmptyStockException {
		System.out.println("\n-----------------------------------------"
				+ "\n 조회하고 싶은 주문 번호를 입력해주세요." 
				+ "\n-----------------------------------------");
		String orderNo = view.Main.br.readLine();
		if(view.MenuImpl.getInstance().onlyNum(orderNo)) {
			Order order = OrderList.getInstance().getOrder(Integer.parseInt(orderNo));
			while(true) {
				order.showOrder();
				System.out.println("\n-----------------------------------------"
						+ "\n 1. 결제 승인 여부 변경 2. 주문 수정 3. 주문 삭제 4. 주문 관리"
						+ "\n-----------------------------------------");
				int answer = view.Main.answer(4);
				switch(answer) {
				case 1 : // 1. 결제 승인 여부 변경
					if(order.getPermission() == HOST_ORDER_CONFIRM) {
						order.setPermission(HOST_ORDER_CANCEL);
					} else if(order.getPermission() == HOST_ORDER_CANCEL) {
						order.setPermission(HOST_ORDER_CONFIRM);
						order.buyConfirm();
					}
					break;
				case 2 : orderMod(orderNo, order);
					return;
				case 3 : 
					System.out.println("\n-----------------------------------------"
							+ "\n " + order.getOrderNo() + "번 주문을 삭제하시겠습니까?"
							+ "\n 1. 삭제 2. 취소"
							+ "\n-----------------------------------------");
					answer = view.Main.answer(2);
					switch(answer) {
					case 1 : 
						if(OrderList.getInstance().remove(Integer.parseInt(orderNo))) {
							System.out.println("-----------------------------------------"
									+ "\n " + orderNo + "번 주문이 삭제되었습니다."
									+ "\n-----------------------------------------");
						}
						MenuImpl.getInstance().commonMenu(HOST_ORDER_MENU);
						return;
					case 2 : 
						System.out.println("-----------------------------------------"
								+ "\n 1. 전체 주문 목록 2. 주문 관리 3. 관리자 메뉴"
								+ "\n-----------------------------------------");
						answer = view.Main.answer(3);
						switch(answer) {
						case 1 : orderList();
							return;
						case 2 : view.MenuImpl.getInstance().commonMenu(HOST_ORDER_MENU);
							return;
						case 3 : view.MenuImpl.getInstance().commonMenu(HOST_MENU);
							return;
						}
						return;
					}
					return;
				case 4 : view.MenuImpl.getInstance().commonMenu(HOST_ORDER_MENU);
					return;
				}
			}
		}
	}
}
