package model;

import java.io.IOException;
import java.util.ArrayList;

import exception.EmptyProductException;
import exception.EmptyStockException;
import exception.WrongOrderNoException;

/*
 * 작성일 : 2022년 07월 09-15일
 * 작성자 : 김아람
 * 설명  : 개별 주문 인스턴스
 */
public class Order implements Code {
	private int orderNo;
	private User user;
	private ArrayList<Accessary> buylist;
	private int totalPrice;
	private int deliveryFee = 3000;
	private int permission = HOST_ORDER_CANCEL;
	private int purchaseCheck = PURCHASE_NON_COMPLETE;
	
	// 디폴트 생성자 - 오더 넘버 랜덤 생성 및 상품을 넣은 어레이리스트 생성
	public Order(User user) {
		buylist = new ArrayList< >();
		this.orderNo = (int)(Math.random()*1000) + 1000;
		this.user = user;
	}
	
	// 상품 추가
	public void addProduct(Accessary acs) throws IOException {
		buylist.add(acs);
	}
	
	// 상품 수량 수정
	public boolean modProduct(Accessary acs, int count) {
		if(buylist.contains(acs)) {
			for(int i=0; i<buylist.size(); i++) {
				if(buylist.get(i).equals(acs)) {
					acs.setProductCount(count);
					buylist.set(i, acs);
					return true;
				}
			}
		}
		System.out.println("-----------------------------------------"
				+ "\n\t해당 상품을 장바구니에 담지 않았습니다."
				+ "\n-----------------------------------------");
		return false;
	}
	
	// 상품 삭제 - 상품 단위
	public boolean removeProduct(Accessary acs) {
		for(int i=0; i<buylist.size(); i++) {
			if(buylist.get(i).equals(acs)) {
				buylist.remove(i);
				return true;
			}
		}
		System.out.println("-----------------------------------------"
				+ "\n\t해당 상품을 장바구니에 담지 않았습니다."
				+ "\n-----------------------------------------");
		return false;
	}
	
	// 주문 전체 가격 계산
	public void totalCal() {
		this.totalPrice = 0;
		for(int i=0; i<buylist.size(); i++) {
			this.totalPrice += buylist.get(i).getProductPrice() * buylist.get(i).getProductCount();
		}
	}
	
	// 결제해야할 금액 계산
	public int paymentPrice() {
		return totalPrice + deliveryFee;
	}
	
	// 주문 내역 출력
	public void showOrder() throws EmptyProductException {
		totalCal();
		if(purchaseCheck == PURCHASE_COMPLETE) {
			System.out.println("-----------------------------------------"
					+ "\n " + orderNo + " : " + user.getName() + "님의 주문입니다"
					+ "\n-----------------------------------------"
					+ "\n 번호\t카테고리\t이름\t가격\t구매량");
		} else if(purchaseCheck == PURCHASE_NON_COMPLETE) {
			System.out.println("-----------------------------------------"
					+ "\n " + orderNo + " : " + user.getName() + "님의 장바구니입니다"
					+ "\n-----------------------------------------"
					+ "\n 번호\t카테고리\t이름\t가격\t구매량");
		}
		
		for(int i=0; i<buylist.size(); i++) {
			System.out.println((i+1) + " " + buylist.get(i));
		}
		
		System.out.println("-----------------------------------------"
				+ "\n 상품 총합 : " + totalPrice
				+ "\n 배송비 : " + deliveryFee
				+ "\n-----------------------------------------"
				+ "\n 결제 금액 : " + paymentPrice());
		
		if(purchaseCheck == PURCHASE_COMPLETE) {
			System.out.println(" 승인 상태 : " + showPermission()
					+ "\n-----------------------------------------");
		} else if(purchaseCheck == PURCHASE_NON_COMPLETE) {
			// 더 띄우지 않기
		}
	}
	
	// 주문 내역에 존재하는 상품 리턴 - 상품명 조회
	public Accessary getBuyProduct(String name) throws EmptyProductException {
		for(int i=0; i<buylist.size(); i++) {
			if(buylist.get(i).getName().equals(name)) {
				return buylist.get(i);
			}
		}
		noOne();
		return null;
	}
	
	// 주문 내역에 존재하는 상품 리턴 - 인덱스 조회
	public Accessary getBuyProduct(int index) throws EmptyProductException {
		if(index>=0 && index<buylist.size()) {
			return buylist.get(index);
		}
		noOne();
		return null;
	}
	
	// 환불
	public void refund() throws EmptyProductException, EmptyStockException, WrongOrderNoException {
		purchaseCheck = PURCHASE_NON_COMPLETE;
		for(int i=0; i<buylist.size(); i++) {
			int count = buylist.get(i).getProductCount();
			String name = buylist.get(i).getName();
			int index = AccessaryList.getInstance().getIndex(name);
			Accessary acs = AccessaryList.getInstance().getProduct(index);
			AccessaryList.getInstance().countChange(index, acs, count);
		}
		OrderList.getInstance().remove(orderNo);
	}
	
	// 결제 승인
	public void buyConfirm() throws EmptyProductException, EmptyStockException {
		for(int i=0; i<buylist.size(); i++) {
			String name = buylist.get(i).getName();
			int count  = buylist.get(i).getProductCount();
			
			int index = AccessaryList.getInstance().getIndex(name);
			Accessary acs = AccessaryList.getInstance().getProduct(index);
			
			AccessaryList.getInstance().countChange(index, acs, count);
		}
	}
	
	// 결제 승인 상태 출력
	public String showPermission() {
		if(permission == HOST_ORDER_CONFIRM) {
			return "주문 승인";
		} else if(permission == HOST_ORDER_CANCEL) {
			return "주문 미승인";
		}
		return null;
	}
	
	// getter setter
	public int getOrderNo() {
		return orderNo;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public int getTotalPrice() {
		return totalPrice;
	}
	
	public int getPermission() {
		return permission;
	}
	
	public int getPurchaseCheck() {
		return purchaseCheck;
	}
	
	public void setPurchaseCheck(int purchaseCheck) {
		this.purchaseCheck = purchaseCheck;
	}
	
	public void setPermission(int permission) {
		this.permission = permission;
	}

	// 해당 상품이 존재하지 않습니다
	public void noOne() throws EmptyProductException {
		System.out.println("-----------------------------------------"
				+ "\n    해당 상품이 존재하지 않습니다."
				+ "\n-----------------------------------------");
		throw new EmptyProductException("존재하지 않는 상품을 입력하였습니다.");
	}
}
