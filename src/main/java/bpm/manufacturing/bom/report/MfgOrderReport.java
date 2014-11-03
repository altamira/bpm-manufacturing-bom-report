package bpm.manufacturing.bom.report;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class MfgOrderReport extends ReportConfig {
	
	public MfgOrderDataBean getData(String code) {
		
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://localhost:8080/bpm-manufacturing-bom-report/mfg-order.json");
		MfgOrderDataBean mfgOrderData = webTarget.path("").request(MediaType.APPLICATION_JSON).get(MfgOrderDataBean.class);
		return mfgOrderData;
	}
	
	public Response getReport(String code) {
		
		JasperPrint jasperPrint;
		byte[] pdf = null;
		
		//MfgOrderReport mfgOrderReport = new MfgOrderReport();
		MfgOrderDataBean mfgReportData = this.getData(code);
		String componentCodeData = "";
		String componentNameData = "";
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		//SET THE PARAMETERS
		parameters.put("Title", "ORDEM FABRICAÇÃO");
     	parameters.put("UserName", "MASTER");
     	parameters.put("Code", mfgReportData.getCode());
     	parameters.put("Description", mfgReportData.getDescription());
     	parameters.put("Date", mfgReportData.getDate());
     	parameters.put("Sector", mfgReportData.getSector());
     	parameters.put("IssuedBy", mfgReportData.getIssuedBy());
     	parameters.put("Operation", mfgReportData.getOperation());
     	parameters.put("LogoImage", this.getLogo());
		
		List<MfgOrderComponent> components = mfgReportData.getComponents();
		for (int i = 0; i < components.size(); i++) {	
			String newLineText = "\r\n";
			if(i == (components.size() - 1)) {
				newLineText = "";
			}
			componentCodeData = componentCodeData + components.get(i).getCode()  + newLineText;
			componentNameData = componentNameData + components.get(i).getName()  + newLineText;
        }
		parameters.put("ComponentCodeDataList", componentCodeData);
     	parameters.put("ComponentNameDataList", componentNameData);
     	
		List<MfgOrderMaterial> OrderMaterial = mfgReportData.getMaterials();
		
		//GET THE JASPER FILE
		InputStream reportStream = getClass().getResourceAsStream("/reports/mfg-order/mfg-order-report.jasper");
		if (reportStream == null) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Não foi possivel carregar o relatorio !").build();
		}
		
		//PRINT THE PDF REPORT
    	try {
    		JRBeanCollectionDataSource beanColDataSource =  new JRBeanCollectionDataSource(OrderMaterial);
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

}
