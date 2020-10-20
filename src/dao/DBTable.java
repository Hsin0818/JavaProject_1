package dao;

import java.math.BigDecimal;
import java.sql.Date;

public class DBTable {
	
	private int id;
	private String name;
	private String address;
	private String phone;
	private BigDecimal adult_PCS;
	private BigDecimal child_PCS;
	private Date upDate;
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public BigDecimal getAdult_PCS() {
		return adult_PCS;
	}
	public void setAdult_PCS(BigDecimal adult_PCS) {
		this.adult_PCS = adult_PCS;
	}
	public BigDecimal getChild_PCS() {
		return child_PCS;
	}
	public void setChild_PCS(BigDecimal child_PCS) {
		this.child_PCS = child_PCS;
	}
	public Date getUpDate() {
		return upDate;
	}
	

}
