package bpm.manufacturing.bom.report;

import java.util.List;

public class MfgOperation {
	private int sequence;
	private String name;
	private String description;
	private List<MfgMaterial> consume;
	private List<MfgMaterial> produce;
	private SketchDataBean sketch;
	
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public SketchDataBean getSketch() {
		return sketch;
	}
	public void setSketch(SketchDataBean sketch) {
		this.sketch = sketch;
	}
	public List<MfgMaterial> getConsume() {
		return consume;
	}
	public void setConsume(List<MfgMaterial> consume) {
		this.consume = consume;
	}
	public List<MfgMaterial> getProduce() {
		return produce;
	}
	public void setProduce(List<MfgMaterial> produce) {
		this.produce = produce;
	}

}
