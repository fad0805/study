package exception;

/*
 * 작성일 : 2022년 07월 09-15일
 * 작성자 : 김아람
 * 설명  : 아이디 형식이 잘못 되었을 때 뜨는 예외
 */
public class WrongIdFormatException extends Exception {
	public WrongIdFormatException(String message) {
		super(message);
	}
}
