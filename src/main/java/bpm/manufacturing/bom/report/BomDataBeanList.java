package bpm.manufacturing.bom.report;

import java.util.ArrayList;

public class BomDataBeanList {
	public ArrayList<BomDataBean> getDataBeanList() {
		ArrayList<BomDataBean> dataBeanList = new ArrayList<BomDataBean>();

		dataBeanList.add(produce("11111", "India"));
		dataBeanList.add(produce("22222", "USA"));
		dataBeanList.add(produce("33333", "India"));
		dataBeanList.add(produce("44444", "California"));

		return dataBeanList;
	}

	/**
	* This method returns a DataBean object,
	* with name and country set in it.
	*/
	private BomDataBean produce(String orderId, String name) {
		BomDataBean dataBean = new BomDataBean();
		dataBean.setOrderId(orderId);
		dataBean.setCustomerName(name);
		return dataBean;
	}
}
