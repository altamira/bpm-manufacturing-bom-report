package bpm.manufacturing.bom.report;



public class BomDataBean {
	
	private String itemNo;
	private String itemDescription;
	private Float prodQty;
	private String prodDescription;
	private String prodColor;
	private Float prodWeight;
	private Float prodWeightTotal;
	
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public Float getProdQty() {
		return prodQty;
	}
	public void setProdQty(Float quantity) {
		this.prodQty = quantity;
	}
	public String getProdDescription() {
		return prodDescription;
	}
	public void setProdDescription(String prodDescription) {
		this.prodDescription = prodDescription;
	}
	public String getProdColor() {
		return prodColor;
	}
	public void setProdColor(String prodColor) {
		this.prodColor = prodColor;
	}
	public Float getProdWeight() {
		return prodWeight;
	}
	public void setProdWeight(Float weight) {
		this.prodWeight = weight;
	}
	public Float getProdWeightTotal() {
		return prodWeightTotal;
	}
	public void setProdWeightTotal(Float weight) {
		this.prodWeightTotal = weight;
	}
	
}