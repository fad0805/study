package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import exception.EmptyProductException;
import exception.EmptyStockException;
import exception.WrongOrderNoException;

/*
 * 작성일 : 2022년 07월 09-15일
 * 작성자 : 김아람
 * 설명  : 주문 리스트. 카트 역할을 겸한다. - 싱글톤 적용
 */
public class OrderList implements Code {
	
	private HashMap<Integer, Order> hmap;
	private ArrayList<Integer> refund;
	private static OrderList list = new OrderList();
	
	private OrderList() {
		hmap = new HashMap< >();
		refund = new ArrayList< >();
	}
	
	public static OrderList getInstance() {
		if(list == null)
			list = new OrderList();
		return list;
	}
	
	// 새로운 오더 생성
	public int addOrder(User user) {
		Order order = new Order(user);
		hmap.put(order.getOrderNo(), order);
		return order.getOrderNo();
	}
	
	// 주문번호에 새로운 유저를 셋팅
	public boolean modOrder(int orderNo, User user) {
		if(hmap.containsKey(orderNo)) {
			hmap.get(orderNo).setUser(user);
			return true;
		}
		return false;
	}
	
	// 주문 삭제
	public boolean remove(int orderNo) throws WrongOrderNoException {
		if(hmap.containsKey(orderNo)) {
			hmap.remove(orderNo);
			return true;
		}
		noOne();
		return false;
	}
	
	// 특정 주문에 상품 추가
	public boolean addProduct(int orderNo, Accessary acs) throws WrongOrderNoException, IOException {
		if(hmap.containsKey(orderNo)) {
			Order order = hmap.get(orderNo);
			order.addProduct(acs);
			hmap.put(orderNo, order);
			return true;
		}
		noOne();
		return false;
	}

	// 특정 주문에 있는 상품 수정
	public boolean modProduct(int orderNo, Accessary acs, int count) throws WrongOrderNoException {
		if(hmap.containsKey(orderNo)) {
			Order order = hmap.get(orderNo);
			order.modProduct(acs, count);
			hmap.put(orderNo, order);
			return true;
		}
		noOne();
		return false;
	}
	
	// 특정 주문에 있는 상품 삭제
	public boolean removeProduct(int orderNo, Accessary acs) throws WrongOrderNoException {
		if(hmap.containsKey(orderNo)) {
			Order order = hmap.get(orderNo);
			order.removeProduct(acs);
			return true;
		}
		noOne();
		return false;
	}
	
	// 주문 인스턴스 리턴 - 주문 번호
	public Order getOrder(int orderNo) throws WrongOrderNoException {
		if(hmap.containsKey(orderNo)) {
			return hmap.get(orderNo);
		}
		noOne();
		return null;
	}
	
	// 전체 주문 목록 출력
	public int showAllList() throws EmptyProductException {
		System.out.println("\n-----------------------------------------"
				+ "\n\t\t 주문 목록"
				+ "\n-----------------------------------------\n");
		int i = 0;
		for(Map.Entry<Integer, Order> e : hmap.entrySet()) {
			if(e.getValue().getPurchaseCheck() == PURCHASE_COMPLETE) {
				e.getValue().showOrder();
				System.out.println();
				i++;
			}
		}
		return i;
	}

	// 승인된 주문 목록 출력
	public int showPermissionList() throws EmptyProductException {
		System.out.println("\n-----------------------------------------"
				+ "\n\t\t 승인한 주문 목록"
				+ "\n-----------------------------------------\n");
		int i = 0;
		for(Map.Entry<Integer, Order> e : hmap.entrySet()) {
			if(e.getValue().getPurchaseCheck() == PURCHASE_COMPLETE) {
				if(e.getValue().getPermission() == HOST_ORDER_CONFIRM) {
					e.getValue().showOrder();
					System.out.println();
					i++;
				}
			}
		}
		System.out.println("-----------------------------------------");
		return i;
	}
	
	// 승인되지 않은 주문 목록 출력
	public int showNonPermissionList() throws EmptyProductException {
		System.out.println("\n-----------------------------------------"
				+ "\n\t\t 승인되지 않은 주문 목록"
				+ "\n-----------------------------------------\n");
		int i = 0;
		for(Map.Entry<Integer, Order> e : hmap.entrySet()) {
			if(e.getValue().getPurchaseCheck() == PURCHASE_COMPLETE) {
				if(e.getValue().getPermission() == HOST_ORDER_CANCEL) {
					e.getValue().showOrder();
					System.out.println();
					i++;
				}
			}
		}
		System.out.println("-----------------------------------------");
		return i;
	}
	
	// 특정 유저의 주문 목록 출력
	public void showOnesList(User user) throws WrongOrderNoException {
		System.out.println("\n-----------------------------------------"
				+ "\n\t\t 주문 정보"
				+ "\n-----------------------------------------");
		try {
			for(Map.Entry<Integer, Order> e : hmap.entrySet()) {
				if(e.getValue().getUser().getName().equals(user.getName())){
					if(e.getValue().getPurchaseCheck() == PURCHASE_COMPLETE) {
						e.getValue().showOrder();
					}
				}
			}
			return;
		} catch (Exception e1) {
			System.out.println(" 주문한 유저 정보가 존재하지 않습니다.");
		}
	}
	
	// 주문 번호가 존재하지 않습니다
	public void noOne() throws WrongOrderNoException {
		System.out.println("-----------------------------------------"
				+ "\n\t잘못된 주문 번호입니다."
				+ "\n-----------------------------------------");
		throw new WrongOrderNoException("잘못된 주문 번호를 입력하였습니다.");
	}
	
	// 가게 전체 수익 계산
	public void total() {
		int totalIncome = 0;
		for(Map.Entry<Integer, Order> e : hmap.entrySet()){
			if(e.getValue().getPermission() == HOST_ORDER_CONFIRM && e.getValue().getPurchaseCheck() == PURCHASE_COMPLETE){
				e.getValue().totalCal();
				totalIncome += e.getValue().getTotalPrice();
			}
		}
		System.out.println("-----------------------------------------"
				+ "\n 총 수익 : " + totalIncome
				+ "\n-----------------------------------------");
	}
	
	// 환불 요청 목록 추가
	public void addRefund(int orderNo) {
		refund.add(orderNo);
	}
	
	// 환불 요청 주문 목록 출력
	public void showRefundList() throws EmptyProductException {
		for(int i=0; i<refund.size(); i++) {
			hmap.get(refund.get(i)).showOrder();
			System.out.println();
		}
	}
	
	// 환불 요청 목록 전체 환불
	public boolean allRefund() throws EmptyProductException, EmptyStockException, WrongOrderNoException {
		if(refund.isEmpty()) {
			System.out.println("-----------------------------------------"
					+ "\n 환불 요청된 주문 목록이 없습니다."
					+ "\n-----------------------------------------");
			return false;
		} else {
			for(Map.Entry<Integer, Order> e : hmap.entrySet()) {
				for(int i=0; i<refund.size(); i++) {
					if(refund.get(i) == e.getKey()) {
						e.getValue().refund();
					}
				}
			}
			return true;
		}
	}
	
	// 환불 요청 목록 전체 삭제
	public void refundRemove() {
		if(refund.isEmpty()) {
			System.out.println("-----------------------------------------"
					+ "\n 환불 요청된 주문 목록이 없습니다."
					+ "\n-----------------------------------------");
		} else {
			for(Map.Entry<Integer, Order> e : hmap.entrySet()) {
				for(int i=0; i<refund.size(); i++) {
					if(refund.get(i) == e.getKey()) {
						hmap.remove(e.getKey());
						refund.clear();
					}
				}
			}
		}
	}
}
