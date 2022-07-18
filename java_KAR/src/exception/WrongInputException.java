package exception;

/*
 * 작성일 : 2022년 07월 09-15일
 * 작성자 : 김아람
 * 설명  : 선택지 입력을 잘못 했을 때 뜨는 예외
 */
public class WrongInputException extends Exception {
	public WrongInputException(String message) {
		super(message);
	}
}
