package com.ssc.model.dto;


public class ContactDetailDTO {
	

	
	private String first;
	private String middle;
	private String last;
	
	public ContactDetailDTO(String first,String middle, String last) {
		this.first=first;
		this.middle=middle;
		this.last=last;
	}
	
	public ContactDetailDTO() {
		
	}
	
	
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getMiddle() {
		return middle;
	}
	public void setMiddle(String middle) {
		this.middle = middle;
	}
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = last;
	}
	

}
