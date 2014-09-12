package bpm.manufacturing.bom.report;



public class BomDataBean {
	
	private String itemNo;
	private String deliveryDate;
	private String description;
	
	private int bomQty;
	private String bomDescription;
	private String bomColor;
	private int bomWeight;
	private int bomWeightTotal;
	
	public int getBomQty() {
		return bomQty;
	}
	public int getBomWeightTotal() {
		return bomWeightTotal;
	}
	public void setBomWeightTotal(int bomWeightTotal) {
		this.bomWeightTotal = bomWeightTotal;
	}
	public void setBomQty(int bomQty) {
		this.bomQty = bomQty;
	}
	public String getBomDescription() {
		return bomDescription;
	}
	public void setBomDescription(String bomDescription) {
		this.bomDescription = bomDescription;
	}
	public String getBomColor() {
		return bomColor;
	}
	public void setBomColor(String bomColor) {
		this.bomColor = bomColor;
	}
	public int getBomWeight() {
		return bomWeight;
	}
	public void setBomWeight(int bomWeight) {
		this.bomWeight = bomWeight;
	}
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}