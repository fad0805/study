package model;

import java.util.ArrayList;

/*
 * 작성일 : 2022년 07월 09-15일
 * 작성자 : 김아람
 * 설명  : 유저 정보를 담은 클래스
 */
public class User {
	private String id;
	private String password;
	private String name;
	private ArrayList<Accessary> cart;
	
	public User(){}
	public User(String id, String password, String name) {
		this.id = id;
		this.password = password;
		this.name = name;
		cart = new ArrayList< >();
	}
	
	// getter setter
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Accessary> getCart() {
		return cart;
	}
	
	@Override
	public String toString() {
		return id + "\t\t" + name;
	}
	
	// 회원 정보 출력
	public void showUserInfo() {
		System.out.println("-----------------------------------------"
				+ "\n\t이메일 \t\t회원명"
				+ "\n\t" + toString()
				+ "\n-----------------------------------------");
	}
	
	@Override
	public boolean equals(Object obj) {
		return id.equals(obj);
	}
	
}
