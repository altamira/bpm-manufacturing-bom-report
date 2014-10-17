package bpm.manufacturing.bom.report;

import java.util.ArrayList;

public class OrderItemDataBean {
	private long item;
	private String description;
	private ArrayList<OrderItemProductDataBean> parts;
	
	public long getItem() {
		return item;
	}
	public void setItem(long item) {
		this.item = item;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<OrderItemProductDataBean> getParts() {
		return parts;
	}
	public void setParts(ArrayList<OrderItemProductDataBean> parts) {
		this.parts = parts;
	}

	

}
