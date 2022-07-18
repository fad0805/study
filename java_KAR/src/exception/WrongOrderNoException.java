package exception;

/*
 * 작성일 : 2022년 07월 09-15일
 * 작성자 : 김아람
 * 설명  : 주문 번호를 잘못 입력했을 때 뜨는 예외
 */
public class WrongOrderNoException extends Exception {
	public WrongOrderNoException(String message) {
		super(message);
	}
}
