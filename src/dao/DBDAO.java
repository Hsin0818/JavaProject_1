package dao;

import java.util.List;

public interface DBDAO {
	
	List<DBTable> listDBTable(String sql);
	
	void updataDBTable(DBTable DB);
	
	void insertDBTable(DBTable DB);

}
