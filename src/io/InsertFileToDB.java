package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;

import dao.*;

public class InsertFileToDB {
	
	public void getCSVFile() {
		
		
		
	}
	
	public void upCSVToDB( String filePath) {
		
		try (InputStreamReader isr = new InputStreamReader(new FileInputStream("/Users/hsin/Desktop/java/Java_Project/JavaProject_1/lib/pharmacy.csv"));
				BufferedReader reader = new BufferedReader(isr);){
			String line = null;
			boolean con = true;
			while((line = reader.readLine())!= null) {
				
				if (con == true) {
					con = false;
					continue;
				}
				
				String item[] = line.split(",");
				
				DBDAO jdbcdao = new JDBCDAO();
				DBTable db = new DBTable();
				
				db.setName(item[1]);
				db.setAddress(item[2]);
				db.setPhone(item[3]);
				db.setAdult_PCS(new BigDecimal(item[4]));
				db.setChild_PCS(new BigDecimal(item[4]));
				
				jdbcdao.insertDBTable(db);
				
				
			}
			System.out.println("finish");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}
	
	
	
}
