package bpm.manufacturing.bom.report;



public class BomDataBean {
	
	private String itemNo;
	private String itemDescription;
	private String prodQty;
	private String prodDescription;
	private String prodColor;
	private String prodWeight;
	private String prodWeightTotal;
	
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
	public String getProdQty() {
		return prodQty;
	}
	public void setProdQty(String quantity) {
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
	public String getProdWeight() {
		return prodWeight;
	}
	public void setProdWeight(String weight) {
		this.prodWeight = weight;
	}
	public String getProdWeightTotal() {
		return prodWeightTotal;
	}
	public void setProdWeightTotal(String weight) {
		this.prodWeightTotal = weight;
	}
	
}