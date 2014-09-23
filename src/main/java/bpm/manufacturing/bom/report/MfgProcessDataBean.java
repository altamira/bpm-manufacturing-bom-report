package bpm.manufacturing.bom.report;

import java.util.List;

public class MfgProcessDataBean {
	private String code;
	private String description;
	private String color;
	private float weight;
	private float width;
	private float length;
	private String finish;
	private List<MfgRevision> revision;
	private List<MfgOperation> operation;
	
	public List<MfgOperation> getOperation() {
		return operation;
	}
	public void setOperation(List<MfgOperation> operation) {
		this.operation = operation;
	}
	public List<MfgRevision> getRevision() {
		return revision;
	}
	public void setRevision(List<MfgRevision> revision) {
		this.revision = revision;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getLength() {
		return length;
	}
	public void setLength(float lenght) {
		this.length = lenght;
	}
	public String getFinish() {
		return finish;
	}
	public void setFinish(String finish) {
		this.finish = finish;
	}
	
}
