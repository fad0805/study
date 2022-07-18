package exception;

/*
 * 작성일 : 2022년 07월 09-15일
 * 작성자 : 김아람
 * 설명  : 조회한 상품이 존재하지 않을 시 뜨는 예외
 */
public class EmptyProductException extends Exception {
	public EmptyProductException(String message) {
		super(message);
	}
}