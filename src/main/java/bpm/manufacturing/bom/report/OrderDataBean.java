package bpm.manufacturing.bom.report;

import java.util.ArrayList;

public class OrderDataBean {
	private long number;
	private String customer;
	private String representative;
	private long created;
	private long delivery;
	private String quotation;
	private String comment;
	private String project;
	private String finish;
	private ArrayList<OrderItemDataBean> item;
	
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getRepresentative() {
		return representative;
	}
	public void setRepresentative(String representative) {
		this.representative = representative;
	}
	public long getCreated() {
		return created;
	}
	public void setCreated(long created) {
		this.created = created;
	}
	public long getDelivery() {
		return delivery;
	}
	public void setDelivery(long delivery) {
		this.delivery = delivery;
	}
	public String getQuotation() {
		return quotation;
	}
	public void setQuotation(String quotation) {
		this.quotation = quotation;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getFinish() {
		return finish;
	}
	public void setFinish(String finish) {
		this.finish = finish;
	}
	public ArrayList<OrderItemDataBean> getItem() {
		return item;
	}
	public void setItem(ArrayList<OrderItemDataBean> item) {
		this.item = item;
	}


}
