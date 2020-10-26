package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.dbcp2.BasicDataSource;

public class DBTableJDBCDAO implements DBDAO{
	
	private BasicDataSource dataSource;
	
	public BasicDataSource getBasicDataSource() {
		
		if ( dataSource == null) {
			//第一次呼叫，daaSource屬性為null，才產生DataSource
			BasicDataSource ds = new BasicDataSource();
			ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			ds.setUrl("jdbc:sqlserver://localhost;database=JDBCDB");
			ds.setUsername("sa");
			ds.setPassword("Mm142325094");
			ds.setMaxIdle(50);//最多可以在pool中發呆的連線數
			ds.setMaxTotal(50);//最多可能同時保留的連線數
			dataSource = ds;
			
		}
		
		return dataSource;
	}


	@Override
	public List<DBTable> listDBTable(String sql) {
		
		List<DBTable> list = new ArrayList<DBTable>();
		try (Connection connection = getBasicDataSource().getConnection();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(sql);) {
			
			while ( rs.next() ) {
				
				DBTable db = new DBTable();
				db.setId(rs.getInt("ID"));
				db.setName(rs.getString("NAME"));
				db.setAddress(rs.getString("ADDRESS"));
				db.setPhone(rs.getString("PHONE"));
				db.setAdult_PCS(rs.getBigDecimal("ADULT_PCS"));
				db.setChild_PCS(rs.getBigDecimal("CHILD_PCS"));
				db.setUpDate(rs.getDate("UP_DATE"));
				list.add(db);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public void updataDBTable(DBTable db) {
		
		try (Connection connection = getBasicDataSource().getConnection();
				PreparedStatement pstmt = connection.prepareStatement("update Pharmacy set ADULT_PCS = ?, CHILD_PCS = ?, UP_DATE = GETDATE() WHERE NAME = ?")
						){
			
			pstmt.setBigDecimal(1, db.getAdult_PCS());
			pstmt.setBigDecimal(2, db.getChild_PCS());
			pstmt.setString(3, db.getName());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
		
			e.printStackTrace();
			
		}
		
	}
	
	@Override
	public void insertDBTable(DBTable db) {
		try (Connection connection = getBasicDataSource().getConnection();
				PreparedStatement pstmt = connection.prepareStatement("Insert into Pharmacy values( ?, ?, ?, ?, ?, GETDATE())")){
			
			pstmt.setString(1, db.getName());
			pstmt.setString(2, db.getAddress());
			pstmt.setString(3, db.getPhone());
			pstmt.setBigDecimal(4, db.getAdult_PCS());
			pstmt.setBigDecimal(5, db.getChild_PCS());
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
}
