
import java.util.Scanner;

import dao.*;

public class MainProject {
	
	static Scanner scanner = new Scanner(System.in);
	static WantToDo wantToDo = new WantToDo();
	
	static String name;
	static String address;
	static String mode;
	static String phone;
	static String adult_PCS;
	static String child_PCS;
	static String sql;
	static String fileName;
	
	public static void main(String[] args) {
		//將CSV匯入資料庫
		/*DBTableCSV upData = new DBTableCSV();
		upData.insertCSVToDB();
		*/
		Openssh ssh = new Openssh();
		ssh.loadSSH();
		
		boolean run = true;
		
		while(run == true) {
			
			System.out.print("模式選擇(1 地點查詢 / 2：店家查詢 / 3：新增販售地點):");
			mode = scanner.next();
			if (mode.equals("1")) {
				
				System.out.print("輸入查詢地址：");
				address = scanner.next();
				
				sql = "Select * FROM Pharmacy where ADDRESS like '%" + address + "%'"; 
				
				ifaddSql();
				ifUpData();
				ifDownloadData();
				
			}else if(mode.equals("2")){
				
				System.out.print("輸入查詢名稱：");
				name = scanner.next();
				
				sql = "Select * FROM Pharmacy where NAME like '%" + name + "%'"; 	
					
				if (ifaddSql() == false ) continue;
				if (ifUpData() == false) continue;
				ifDownloadData();
				
			}else if(mode.equals("3")){
				
				System.out.print("輸入店名：");
				name = scanner.next();
				System.out.print("輸入地址：");
				address = scanner.next();
				System.out.print("輸入電話：");
				phone = scanner.next();
				System.out.print("輸入成人口罩數量：");
				adult_PCS = scanner.next();
				System.out.print("輸入成人口罩數量：");
				child_PCS = scanner.next();
				
				wantToDo.insertData(name, address, phone, adult_PCS, child_PCS);
				System.out.println("新增成功");
				
			}else {
				System.out.println("輸入錯誤，請重新輸入 ");
				continue;
			}
			
			System.out.print("是否要離開( Y / N )：");
			if (scanner.next().equals("Y")) {
				run = false;
				System.out.println("成功離開");
				ssh.closeSSH();
			} 	
		}
	}
	
	public static boolean ifaddSql() {
		boolean con = true;
		System.out.print("是否要限定口罩數量一定範圍以上( Y / N )：");
		
		mode = scanner.next();
		if (mode.equals("Y")) {
			System.out.print("模式選擇(1 限定成人口罩 / 2：限定兒童口罩 / 3：兩者皆限定):");
			mode = scanner.next();
			if (mode.equals("1")) {
				System.out.print("輸入成人口罩數量：");
				adult_PCS = scanner.next();
				sql = sql + " and ADULT_PCS > " + adult_PCS;
				if (wantToDo.getPrintTable(sql) == false ) con = false;
			}else if (mode.equals("2")) {
				System.out.print("輸入兒童口罩數量：");
				child_PCS = scanner.next();
				sql = sql + " and CHILD_PCS > " + child_PCS;
				if (wantToDo.getPrintTable(sql) == false ) con = false;
			}else if (mode.equals("3")){
				System.out.print("輸入成人口罩數量：");
				adult_PCS = scanner.next();
				System.out.print("輸入兒童口罩數量：");
				child_PCS = scanner.next();
				sql =sql + String.format(" AND ADULT_PCS > %s AND CHILD_PCS  > %s", adult_PCS,child_PCS);
				if (wantToDo.getPrintTable(sql) == false ) con = false;
			}
		}else if (mode.equals("N")){
			if (wantToDo.getPrintTable(sql) == false ) con = false;
		}else {
			System.out.println("輸入錯誤，請重新輸入 ");
			ifaddSql();
		}
		
		return con;
	}
	
	public static boolean ifUpData() {
		boolean con = true;
		System.out.print("是否要對口罩數量進行修改( Y / N )：");
		mode = scanner.next();
		if (mode.equals("Y")) {
			System.out.print("輸入要更改的店名：");
			name = scanner.next();
			System.out.print("輸入成人口罩數量(不改變輸入 Ｎ)：");
			adult_PCS = scanner.next();
			System.out.print("輸入兒童口罩數量(不改變輸入 Ｎ)：");
			child_PCS = scanner.next();
			
			sql = "Select * FROM Pharmacy where NAME = '"+ name + "'";
			wantToDo.upData(sql, adult_PCS, child_PCS);
			if (wantToDo.getPrintTable(sql) == false ) con = false;
			
		}else if (mode.equals("N") == false){
			System.out.println(" 輸入錯誤，請重新輸入 "); 
			ifUpData();
		}
		return con;
	}
	
	public static void ifDownloadData() {
		System.out.print("是否要下載搜尋結果( Y / N )：");
		mode = scanner.next();
		if (mode.equals("Y")) {
			System.out.print("輸入檔案名稱：");
			fileName = scanner.next();
			wantToDo.getDownloadTable(sql, fileName);
			System.out.println("下載成功");
		}else if (mode.equals("N") == false){
			System.out.println(" 輸入錯誤，請重新輸入 ");
			ifDownloadData();
		}
	}
	
}
