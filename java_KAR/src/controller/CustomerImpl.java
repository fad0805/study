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
import model.User;
import model.UserList;
import view.MenuImpl;


/*
 * 작성일 : 2022년 07월 09-15일
 * 작성자 : 김아람
 * 설명  : 고객 페이지 기능 구현 혹은 링크 - 싱글톤 적용
 */

public class CustomerImpl implements Customer, Code {
	private CustomerImpl() {}
	private static CustomerImpl list = new CustomerImpl();
	
	public static CustomerImpl getInstance() {
		if(list == null)
			list = new CustomerImpl();
		return list;
	}
	
	// 상품 목록 출력 - 이름 및 번호 검색으로 개별 정보 조회
	public void productList() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		System.out.println("-----------------------------------------"
				+ "\n\t       상점에 들어왔습니다."
				+ "\n\t        어디부터 볼까요?"
				+ "\n-----------------------------------------"
				+ "\n 1. 귀걸이 2. 목걸이 3. 열쇠고리 4. 기타 5. 전체"
				+ "\n-----------------------------------------");
		int answer = view.Main.answer(5);
		
		int n = 0;
		switch(answer) {
		case 1 : n = AccessaryList.getInstance().earringList();
			break;
		case 2 : n = AccessaryList.getInstance().necklaceList();
			break;
		case 3 : n = AccessaryList.getInstance().keyringList();
			break;
		case 4 : n = AccessaryList.getInstance().etcList();
			break;
		case 5 : n = AccessaryList.getInstance().showAllList();
			break;
		}
		System.out.println("\n-----------------------------------------"
				+ "\n " + n + "개의 물건이 진열되어 있습니다."
				+ "\n 1. 다른 진열장 보기 2. 바로 구매 3. 장바구니 담기 4. 장바구니 보기 5. 상점을 빠져나가기"
				+ "\n-----------------------------------------");
		answer = view.Main.answer(5);
		switch(answer) {
		case 1 : productList();
			return;
		case 2 : nowBuy();
			return;
		case 3 : cartAdd();
			return;
		case 4 : cartList();
			return;
		case 5 : MenuImpl.getInstance().commonMenu(GUEST_MENU);
			return;
		}
	}

	// 고객 카트 목록 출력 
	@Override
	public void cartList() throws WrongOrderNoException, EmptyProductException, NumberFormatException, IOException, WrongInputException, CloneNotSupportedException, EmptyStockException {
		Order o = OrderList.getInstance().getOrder(view.Main.getOrderNo());
		o.showOrder();
		System.out.println("\n-----------------------------------------"
				+ "\n\t 어떻게 할까요?"
				+ "\n 1. 전체 구매하기 2. 물건 수량 변경하기 3. 물건 빼기"
				+ "\n 4. 광장으로 나가기 5. 상점을 둘러보기 6. 섬 빠져나가기"
				+ "\n-----------------------------------------");
		int answer = view.Main.answer(7);
		switch(answer) {
		case 1 : MenuImpl.getInstance().commonMenu(GUEST_CART_BUY);
			break;
		case 2 : 
		case 3 : 
			cartMod(answer, String.valueOf(view.Main.getOrderNo()), o);
			break;		
		case 4 : MenuImpl.getInstance().commonMenu(GUEST_MENU);
			break;
		case 5 : MenuImpl.getInstance().commonMenu(GUEST_GOODS);
			break;
		case 6 : MenuImpl.getInstance().commonMenu(SHOP_EXIT);
			break;
		}
	}

	// 고객 카트에 물건을 추가
	@Override
	public void cartAdd() throws NumberFormatException, IOException, WrongInputException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		System.out.println("\n-----------------------------------------"
				+ "\n\t\t어떤 물건을 담을까요?"
				+ "\n\t상품명, 혹은 상품 번호를 입력해주세요."
				+ "\n-----------------------------------------");
		String product = view.Main.br.readLine();
		try {
			String thisProduct;
			int productIndex;
			if(view.MenuImpl.getInstance().checkNum(product)) {
				thisProduct = AccessaryList.getInstance().getProductInfo(Integer.parseInt(product)-1);
				productIndex = Integer.parseInt(product);
			}
			else {
				thisProduct = AccessaryList.getInstance().getProductInfo(product);
				productIndex = AccessaryList.getInstance().getIndex(product)+1;
			}
			System.out.println("-----------------------------------------"
					+ "\n\t" + AccessaryList.getInstance().getName(productIndex) + " 상품 정보"
					+ "\n-----------------------------------------"
					+ "\n 번호\t카테고리\t이름\t가격\t수량"
					+ "\n " + thisProduct
					+ "\n-----------------------------------------"
					+ "\n 이 상품을  장바구니에 담으실 건가요?"
					+ "\n 1. 네 2. 아니요"
					+ "\n-----------------------------------------");
			
			int answer = view.Main.answer(2);
			switch(answer) {
			case 1 : 
				Accessary acsOrigin = AccessaryList.getInstance().getProduct(productIndex-1); // 악세사리 검색
				Accessary acs = (Accessary) acsOrigin.clone(); // 장바구니에 넣을 상품 인스턴스(클론) 생성
				while(true) {
					System.out.println("-----------------------------------------"
							+ "\n\t    얼마나 담을까요?"
							+ "\n-----------------------------------------");
					
					String count = view.Main.br.readLine();
					if(view.MenuImpl.getInstance().onlyNum(count)) {
						if(acsOrigin.getProductCount() >= Integer.parseInt(count)) { // 재고 체크
							// 수량 OK
							acs.setProductCount(Integer.parseInt(count)); // 장바구니에 넣을 상품 인스턴스 셋팅
							
							int orderNo = view.Main.getOrderNo();
							
							OrderList.getInstance().addProduct(orderNo, acs); // 물건 담기
							
							System.out.println("\n-----------------------------------------"
									+ "\n\t 장바구니에 물건을 담았습니다."
									+ "\n-----------------------------------------");
							cartList(); // 장바구니로 들어가기
							break;
						} 
						System.out.println("-----------------------------------------"
								+ "\n\t   상점에 구비된 물건이 부족합니다.");
						// 수량 부족, 다시 앞으로
					} 
					// 문자열 입력. 다시 앞으로.
				}
				break;
			case 2 : 
				System.out.println("\n-----------------------------------------"
						+ "\n\t 다른 물건을 담으시겠어요?"
						+ "\n\t 1. 다시 진열장을 볼래 2. 다른 물건을 담을래"
						+ "\n-----------------------------------------");
				answer = view.Main.answer(2);
				switch(answer) {
				case 1 : productList();
					return;
				case 2 : cartAdd();
					return;
				}
				break;
			}
		} catch (EmptyProductException e) {
			System.out.println(e.getMessage());
		}
	}

	// 고객 카트의 물건을 수정(수량)
	public void cartMod() throws IOException, NumberFormatException, WrongOrderNoException, EmptyProductException, WrongInputException, CloneNotSupportedException, EmptyStockException {
		System.out.println("-----------------------------------------"
				+ "\n 어떤 물건의 수량을 변경하시겠어요? 상품명을 입력하세요."
				+ "\n-----------------------------------------");
		String prd = view.Main.br.readLine();
		if(view.MenuImpl.getInstance().checkNum(prd)) {
			Accessary acs = OrderList.getInstance().getOrder(view.Main.getOrderNo()).getBuyProduct(prd);
			System.out.println("-----------------------------------------"
					+ "\n\t\t 몇 개나 담을까요?"
					+ "\n-----------------------------------------");
			prd = view.Main.br.readLine();
			if(view.MenuImpl.getInstance().onlyNum(prd)) {
				OrderList.getInstance().modProduct(view.Main.getOrderNo(), acs, Integer.parseInt(prd));
			} else { cartMod(); }	
		} else { cartMod(); }
		cartList();
	}
	
	// 고객 카트의 물건을 상품 단위로 삭제
	@Override
	public void cartRemove() throws IOException, EmptyProductException, WrongOrderNoException, NumberFormatException, WrongInputException, CloneNotSupportedException, EmptyStockException {
		System.out.println("-----------------------------------------"
				+ "\n\t 어떤 물건을 꺼낼까요? 상품명을 입력하세요."
				+ "\n-----------------------------------------");
		String prd = view.Main.br.readLine();
		if(view.MenuImpl.getInstance().checkNum(prd)) {
			Accessary acs = OrderList.getInstance().getOrder(view.Main.getOrderNo()).getBuyProduct(Integer.parseInt(prd)-1);
			
			System.out.println("-----------------------------------------"
					+ "\n " + acs.getName() + "을 장바구니에서 꺼낼까요?"
					+ "\n\t\t 1. 꺼내기 2. 그냥 두기"
					+ "\n-----------------------------------------");
			int answer = view.Main.answer(2);
			switch(answer) {
			case 1 : 
				if(OrderList.getInstance().removeProduct(view.Main.getOrderNo(), acs)) {
					System.out.println("-----------------------------------------"
							+ "\n " + acs.getName() + "를 장바구니에서 꺼냈습니다."
							+ "\n-----------------------------------------");
					cartList();
				} else { cartRemove(); }
				break;
			case 2 : cartList();
				break;
			}
		} else { cartRemove(); }
	}

	// 고객 카트의 물건 결제
	@Override
	public void cartBuy() throws NumberFormatException, IOException, WrongInputException, WrongOrderNoException, EmptyProductException, CloneNotSupportedException, EmptyStockException {
		System.out.println("-----------------------------------------"
				+ "\n\t 장바구니에 담은 물건을 모두 구매할까요?"
				+ "\n\t\t 1. 살래 2. 좀 더 둘러볼래"
				+ "\n-----------------------------------------");
		int answer = view.Main.answer(2);
		switch(answer) {
		case 1 : 
			Order o = OrderList.getInstance().getOrder(view.Main.getOrderNo());
			o.setPurchaseCheck(PURCHASE_COMPLETE);
			int newOder = OrderList.getInstance().addOrder(view.MenuImpl.getInstance().getUser());
			view.Main.setOrderNo(newOder);
			System.out.println("-----------------------------------------"
					+ "\n\t\t 상점에서 물건을 구매했습니다."
					+ "\n\t\t 주문 번호 : " + o.getOrderNo()
					+ "\n-----------------------------------------"
					+ "\n\t\t 이번엔 어디로 갈까요?"
					+ "\n 1. 광장으로 돌아가기 2. 상점을 더 둘러보기 3. 섬을 빠져나가기"
					+ "\n-----------------------------------------");
			answer = view.Main.answer(4);
			switch(answer) {
			case 1 : MenuImpl.getInstance().commonMenu(GUEST_MENU);
				return;
			case 2 : MenuImpl.getInstance().commonMenu(GUEST_GOODS);
				return;
			case 3 : MenuImpl.getInstance().commonMenu(SHOP_EXIT);
				return;
			}
			break;
		case 2 : cartList();
			break;
		}
	}

	// 상품 리스트에서 바로 개별 상품을 구매 - 카트와 동일한데 하나만 넘기는 기능
	@Override
	public void nowBuy() throws IOException, EmptyProductException, WrongOrderNoException, NumberFormatException, WrongInputException, CloneNotSupportedException, EmptyStockException {
		System.out.println("-----------------------------------------"
				+ "\n\t     물건을 살까요?"
				+ "\n 구매하고 싶은 물건의 상품번호 혹은 상품명을 입력해주세요."
				+ "\n-----------------------------------------");
		String prd = view.Main.br.readLine();
		String thisProduct = null;
		int index;
		if(view.MenuImpl.getInstance().checkNum(prd)) {
			thisProduct = AccessaryList.getInstance().getProductInfo(Integer.parseInt(prd)-1);
			index = Integer.parseInt(prd);
		} else {
			thisProduct = AccessaryList.getInstance().getProductInfo(prd);
			index = AccessaryList.getInstance().getIndex(prd)+1;
		}
		System.out.println("-----------------------------------------"
				+ "\n\t\t" + AccessaryList.getInstance().getName(index)
				+ "\n-----------------------------------------"
				+ "\n 번호\t카테고리\t이름\t가격\t재고량"
				+ "\n " + thisProduct
				+ "\n-----------------------------------------"
				+ "\n\t 이 물건을 구매할까요?"
				+ "\n\t 1. 구매하기 2. 그만두기"
				+ "\n-----------------------------------------");
		int answer = view.Main.answer(2);
		switch(answer) {
		case 1 : 
			Accessary acsOrigin = AccessaryList.getInstance().getProduct(index-1); // 악세사리 검색
			Accessary acs = (Accessary) acsOrigin.clone(); // 장바구니에 넣을 상품 인스턴스(클론) 생성
			while(true) {
				System.out.println("-----------------------------------------"
						+ "\n\t\t얼마나 살까요?"
						+ "\n-----------------------------------------");
				
				String count = view.Main.br.readLine();
				if(view.MenuImpl.getInstance().onlyNum(count)) {
					if(acsOrigin.getProductCount() >= Integer.parseInt(count)) { // 재고 체크
						// 수량 OK
						acs.setProductCount(Integer.parseInt(count)); // 장바구니에 넣을 상품 인스턴스 셋팅
						
						int orderNo = view.Main.getOrderNo();
						OrderList.getInstance().addProduct(orderNo, acs); // 물건 담기
						
						System.out.println("-----------------------------------------"
								+ "\n\t " + acs.getName() + "를 " + acs.getProductCount() + "개 구매하시겠어요?"
								+ "\n\t 1. 구매하기 2. 상점을 더 둘러보기"
								+ "\n-----------------------------------------");
						answer = view.Main.answer(2);
						switch(answer) {
						case 1 : 
							OrderList.getInstance().modOrder(view.Main.getOrderNo(), MenuImpl.getInstance().getUser()); // 유저 세팅
							Order o = OrderList.getInstance().getOrder(orderNo); // 오더 세팅
							o.setPurchaseCheck(PURCHASE_COMPLETE); // 결제 완료
							
							System.out.println("-----------------------------------------"
									+ "\n\t " + acs.getName() + "를 " + acs.getProductCount() + "개 구매했습니다."
									+ "\n\t 주문 번호 : " + orderNo
									+ "\n-----------------------------------------");
							int newOder = OrderList.getInstance().addOrder(view.MenuImpl.getInstance().getUser());
							view.Main.setOrderNo(newOder);
							productList();
							return;
						case 2 : MenuImpl.getInstance().commonMenu(GUEST_GOODS);
							return;
						}
						break;
					} 
					System.out.println("-----------------------------------------"
							+ "\n\t\t 상점에 구비된 물건이 부족합니다.");
					// 수량 부족, 다시 앞으로
				} 
				// 문자열 입력. 다시 앞으로.
			}
			break;
		case 2 : MenuImpl.getInstance().commonMenu(GUEST_GOODS);
			break;
		}
		
		User user = MenuImpl.getInstance().getUser();
		
		// 고객 메뉴 만들고 오더 상품 추가 넣기
		UserList.getInstance().orderAdd(user);
	}

	// 결제 요청한 주문을 환불 요청하는 기능
	@Override
	public void refund() throws IOException, NumberFormatException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		System.out.println("-----------------------------------------"
				+ "\n 영수증을 확인합니다. 주문 번호를 입력해주세요."
				+ "\n-----------------------------------------");
		String orderNo = view.Main.br.readLine();
		if(MenuImpl.getInstance().onlyNum(orderNo)) {
			Order o = OrderList.getInstance().getOrder(Integer.parseInt(orderNo));
			o.showOrder();
			System.out.println("-----------------------------------------"
					+ "\n\t 이 구매 내역을 환불하러 갈까요?"
					+ "\n\t\t 1. 환불하기 2. 광장으로 돌아가기"
					+ "\n-----------------------------------------");
			int answer = view.Main.answer(2);
			switch(answer) {
			case 1 : 
				OrderList.getInstance().addRefund(Integer.parseInt(orderNo));
				System.out.println("-----------------------------------------"
						+ "\n " + orderNo + "의 환불을 요청했습니다."
						+ "\n-----------------------------------------");
				MenuImpl.getInstance().commonMenu(GUEST_MENU);
				return;
			case 2 : MenuImpl.getInstance().commonMenu(GUEST_MENU);
				break;
			}
		} else {
			MenuImpl.getInstance().commonMenu(GUEST_ORDER_LIST);
		}
	}

	// 카트의 물건을 수정하는 실제 기능(너무 길어서 아래로 뺌)
	private void cartMod(int answer, String orderNo, Order order) throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		while(true) {
			Accessary acs;
			switch(answer) {
			case 2 : // 해당 상품 개수 변경
				System.out.println("\n-----------------------------------------"
						+ "\n\t 어떤 물건의 수량을 변경할까요?"
						+ "\n\t 상품 번호 또는 상품명을 입력하세요."
						+ "\n-----------------------------------------");
				String prd = view.Main.br.readLine();
				if(view.MenuImpl.getInstance().checkNum(prd)) {
					acs = order.getBuyProduct(Integer.parseInt(prd)-1);
				} else {
					acs = order.getBuyProduct(prd);
				}
				System.out.println("\n-----------------------------------------"
						+ "\n\t 담고 싶은 수량을 입력하세요."
						+ "\n-----------------------------------------");
				String count = view.Main.br.readLine();
				if(view.MenuImpl.getInstance().onlyNum(count)) {
					OrderList.getInstance().modProduct(order.getOrderNo(), acs, Integer.parseInt(count));
					cartList();
					return;
				}
			case 3 : // 해당 상품 삭제
				System.out.println("\n-----------------------------------------"
						+ "\n\t 어떤 물건을 꺼낼까요?"
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
					cartList();
					break;
				case 2 : cartList();
					break;
				}							
				break;
			}
		}
	}
}
