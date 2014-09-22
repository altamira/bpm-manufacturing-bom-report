package bpm.manufacturing.bom.report;



public class BomDataBean {
	
	private long itemNo;
	private String itemDescription;
	private double prodQty;
	private String prodDescription;
	private String prodColor;
	private double prodWeight;
	private double prodWeightTotal;
	
	public long getItemNo() {
		return itemNo;
	}
	public void setItemNo(long itemNo) {
		this.itemNo = itemNo;
	}
	public String getItemDescription() {
		return itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	public double getProdQty() {
		return prodQty;
	}
	public void setProdQty(double quantity) {
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
	public double getProdWeight() {
		return prodWeight;
	}
	public void setProdWeight(double weight) {
		this.prodWeight = weight;
	}
	public double getProdWeightTotal() {
		return prodWeightTotal;
	}
	public void setProdWeightTotal(double weightTotal) {
		this.prodWeightTotal = weightTotal;
	}
	
}