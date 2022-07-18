package model;

import exception.EmptyStockException;

/*
 * 작성일 : 2022년 07월 09-15일
 * 작성자 : 김아람
 * 설명  : 상품 개별 정보를 담은 클래스
 */
public class Accessary implements AccessaryCategory, Cloneable {
	// 멤버변수
	private int category;
	private String name;
	private int productPrice;
	private int productCount;
	
	public Accessary(int category, String name, int productPrice, int productCount){
		this.category = category;
		this.name = name;
		this.productPrice = productPrice;
		this.productCount = productCount;
	}
	
	
	// 카테고리명을 리턴
	public String getCategoryName() {
		if(this.category == EARRING) return "귀걸이";
		else if(this.category == NECKLACE) return "목걸이";
		else if(this.category == KEYRING) return "키링";
		else if(this.category == ETC) return "기타";
		else if(this.category == ALL) return "전체";
		return "카테고리가 입력되지 않았습니다";
	}
	
	// 재고량을 수정
	public boolean countChange(int count) throws EmptyStockException{
		if(this.productCount-count < 0){
			System.out.println("-----------------------------------------"
					+ "\n\t재고가 부족합니다."
					+ "\n-----------------------------------------");
			throw new EmptyStockException("재고가 부족해 물건을 살 수 없습니다.");
		}
		else {
			this.productCount -= count;
			return true;
		}
	}
	
	// setter getter
	public int getCategory() {
		return category;
	}
			
	public void setCategory(int category) {
		this.category = category;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getProductPrice() {
		return productPrice;
	}
	
	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}
	
	public int getProductCount() {
		return productCount;
	}
	
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	
	// 클론
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	// toString
	@Override
	public String toString() {
		return getCategoryName() + "\t" + name + "\t" + productPrice + "\t" + productCount;
	}
}
