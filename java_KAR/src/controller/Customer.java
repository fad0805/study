package controller;

import java.io.IOException;

import exception.EmptyProductException;
import exception.EmptyStockException;
import exception.WrongInputException;
import exception.WrongOrderNoException;

public interface Customer {
	// 고객 - 장바구니
	public void cartList() throws WrongOrderNoException, EmptyProductException, NumberFormatException, IOException, WrongInputException, CloneNotSupportedException, EmptyStockException; // 장바구니 리스트
	public void cartAdd() throws NumberFormatException, IOException, WrongInputException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException; // 장바구니 추가
	public void cartRemove() throws IOException, EmptyProductException, WrongOrderNoException, NumberFormatException, WrongInputException, CloneNotSupportedException, EmptyStockException; // 장바구니 삭제
	public void cartBuy() throws NumberFormatException, IOException, WrongInputException, WrongOrderNoException, EmptyProductException, CloneNotSupportedException, EmptyStockException; // 장바구니 구매
	
	// 고객 - 바로구매
	public void nowBuy() throws IOException, EmptyProductException, WrongOrderNoException, NumberFormatException, WrongInputException, CloneNotSupportedException, EmptyStockException; // 바로구매
	
	// 고객 - 환불
	public void refund() throws IOException, NumberFormatException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException; // 환불
}
