package dao;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class DBTableCSV {
	
    public void downloadDBTable(List<DBTable>  dbList, String fileName) {
    	
    	try (FileOutputStream fos = new FileOutputStream("/Users/hsin/Desktop/" + fileName);
				BufferedOutputStream bos = new BufferedOutputStream(fos)
				) {
			
    		String content = "ID,NAME,ADDRESS,PHONE,ADULT_PSC,CHILD_PSC,UP_DATE";
    		byte[] bytes = content.getBytes();//把文字轉換成byte array
			bos.write(bytes);//寫檔案
			bos.write('\n');
			for (DBTable db: dbList) {
				content = db.getId() + "," + db.getName() + "," + db.getAddress() + "," + db.getPhone() + "," + db.getAdult_PCS() + 
						"," + db.getChild_PCS() + "," + db.getUpDate();
				bytes = content.getBytes();//把文字轉換成byte array
				bos.write(bytes);//寫檔案
				bos.write('\n');
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	//System.out.println("Success Download CSV");
    	
    }
	
	public void insertCSVToDB() {
		
		DBTableJDBCDAO jdbcdao = new DBTableJDBCDAO();
		
		try (Connection connection = jdbcdao.getBasicDataSource().getConnection();
				PreparedStatement pstmt = connection.prepareStatement("Insert into Pharmacy values( ?, ?, ?, ?, ?, GETDATE())");
				InputStreamReader isr =  new InputStreamReader(new FileInputStream("/Users/hsin/Desktop/java/Java_Project/JavaProject_1/lib/maskdata.csv"));
				BufferedReader reader = new BufferedReader(isr);){
			String line = null;
			int cont = 0; 
			System.out.println(cont);
			while((line = reader.readLine())!= null) {
				cont++;
				if (cont == 1) continue;
				String item[] = line.split(",");
				
				pstmt.setString(1, item[1]);
				pstmt.setString(2, item[2]);
				pstmt.setString(3, item[3]);
				pstmt.setBigDecimal(4, new BigDecimal(item[4]));
				pstmt.setBigDecimal(5, new BigDecimal(item[5]));
				
				pstmt.addBatch();
				pstmt.clearParameters();
				if (cont % 600 == 0) {
					pstmt.executeBatch();
					pstmt.clearBatch();
					System.out.println(cont);
				}
				
			}
			pstmt.executeBatch();
			pstmt.clearBatch();
			System.out.println(cont);
			System.out.println("finish");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("Success Insert");
		
	}
	
}
