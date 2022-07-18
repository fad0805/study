package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import controller.AdminImpl;
import exception.EmptyProductException;
import exception.EmptyStockException;
import exception.WrongInputException;
import exception.WrongOrderNoException;
import view.MenuImpl;

/*
 * 작성일 : 2022년 07월 09-15일
 * 작성자 : 김아람
 * 설명  : 어레이 리스트를 사용한 상품 리스트 - 싱글톤 적용
 */
public class AccessaryList implements AccessaryCategory, Code {
	private ArrayList<Accessary> arr;
	private static AccessaryList list = new AccessaryList();
	
	private AccessaryList() {
		arr = new ArrayList< >();
	}
	
	// 싱글톤 적용된 인스턴스 주소 획득
	public static AccessaryList getInstance() {
		if(list == null)
			list = new AccessaryList();
		return list;
	}
	
	// 파일을 읽어 등록된 상품 정보를 읽어들임
	public void start() throws NumberFormatException, IOException {
		BufferedReader fileList = new BufferedReader(new FileReader(".\\data\\productList.txt"));
		
		String products;
		while((products = fileList.readLine()) != null){
			String[] acs = products.split("/");
			arr.add(new Accessary(Integer.parseInt(acs[0]), acs[1],  Integer.parseInt(acs[2]), Integer.parseInt(acs[3])));
		}
		fileList.close();
	}
	
	// 상품 추가 - 카테고리 선택
	public void add() throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		while(true) {
			System.out.println("-----------------------------------------"
					+ "\n\t     상품을 추가합니다."
					+ "\n-----------------------------------------"
					+ "\n\t       카테고리 선택"
					+ "\n    1. 귀걸이 2. 목걸이 3. 키링 4. 기타"
					+ "\n-----------------------------------------");
			int answer = view.Main.answer(4);
			int category;
			switch(answer) {
			case 1 : category = EARRING;
				addProductAfter(category);
				AdminImpl.getInstance().setProductIndex(arr.size());
				AdminImpl.getInstance().switchNowProduct(AdminImpl.getInstance().getNowProduct());
				return;
			case 2 : category = NECKLACE;
				addProductAfter(category);
				AdminImpl.getInstance().setProductIndex(arr.size());
				AdminImpl.getInstance().switchNowProduct(AdminImpl.getInstance().getNowProduct());
				return;
			case 3 : category = KEYRING;
				addProductAfter(category);
				AdminImpl.getInstance().setProductIndex(arr.size());
				AdminImpl.getInstance().switchNowProduct(AdminImpl.getInstance().getNowProduct());
				return;
			case 4 : category = ETC;
				addProductAfter(category);
				AdminImpl.getInstance().setProductIndex(arr.size());
				AdminImpl.getInstance().switchNowProduct(AdminImpl.getInstance().getNowProduct());
				return;
			}
		}
	}
	
	// 상품 수정
	public void mod(int index) throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		if(index <= arr.size()) {
			System.out.println("-----------------------------------------"
					+ "\n\t" + arr.get(index).getName() + " 상품을 수정합니다."
					+ "\n-----------------------------------------"
					+ "\n 번호\t카테고리\t이름\t가격\t재고량"
					+ "\n " + (index+1) + "\t" + arr.get(index)
					+ "\n-----------------------------------------"
					+ "\n\t무엇을 수정하시겠습니까?"
					+ "\n 1. 카테고리 2. 상품명 3. 가격 4. 재고량 5. 상품 목록 6. 재고 관리"
					+ "\n-----------------------------------------");
			int answer = view.Main.answer(4);
			switch(answer) {
			case 1 :
				System.out.println("-----------------------------------------"
						+ "\n\t       카테고리 선택"
						+ "\n    1. 귀걸이 2. 목걸이 3. 키링 4. 기타"
						+ "\n-----------------------------------------");
				Accessary mod = arr.get(index);
				answer = view.Main.answer(4);
				switch(answer) {
				case 1 : mod.setCategory(EARRING);
					arr.set(index, mod);
					break;
				case 2 :mod.setCategory(NECKLACE);
				arr.set(index, mod);
					break;
				case 3 : mod.setCategory(KEYRING);
				arr.set(index, mod);
					break;
				case 4 : mod.setCategory(ETC);
				arr.set(index, mod);
					break;
				}
				break;
			case 2 : 
				while(true){
					System.out.print("  새로운 상품명 :");
					String name = view.Main.br.readLine();
					if(name.isEmpty()) {
						System.out.println("\n-----------------------------------------"
								+ "\n\t이름을 비워둘 수 없습니다."
								+ "\n-----------------------------------------");
					}
					else {
						arr.get(index).setName(name);
						break;
					}
				}
				break;
			case 3 : 
				while(true){
					System.out.print("  새로운 가격 :");
					String price = view.Main.br.readLine();
					if(price.isEmpty()) {
						System.out.println("\n-----------------------------------------"
								+ "\n\t가격을 비워둘 수 없습니다."
								+ "\n-----------------------------------------");
					}
					else if(view.MenuImpl.getInstance().onlyNum(price)) {
						arr.get(index).setProductPrice(Integer.parseInt(price));
						break;
					}
				}
				break;
			case 4 : 
				while(true){
					System.out.print("  새로운 재고량 :");
					String count = view.Main.br.readLine();
					if(count.isEmpty()) {
						System.out.println("\n-----------------------------------------"
								+ "\n\t재고량을 비워둘 수 없습니다."
								+ "\n-----------------------------------------");
					}
					else if(view.MenuImpl.getInstance().onlyNum(count)) {
						arr.get(index).setProductCount(Integer.parseInt(count));
						break;
					}
				}
				break;
			case 5 : AdminImpl.getInstance().productList();
				break;
			case 6 : MenuImpl.getInstance().commonMenu(HOST_STOCK_MENU);
				break;
 			}
			System.out.printf("\n-----------------------------------------"
					+ "\n\t" + arr.get(index).getName() + " 상품을 수정하였습니다."
					+ "\n-----------------------------------------"
					+ "\n  번호   카테고리       이름       가격    재고량"
					+ "\n %3d", (index+1));
			System.out.println(arr.get(index)
					+ "\n-----------------------------------------");
		}
	}
	
	// 상품 삭제
	public void remove(int index) throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		if(index <= arr.size()) {
			System.out.println("\n-----------------------------------------"
					+ "\n\t " + arr.get(index).getName() + " 상품을 삭제하시겠습니까?"
					+ "\n\t    1. 삭제 2. 취소"
					+ "\n-----------------------------------------");
			int answer = view.Main.answer(2);
			switch(answer) {
			case 1 : 
				System.out.println("\n-----------------------------------------"
						+ "\n\t" + arr.get(index).getName() + " 상품을 삭제하였습니다."
						+ "\n-----------------------------------------");
				arr.remove(index);
				return;
			case 2 : MenuImpl.getInstance().commonMenu(HOST_STOCK_MENU);
				return;
			}
		}
		noOne();
	}
	
	// 전체 리스트 조회
	public int showAllList() {
		System.out.println("\n-----------------------------------------"
				+ "\n\t\t 상품 목록"
				+ "\n-----------------------------------------"
				+ "\n 번호\t카테고리\t이름\t가격\t재고량\n");
		int i;
		for(i=0; i<arr.size(); i++) {
			System.out.println(" " + (i+1) + "\t" + arr.get(i));
		}
		System.out.println("-----------------------------------------");
		return i;
	}
	
	// 귀걸이 리스트 조회
	public int earringList() {
		System.out.println("\n-----------------------------------------"
				+ "\n\t\t 상품 목록"
				+ "\n-----------------------------------------"
				+ "\n 번호\t카테고리\t이름\t가격\t재고량\n");
		int count = 0;
		for(int i=0; i<arr.size(); i++) {
			if(arr.get(i).getCategory() == EARRING) {
				System.out.println("  " + (i+1) + "\t" + arr.get(i));
				count++;
			}
		}
		System.out.println("-----------------------------------------");
		return count;
	}
	
	// 목걸이 리스트 조회
	public int necklaceList() {
		System.out.println("\n-----------------------------------------"
				+ "\n\t\t 상품 목록"
				+ "\n-----------------------------------------"
				+ "\n 번호\t카테고리\t이름\t가격\t재고량\n");
		int count = 0;
		for(int i=0; i<arr.size(); i++) {
			if(arr.get(i).getCategory() == NECKLACE) {
				System.out.println("  " + (i+1) + "\t" + arr.get(i));
				count++;
			}
		}
		System.out.println("-----------------------------------------");
		return count;
	}
	
	// 키링 리스트 조회
	public int keyringList() {
		System.out.println("\n-----------------------------------------"
				+ "\n\t\t 상품 목록"
				+ "\n-----------------------------------------"
				+ "\n 번호\t카테고리\t이름\t가격\t재고량\n");
		int count = 0;
		for(int i=0; i<arr.size(); i++) {
			if(arr.get(i).getCategory() == KEYRING) {
				System.out.println("  " + (i+1) + "\t" + arr.get(i));
				count++;
			}
		}
		System.out.println("-----------------------------------------");
		return count;
	}
	
	// 기타 리스트 조회
	public int etcList() {
		System.out.println("\n-----------------------------------------"
				+ "\n\t\t 상품 목록"
				+ "\n-----------------------------------------"
				+ "\n 번호\t카테고리\t이름\t가격\t재고량\n");
		int count = 0;
		for(int i=0; i<arr.size(); i++) {
			if(arr.get(i).getCategory() == ETC) {
				System.out.println("  " + (i+1) + "\t" + arr.get(i));
				count++;
			}
		}
		System.out.println("-----------------------------------------");
		return count;
	}
	
	// 개별 상품 정보 리턴 - 상품명
	public String getProductInfo(String name) throws EmptyProductException {
		for(int i=0; i<arr.size(); i++) {
			if(arr.get(i).getName().equals(name)) {
				return (i+1) + "\t" + arr.get(i).toString();
			}
		}
		noOne();
		return null;
	}

	// 개별 상품 인스턴스 리턴 - 상품명
	public Accessary getProduct(String name) throws EmptyProductException {
		for(int i=0; i<arr.size(); i++) {
			if(arr.get(i).getName().equals(name)) {
				return arr.get(i);
			}
		}
		noOne();
		return null;
	}
	
	// 개별 상품 정보 리턴 - 인덱스
	public String getProductInfo(int index) throws EmptyProductException {
		if(index>=0 && index<arr.size()) {
			return (index+1)+ "\t" + arr.get(index).toString();
		}
		noOne();
		return null;
	}
	
	// 개별 상품 인스턴스 리턴 - 인덱스
	public Accessary getProduct(int index) throws EmptyProductException {
		if(index>=0 && index<arr.size()) {
			return arr.get(index);
		}
		noOne();
		return null;
	}
	
	// 상품명으로 인덱스 확인
	public int getIndex(String name) throws EmptyProductException {
		for(int i=0; i<arr.size(); i++) {
			if(arr.get(i).getName().equals(name)) {
				return i;
			}
		}
		noOne();
		return 0;
	}
	
	// 인덱스로 상품명 확인
	public String getName(int index) {
		return arr.get(index-1).getName();
	}
	
	// 상품 인스턴스 복사 - 상품명
	public Accessary productCopy(String name) throws EmptyProductException, CloneNotSupportedException {
		Accessary toUser;
		for(int i=0; i<arr.size(); i++) {
			if(arr.get(i).getName().equals(name)) {
				toUser = (Accessary) arr.get(i).clone();
				toUser.setProductCount(0);
				return toUser;
			}
		}
		noOne();
		return null;
	}
	
	// 상품 인스턴스 복사 - 인덱스
	public Accessary productCopy(int index) throws EmptyProductException, CloneNotSupportedException {
		Accessary toUser;
		if(index>0 && index<=arr.size()) {
			toUser = (Accessary) arr.get(index-1).clone();
			toUser.setProductCount(0);
			return toUser;
		}
		noOne();
		return null;
	}
	
	// 해당 상품이 존재하지 않습니다
	public void noOne() throws EmptyProductException {
		System.out.println("-----------------------------------------"
				+ "\n    해당 상품이 존재하지 않습니다."
				+ "\n-----------------------------------------");
		throw new EmptyProductException("존재하지 않는 상품을 입력하였습니다.");
	}
	
	// 상품 추가 - 카테고리 선택 이후
	public boolean addProduct(int category) throws IOException {
		System.out.print("  상품명 : ");
		String name = view.Main.br.readLine();
		if(name.isEmpty()) {
			System.out.println("-----------------------------------------"
					+ "\n\t  상품명을 비워둘 수 없습니다."
					+ "\n-----------------------------------------");
			return false;
		}
		else {
			System.out.print("\n  가격 : ");
			String productPrice = view.Main.br.readLine();
			if(productPrice.isEmpty()) {
				System.out.println("-----------------------------------------"
						+ "\n\t   가격을 비워둘 수 없습니다."
						+ "\n-----------------------------------------");

				return false;
			}
			else if(view.MenuImpl.getInstance().onlyNum(productPrice)) {
				System.out.print("\n  재고량 : ");
				String productCount = view.Main.br.readLine();
				if(productCount.isEmpty()) {
					System.out.println("-----------------------------------------"
							+ "\n\t  재고량을 비워둘 수 없습니다."
							+ "\n-----------------------------------------");

					return false;
				}
				else if(view.MenuImpl.getInstance().onlyNum(productCount)) {
					Accessary newAccessary = new Accessary(category, name, Integer.parseInt(productPrice), Integer.parseInt(productCount));
					arr.add(newAccessary);
					System.out.println("-----------------------------------------"
							+ "\n\t" + name + " 상품을 추가하였습니다."
							+ "\n-----------------------------------------"
							+ "\n  번호   카테고리       이름       가격    재고량"
							+ "\n  " + (arr.indexOf(newAccessary)+1) + arr.get(arr.indexOf(newAccessary))
							+ "\n-----------------------------------------");
					return true;
				}
			}
		}
		return false;
	}
	
	// 상품 추가 후 선택지
	public void addProductAfter(int category) throws NumberFormatException, IOException, WrongInputException, EmptyProductException, WrongOrderNoException, CloneNotSupportedException, EmptyStockException {
		if(addProduct(category)) {
			System.out.println("-----------------------------------------"
					+ "\n\t상품을 계속 추가하시겠습니까?"
					+ "\n 1. 계속하기 2. 추가한 상품 조회 3. 상품 목록 4. 재고 관리 5. 관리자 메뉴"
					+ "\n-----------------------------------------");
			switch(view.Main.answer(5)) {
			case 1 : add();
				return;
			case 2 : 
				System.out.println("-----------------------------------------"
						+ "\n\t" + getName(arr.size()) + " 상품 정보"
						+ "\n-----------------------------------------"
						+ "\n 번호\t카테고리\t이름\t가격\t재고량"
						+ "\n " + arr.size() + "\t" + arr.get(arr.size()-1)
						+ "\n-----------------------------------------"
						+ "\n 1. 수정 2. 삭제 3. 재고 관리 4. 관리자 메뉴 5. 로그아웃");
				int answer = view.Main.answer(5);
				switch(answer) {
				case 1 : 
					AdminImpl.getInstance().setProductIndex(arr.size()-1);
					AdminImpl.getInstance().productUpdate();
					break;
				case 2 : 
					AdminImpl.getInstance().setProductIndex(arr.size()-1);
					AdminImpl.getInstance().productRemove();
					break;
				case 3 : MenuImpl.getInstance().commonMenu(HOST_STOCK_MENU);
					break;
				case 4 : MenuImpl.getInstance().commonMenu(HOST_MENU);
					break;
				case 5 : MenuImpl.getInstance().commonMenu(HOST_LOGOUT);
					break;
				}
				return;
			case 3 : MenuImpl.getInstance().commonMenu(HOST_GOODS_LIST);
				return;
			case 4 : MenuImpl.getInstance().commonMenu(HOST_STOCK_MENU);
				return;
			case 5 : MenuImpl.getInstance().commonMenu(HOST_MENU);
				return;
			}
		}
	}	

	// 재고량 변경
	public boolean countChange(int index, Accessary acs, int count) throws EmptyProductException, EmptyStockException {
		if(arr.contains(acs)) {
			acs.countChange(count);
			arr.set(index, acs);
			return true;
		}
		noOne();
		return false;
	}
}
