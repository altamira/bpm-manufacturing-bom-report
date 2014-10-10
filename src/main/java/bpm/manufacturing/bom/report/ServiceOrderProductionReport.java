package bpm.manufacturing.bom.report;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ServiceOrderProductionReport {
	
	public OrderDataBean getData(String code) {
		
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://localhost:8080/bpm-manufacturing-bom-report/service-order-production.json");
		OrderDataBean OrderData = webTarget.path("").request(MediaType.APPLICATION_JSON).get(OrderDataBean.class);
		return OrderData;
	}

	public Response getReport(String code) {
		JasperPrint jasperPrint;
		byte[] pdf = null;
		
		OrderDataBean reportData = this.getData(code);
		
		long createdTimeStamp = (long) reportData.getCreated();
		long deliveryTimeStamp = (long) reportData.getDelivery();
		
		//FORMATING ORDER DATE
		String orderDateDisplay = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(createdTimeStamp));
		
		//FORMATING DELIVERT DATE
		String deliveryDateDisplay = new java.text.SimpleDateFormat("w/yyyy").format(new java.util.Date(deliveryTimeStamp));
		String weekString = new java.text.SimpleDateFormat("w").format(new java.util.Date(deliveryTimeStamp));
		int weekNo = Integer.parseInt( weekString );
		String yearString = new java.text.SimpleDateFormat("yyyy").format(new java.util.Date(deliveryTimeStamp));
		int yearNo = Integer.parseInt( yearString );

		// CALCULATE THE 1ST DAY AND LAST DAY OF THE WEEK
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.WEEK_OF_YEAR, weekNo);
		calendar.set(Calendar.YEAR, yearNo);
		// Get the FIRST day of week.
		Date date = calendar.getTime();
		String startDateDisplay = new java.text.SimpleDateFormat("dd/MM/yyyy").format(date);
		// Get the LAST day of week.
		calendar.add(Calendar.DATE, 6);
		Date endDate = calendar.getTime();
		String endDateDisplay = new java.text.SimpleDateFormat("dd/MM/yyyy").format(endDate);
		String deliveryWeekDisplay = startDateDisplay + " a " + endDateDisplay;
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		//SET THE PARAMETERS FOR JASPER REPORT
        parameters.put("Title", "￼￼Ordem de Serviço (Produção)");
     	parameters.put("UserName", "MASTER");
     	parameters.put("Customer", reportData.getCustomer());
     	parameters.put("Representative", reportData.getRepresentative());
     	parameters.put("OrderID", reportData.getNumber());
     	parameters.put("LogoImage", this.getLogo());
     	parameters.put("DateRequest", orderDateDisplay);
     	parameters.put("DeliveryTime", deliveryDateDisplay);
     	parameters.put("DeliveryWeek", deliveryWeekDisplay);
     	parameters.put("NoBudget", reportData.getQuotation());
     	parameters.put("NoProject", reportData.getProject());
     	parameters.put("Finish", reportData.getFinish());
     	parameters.put("Comment", reportData.getComment());
     	
     	ArrayList<OrderItemProductDataBean> dataList = new ArrayList<OrderItemProductDataBean>();
     	ArrayList<OrderItemDataBean> OrderItemList = reportData.getItem();
     	for (int i = 0; i < OrderItemList.size(); i++) {
			
			ArrayList<OrderItemProductDataBean> OrderItemProductList = OrderItemList.get(i).getProduct();
			for (int j = 0; j < OrderItemProductList.size(); j++) {
				OrderItemProductList.get(j).setItemCode(OrderItemList.get(i).getItem());
				OrderItemProductList.get(j).setItemDescription(OrderItemList.get(i).getDescription());
				dataList.add(OrderItemProductList.get(j));
			}
			
     	}
     	
     	//GET THE JASPER FILE
		InputStream reportStream = getClass().getResourceAsStream("/reports/service-order-production/service-order-production-report.jasper");
		if (reportStream == null) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Não foi possivel carregar o relatorio !").build();
		}
		
		//PRINT THE PDF REPORT
    	try {
    		JRBeanCollectionDataSource beanColDataSource =  new JRBeanCollectionDataSource(dataList);
    		jasperPrint = JasperFillManager.fillReport(reportStream, parameters, beanColDataSource);
    		pdf = JasperExportManager.exportReportToPdf(jasperPrint);
    		ByteArrayInputStream pdfStream = new ByteArrayInputStream(pdf);

            Response.ResponseBuilder response = Response.ok(pdfStream);
            response.header("Content-Disposition", "inline; filename=Request Report.pdf");
            return response.build();
    	} catch (JRException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
		return null;
	}
	
	public BufferedImage getLogo() {
		//GET THE LOGO FILE FROM RESOURCE
		InputStream reportLogo = getClass().getResourceAsStream("/images/report-header-logo.png");
		
		 BufferedImage imfg = null;
		 try {
		     //InputStream in = new ByteArrayInputStream(requestReportAltamiraimage);
		     imfg = ImageIO.read(reportLogo);
		 } catch (Exception e1) {
		     e1.printStackTrace();
		 }
		 return imfg;
	}


}
