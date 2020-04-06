package com.practice.model;

public class StockProfile {

	private String company;
	private String profession;
	private String sector;
	private String address;
	private String registration;
	
	public StockProfile(String company, String profession, String sector, String address, String registration) {
		super();
		this.company = company;
		this.profession = profession;
		this.sector = sector;
		this.address = address;
		this.registration = registration;
	}
	
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRegistration() {
		return registration;
	}
	public void setRegistration(String registration) {
		this.registration = registration;
	}

}
