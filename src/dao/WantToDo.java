package dao;

import java.math.BigDecimal;
import java.util.List;

public class WantToDo {
	
	private String ouput;
	private DBDAO dbs = new DBTableJDBCDAO();
	private DBTableCSV toCSV = new DBTableCSV();
	private List<DBTable> selectTables;

	public void getDownloadTable ( String sql, String fileName) {
		
		selectTables = dbs.listDBTable(sql);
		toCSV.downloadDBTable(selectTables, fileName + ".csv");
	}
	
	public boolean getPrintTable( String sql ) {
		boolean  s = true;
		selectTables = dbs.listDBTable(sql);
		if (selectTables.size() == 0) {
			System.out.println();
			System.out.println("查無結果，重新搜尋");
			s = false;
		}
		for ( DBTable db : selectTables ) {
			ouput = String.format("店名：%s, 地址：%s, 電話：%s\n成人口罩數量：%s\n兒童口罩數量：%s\n", 
					db.getName(), db.getAddress(), db.getPhone(), db.getAdult_PCS(), db.getChild_PCS());
			 System.out.print(ouput);
		}
		System.out.println();
		
		return s;
	}
	
	public void upData( String sql , String adult_PCS, String child_PCS) {
		selectTables = dbs.listDBTable(sql);
		for ( DBTable db : selectTables ) {
			
			if (adult_PCS.equals("N") != true) db.setAdult_PCS(new BigDecimal(adult_PCS));
			if (child_PCS.equals("N") != true) db.setChild_PCS(new BigDecimal(child_PCS));
			
			dbs.updataDBTable(db);
		}
	}
	
	public void insertData(String name, String address, String phone, String adult_PCS, String child_PCS) {
		
		DBTable db = new DBTable();
		
		db.setName(name);
		db.setAddress(address);
		db.setPhone(phone);
		db.setAdult_PCS(new BigDecimal(adult_PCS));
		db.setChild_PCS(new BigDecimal(child_PCS));
		
		dbs.insertDBTable(db);
	}	
	
	
}
