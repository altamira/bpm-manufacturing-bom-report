package bpm.manufacturing.bom.report;

import java.util.List;

public class MfgOperation {
	private int sequence;
	private String name;
	private String description;
	private List<MfgMaterial> input;
	
	public List<MfgMaterial> getInput() {
		return input;
	}
	public void setInput(List<MfgMaterial> input) {
		this.input = input;
	}
	public List<MfgMaterial> getOutput() {
		return output;
	}
	public void setOutput(List<MfgMaterial> output) {
		this.output = output;
	}
	private List<MfgMaterial> output;
	
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
	public String getCroqui() {
		return croqui;
	}
	public void setCroqui(String croqui) {
		this.croqui = croqui;
	}
	private String croqui;

}
