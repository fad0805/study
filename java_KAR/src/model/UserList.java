package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import exception.EmptyProductException;
import exception.EmptyStockException;
import exception.WrongInputException;
import exception.WrongOrderNoException;
import view.MenuImpl;

/*
 * 작성일 : 2022년 07월 09-15일
 * 작성자 : 김아람
 * 설명  : 유저 목록 - 회원 가입, 회원 정보 수정, 회원 탈퇴, 로그인 가능 - 싱글톤 적용
 */
public class UserList implements Code {
	private HashMap<String, User> hmap;
	private static UserList list = new UserList();
	
	private UserList() {
		hmap = new HashMap< >();
		hmap.put(null, new User(null, null, "비회원"));
	}
	public static UserList getInstance() {
		if(list == null)
			list = new UserList();
		return list;
	}
	
	// 파일을 읽어 기존 회원 정보 입력
	public void start() throws NumberFormatException, IOException {
		BufferedReader fileList = new BufferedReader(new FileReader(".\\data\\userList.txt"));
		
		String products;
		while((products = fileList.readLine()) != null){
			String[] acs = products.split("/");
			hmap.put(acs[0], new User(acs[0], acs[1],  acs[2]));
		}
		fileList.close();
	}
	
	// 회원 가입
	public void add() throws IOException {
		System.out.println("-----------------------------------------"
				+ "\n\t\t회원 가입"
				+ "\n-----------------------------------------");
		System.out.print("  이메일 : ");
		String id = view.Main.br.readLine();
		if(checkIdFormat(id)) {
			if(hmap.containsKey(id)) {
				System.out.println("-----------------------------------------"
						+ "\n\t  이미 존재하는 아이디입니다"
						+ "\n-----------------------------------------");
				return;
			}
			System.out.print("\n  비밀번호 : ");
			String password = view.Main.br.readLine();
			System.out.print("\n  이름 : ");
			String name= view.Main.br.readLine();
			hmap.put(id, new User(id, password, name));
			System.out.println("\n-----------------------------------------"
					+ "\n\t  잃어버린 섬의 주민이 되신 것을 환영합니다."
					+ "\n\t함께 즐거운 시간 보내요, " + name + "님!"
					+ "\n-----------------------------------------");
		}
	}
	
	// 개별 유저 정보 출력
	public User showUserInfo(String id) throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException{
		if(hmap.containsKey(id)) {
			hmap.get(id).showUserInfo();

			System.out.println("-----------------------------------------"
					+ "\n\t  주민 정보를 수정하시겠습니까?"
					+ "\n   1. 수정하기 2. 탈퇴하기 3. 입구로 돌아가기"
					+ "\n-----------------------------------------");
			int answer = view.Main.answer(3);
			
			switch(answer) {
			case 1 : 
				mod(hmap.get(id));
				return hmap.get(id);
			case 2 : 
				remove(hmap.get(id));
				return hmap.get(id);
			case 3 : 
				MenuImpl.getInstance().commonMenu(GUEST_MENU);
				return hmap.get(id);
			}
		}
		noOne();
		return null;
	}
	
	// 회원 정보 수정
	public void mod(User user) throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		System.out.println("-----------------------------------------"
				+ "\n\t    주민 정보를 수정합니다."
				+ "\n\t1. 비밀번호 수정 2. 주민명 수정"
				+ "\n-----------------------------------------"
				+ "\n\t  비밀번호를 다시 입력해 주세요");
		if(user.getPassword().equals(view.Main.br.readLine())) {
			int answer = view.Main.answer(2);
			if(answer == 1) {
				System.out.print("\n\n  새 비밀번호 : ");
				String password = view.Main.br.readLine();
				
				hmap.get(user.getId()).setPassword(password);
				System.out.println("-----------------------------------------"
						+ "\n\t  비밀번호가 수정되었습니다."
						+ "\n-----------------------------------------");
			}
			else if (answer == 2) {
				System.out.print("\n\n  변경할 이름 : ");
				String name= view.Main.br.readLine();
				
				hmap.get(user.getId()).setName(name);
				System.out.println("-----------------------------------------"
						+ "\n\t   주민명이 수정되었습니다."
						+ "\n-----------------------------------------");
			}
		}
		else {
			System.out.println("-----------------------------------------"
					+ "\n\t    비밀번호가 틀렸습니다."
					+ "\n-----------------------------------------");
			MenuImpl.getInstance().commonMenu(GUEST_MENU);
		}
	}
	
	// 회원 삭제(탈퇴)
	public void remove(User user) throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		System.out.println("-----------------------------------------"
				+ "\n\t잃어버린 섬을 영원히 떠나실 건가요?"
				+ "\n\t 1. 탈퇴하기 2. 입구로 돌아가기"
				+ "\n-----------------------------------------");
		switch(view.Main.answer(2)) {
		case 1 : 
			System.out.println("-----------------------------------------"
					+ "\n\t안녕히, 안녕히. 그동안 즐거웠어요."
					+ "\n\n     ▶️ " + user.getName() + "님의 주민 정보가 삭제되었습니다."
					+ "\n-----------------------------------------");
			hmap.remove(user.getId());
			break;
		case 2 : 
			MenuImpl.getInstance().commonMenu(GUEST_MENU);
			break;
		}
	}
	
	// 로그인
	public boolean login(String id) throws IOException, NumberFormatException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		if(id.equals("admin")) {
			System.out.print("\n  비밀번호 : ");
			String password = view.Main.br.readLine();
			if(password.equals("admin")) {
				System.out.println("-----------------------------------------"
						+ "\n\t    관리자로 로그인하였습니다"
						+ "\n-----------------------------------------");
				MenuImpl.getInstance().commonMenu(HOST_MENU);
				MenuImpl.getInstance().setUser(null);
				return true;
			}
		}
		else if(hmap.containsKey(id)) {
			System.out.print("\n  비밀번호 : ");
			String password = view.Main.br.readLine();
			if(hmap.get(id).getPassword().equals(password)) {
				System.out.println("-----------------------------------------"
						+ "\n\t어서오세요, " + hmap.get(id).getName() + "님"
						+ "\n\t       잃어버린 섬에서 즐거운 시간 되세요."
						+ "\n-----------------------------------------");
				OrderList.getInstance().modOrder(view.Main.getOrderNo(), hmap.get(id)); // 장바구니에 아이디 셋팅
				MenuImpl.getInstance().setUser(hmap.get(id));
				MenuImpl.getInstance().commonMenu(GUEST_MENU);
				return true;
			}
		}
		noOne();
		return false;
	}
	
	// 전체 회원 목록 출력
	public void showAllInfo() throws EmptyProductException{
		if(hmap.isEmpty()) {
			noOne();
			return;
		}
		System.out.println("-----------------------------------------"
				+ "\n\t  이메일  \t\t주민명"
				+ "\n-----------------------------------------");
		for(Map.Entry<String, User> entry : hmap.entrySet()) {
			System.out.println("    " + entry.getValue());
		}
		System.out.println("-----------------------------------------");
	}
	
	public String printOneInfo(String id) throws EmptyProductException {
		if(hmap.containsKey(id)) {
			return hmap.get(id).toString();
		}
		else{
			noOne();
			return null;
		}
	}
	
	// 회원이 존재하지 않습니다 - 예외
	public void noOne() throws EmptyProductException {
		System.out.println("-----------------------------------------"
				+ "\n\t    주민이 존재하지 않습니다"
				+ "\n-----------------------------------------");
		throw new EmptyProductException("존재하지 않는 주민을 입력하였습니다.");
	}
	
	// 아이디 형식 체크
	public boolean checkIdFormat(String id){
		if(id.contains("@")) {
			if(id.contains(".")) {
				return true;
			}
		}
		System.out.println("-----------------------------------------"
				+ "\n\t  메일 형식이 잘못되었습니다."
				+ "\n-----------------------------------------");
		return false;
	}
	
	// ?? 뭔지 기억이 안 나는데 어디에 엮여있을지 몰라서 못 지우겠습니다
	public void orderAdd(User user) {
		if(hmap.containsValue(user)) {
			hmap.put(user.getId(), user);
		}
		else {
			hmap.put(null, new User(null, null, "비회원"));
		}
	}
	
	// 회원 인스턴스 리턴
	public User getUser(String id) {
		return hmap.get(id);
	}
}
