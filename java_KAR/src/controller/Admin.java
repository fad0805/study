package controller;

import java.io.IOException;

import exception.EmptyProductException;
import exception.EmptyStockException;
import exception.WrongInputException;
import exception.WrongOrderNoException;

public interface Admin {
	public static final String ID = "admin";
	public static final String PASSWORD = "admin";
	
	// 관리자 - 재고관리
	public void productList() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException; // 상품 목록
	public void productAdd() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException; // 상품 추가
	public void productUpdate() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException; // 상품 수정
	public void productRemove() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException; // 상품 삭제
	
	// 관리자 - 주문관리
	public void orderList() throws EmptyProductException, NumberFormatException, IOException, WrongInputException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException; // 주문 목록
	public void orderConfirm() throws EmptyProductException, NumberFormatException, IOException, WrongInputException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException; // 결제 승인
	public void orderCancel() throws EmptyProductException, NumberFormatException, IOException, WrongInputException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException; // 결제 취소
	
	// 관리자 - 결산
	public void saleTotal() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException; // 결산
}
