package exception;

/*
 * 작성일 : 2022년 07월 09-15일
 * 작성자 : 김아람
 * 설명  : 물건을 결제 승인할 때 해당 상품의 재고가 없을 경우 뜨는 예외
 */
public class EmptyStockException extends Exception {
	public EmptyStockException(String message) {
		super(message);
	}
}
