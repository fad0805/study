package model;

public interface Code {
	 // 상수
    static final int SHOP_LOGIN = 999; // 로그인
    static final int SHOP_EXIT = 000; // 상점 종료
    
    // 관리자
    static final int HOST_MENU = 100; // 관리자
    static final int HOST_MENU_LOGIN = 199;  // 관리자 로그인
    
    // 관리자 - 재고관리
    static final int HOST_STOCK_MENU = 110; // 재고관리
    static final int HOST_GOODS_LIST = 111; // 상품목록
    static final int HOST_GOODS_ADD = 112; // 상품추가
    static final int HOST_GOODS_UPDATE = 113; // 상품수정
    static final int HOST_GOODS_DEL = 114; // 상품삭제
    
    // 관리자 - 주문관리
    static final int HOST_ORDER_MENU = 120; // 주문관리
    static final int HOST_ORDER_LIST = 121; // 주문목록
    
    // 관리자 - 결제기능
    static final int HOST_ORDER_CONFIRM = 122; // 결제승인
    static final int HOST_ORDER_CANCEL = 123; // 결제취소
    static final int HOST_ORDER_REFUND = 124;
    
    // 관리자 - 결산
    static final int HOST_ORDER_TOTAL = 130; // 결산

    static final int HOST_LOGOUT = 998;
    
    // ---------------------------------------------------
    
    // 고객 
    static final int GUEST_MENU = 200; // 고객
    static final int GUEST_MENU_LOGIN = 299; // 고객 로그인
    static final int GUEST_MENU_JOIN = 250; // 고객 신규 가입
    
    // 고객 - 마이페이지
    static final int GUEST_MTPAGE = 251;
    static final int GUEST_MYINFO_SHOW = 252;
    static final int GUEST_MYINFO_MOD = 253;
    static final int GUEST_EXCIT = 254;
    
    // 고객 - 상품목록
    static final int GUEST_GOODS = 210; // 상품리스트
    
    static final int GUEST_CART_LIST = 220; // 장바구니 리스트
    static final int GUEST_CART_ADD = 221; // 장바구니 담기
    static final int GUEST_CART_DEL = 222; // 장바구니 삭제
    static final int GUEST_CART_BUY = 223; // 장바구니 구매
   
    // 고객 - 바로구매
    static final int GUEST_NOW_BUY = 230; // 상품 바로구매
    
    static final int GUEST_ORDER_LIST = 231;
    static final int PURCHASE_COMPLETE = 1;
    static final int PURCHASE_NON_COMPLETE = 0;
    
    // 고객 - 환불
    static final int GUEST_REFUND = 240; // 환불
    
    static final int GUEST_LOGOUT = 997;
}
