package view;

import java.io.IOException;

import exception.EmptyProductException;
import exception.EmptyStockException;
import exception.WrongInputException;
import exception.WrongOrderNoException;

public interface Menu {	
	
	public void commonMenu(int num) throws IOException, NumberFormatException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException; // 공통 메뉴
	public void join() throws IOException, NumberFormatException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException; // 회원가입
	public void login() throws IOException, NumberFormatException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException;
	
	public void adminMenu() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException; // 관리자 메뉴 (1. 재고 관리. 2. 주문 관리 3. 결산 4. 로그아웃)
	public void adminStockMenu() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException; // 관리자 재고 관리 메뉴 (1. 제품 목록 2. 제품 추가 3. 제품 수정 4. 제품 삭제 5. 이전)
	public void adminOrderMenu() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException; // 관리자 주문 관리 메뉴 (1. 주문 목록 2. 구매 승인 3. 환불 처리 4. 이전화면)
	public void adminTotalMenu() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException; // 관리자 결산 메뉴 (1. 결산 2. 이전화면)
	
	public void customerMenu() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException; // 고객 메뉴 (1. 장바구니 2. 바로구매 3. 환불 4. 로그아웃)
	public void customerCart() throws NumberFormatException, WrongOrderNoException, EmptyProductException, IOException, WrongInputException, CloneNotSupportedException, EmptyStockException; // 고객 장바구니 메뉴 (1. 장바구니 목록 2. 제품 추가 3. 목록 삭제 4. 제품 구매 4. 이전화면)
}
