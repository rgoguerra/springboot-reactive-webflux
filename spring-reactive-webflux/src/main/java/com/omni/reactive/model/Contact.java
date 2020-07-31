package com.omni.reactive.model;

public class Contact {
	private Integer contactid;
	private String contactname;

	public Contact() {
		
	}
	
	public Contact(Integer contactid, String contactname) {
		super();
		this.contactid = contactid;
		this.contactname = contactname;
	}

	public Integer getContactid() {
		return contactid;
	}

	public void setContactid(Integer contactid) {
		this.contactid = contactid;
	}

	public String getContactname() {
		return contactname;
	}

	public void setContactname(String contactname) {
		this.contactname = contactname;
	}

	
	
}