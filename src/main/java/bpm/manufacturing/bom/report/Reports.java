package bpm.manufacturing.bom.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 * Root resource (exposed at "reports" path)
 */

@Path("reports")
public class Reports {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
     * @return 
     *
     */

	@GET
    @Produces("application/pdf") 
    public  void getIt(@Context HttpServletRequest req, @Context HttpServletResponse resp) throws ServletException, IOException {
    	
    	//CALCULATE THE PATH OF THE JASPER FILE
    	URL resource = getClass().getResource("/");
    	String path = resource.getPath();
    	path = path.replace("WEB-INF/classes/", "");
    	String sourceFileName = "reports/bom-by-order.jasper";
    	String reportPath = path + sourceFileName; 
    	String logoPath = path + "images/report-header-logo.png";
    	
    	BomDataBeanList DataBeanList = new BomDataBeanList();
    	ArrayList<BomDataBean> dataList = DataBeanList.getDataBeanList();
    	JRBeanCollectionDataSource beanColDataSource =  new JRBeanCollectionDataSource(dataList);

    	Map<String, Object> parameters = new HashMap<String, Object>();
    	parameters.put("Title", "Ordem de Serviço (Engenharia)");
    	parameters.put("UserName", "MASTER");
    	parameters.put("Customer", "ALIANÇA LATINA INDUSTRIA E COMERCIO LTDA");
    	parameters.put("Representative", "MOVITTEC IND.E COM.LTDA");
    	parameters.put("OrderID", "12345");
    	parameters.put("ImgUrl", logoPath);
    	parameters.put("DateRequest", "22/08/2014");
    	parameters.put("DeliveryTime", "41/2014 (06/10/2014 a 10/10/2014)");
    	parameters.put("NoBudget", "00087234");
    	parameters.put("NoProject", "00000");
    	parameters.put("Finish", "ACABAMENTO ESPECIAL");
    	JasperPrint jasperPrint;
    	try {
    		jasperPrint = JasperFillManager.fillReport(reportPath, parameters, beanColDataSource);
    		JRExporter exporter = new JRPdfExporter();
    		ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
    		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
    		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputstream);
    		exporter.exportReport();
    		resp.setContentLength(outputstream.size());
    		resp.setContentType("application/pdf");

    		ServletOutputStream servletOutputStream =  resp.getOutputStream();
    		outputstream.writeTo(servletOutputStream);
    		servletOutputStream.flush();
    	} catch (JRException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }
}
