package bpm.manufacturing.bom.report;

import java.util.ArrayList;

public class OrderItemDataBean {
	private long item;
	private String description;
	private ArrayList<OrderItemProductDataBean> product;
	
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
	public ArrayList<OrderItemProductDataBean> getProduct() {
		return product;
	}
	public void setProduct(
			ArrayList<OrderItemProductDataBean> product) {
		this.product = product;
	}
	

}
