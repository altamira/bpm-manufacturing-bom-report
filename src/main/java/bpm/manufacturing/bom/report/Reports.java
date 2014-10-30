package bpm.manufacturing.bom.report;

import java.util.List;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * Root resource (exposed at "reports" path)
 */

@Path("manufacturing")
public class Reports {
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
	 * @return 
     * @return 
     *
     */
	@GET @Path("/bom/{id}")
    @Produces("application/pdf") 
    public  Response mergeAllReport(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("id") String id) throws ServletException, IOException {
		
		byte[] pdf = null;
		JasperPrint jasperPrintAll;
		JasperPrint jasperPrintChecklist;
		JasperPrint jasperPrintPainting;
		JasperPrint jasperPrintProduction;
		JasperPrint jasperPrintShipping;
		JasperPrint jasperPrintTransportation;
		JasperPrint jasperPrintwarehouse;
		JasperPrint jasperPrintWeld;
		JasperPrint jasperPrintEditor;
		
		MaterialListReport materialListReport = new MaterialListReport();
		ServiceOrderPaintingReport serviceOrderPaintingReport = new ServiceOrderPaintingReport();
		ServiceOrderProductionReport serviceOrderProductionReport = new ServiceOrderProductionReport();
		ServiceOrderShippingReport serviceOrderShippingReport = new ServiceOrderShippingReport();
		ServiceOrderEditorReport serviceOrderEditorReport = new ServiceOrderEditorReport();
		ServiceOrderWeldReport serviceOrderWeldReport = new ServiceOrderWeldReport();
		ServiceOrderWarehouseReport serviceOrderWarehouseReport = new ServiceOrderWarehouseReport();
		ServiceOrderTransportationReport serviceOrderTransportationReport = new ServiceOrderTransportationReport();
			
		jasperPrintChecklist = materialListReport.getPDF(id);
		jasperPrintPainting = serviceOrderPaintingReport.getPDF(id);
		jasperPrintProduction = serviceOrderProductionReport.getPDF(id);
		jasperPrintShipping = serviceOrderShippingReport.getPDF(id);
		jasperPrintTransportation = serviceOrderTransportationReport.getPDF(id);
		jasperPrintwarehouse = serviceOrderWarehouseReport.getPDF(id);
		jasperPrintWeld = serviceOrderWeldReport.getPDF(id);
		jasperPrintEditor = serviceOrderEditorReport.getPDF(id);
		
		//Initialize with the 1st Report
		jasperPrintAll = jasperPrintChecklist;
		
		List<JRPrintPage> pages;
		pages = jasperPrintPainting .getPages();
        for (int j = 0; j < pages.size(); j++) {
        	JRPrintPage object = (JRPrintPage)pages.get(j);
        	jasperPrintAll.addPage(object);
    
        }
        
        pages = jasperPrintProduction .getPages();
        for (int j = 0; j < pages.size(); j++) {
        	JRPrintPage object = (JRPrintPage)pages.get(j);
        	jasperPrintAll.addPage(object);
    
        }
        
        pages = jasperPrintShipping .getPages();
        for (int j = 0; j < pages.size(); j++) {
        	JRPrintPage object = (JRPrintPage)pages.get(j);
        	jasperPrintAll.addPage(object);
    
        }
        
        pages = jasperPrintTransportation .getPages();
        for (int j = 0; j < pages.size(); j++) {
        	JRPrintPage object = (JRPrintPage)pages.get(j);
        	jasperPrintAll.addPage(object);
    
        }
        
        pages = jasperPrintwarehouse .getPages();
        for (int j = 0; j < pages.size(); j++) {
        	JRPrintPage object = (JRPrintPage)pages.get(j);
        	jasperPrintAll.addPage(object);
    
        }
        
        pages = jasperPrintWeld .getPages();
        for (int j = 0; j < pages.size(); j++) {
        	JRPrintPage object = (JRPrintPage)pages.get(j);
        	jasperPrintAll.addPage(object);
    
        }
        
        pages = jasperPrintEditor .getPages();
        for (int j = 0; j < pages.size(); j++) {
        	JRPrintPage object = (JRPrintPage)pages.get(j);
        	jasperPrintAll.addPage(object);
    
        }
        
		try {
			pdf = JasperExportManager.exportReportToPdf(jasperPrintAll);
		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ByteArrayInputStream pdfStream = new ByteArrayInputStream(pdf);

        Response.ResponseBuilder response = Response.ok(pdfStream);
        response.header("Content-Disposition", "inline; filename=Request Report.pdf");
        return response.build();
		
	}
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
	 * @return 
     * @return 
     *
     */
	@GET @Path("/bom/{id}/checklist")
    @Produces("application/pdf") 
    public  Response materialListReport(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("id") String id) throws ServletException, IOException {
		
		MaterialListReport materialListReport = new MaterialListReport();
		return materialListReport.getReport(id);	
		
	}

	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
	 * @return 
     * @return 
     *
     */
	@GET @Path("/bom/{id}/painting")
    @Produces("application/pdf") 
    public  Response serviceOrderPainting(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("id") String id) throws ServletException, IOException {
		
		ServiceOrderPaintingReport serviceOrderPaintingReport = new ServiceOrderPaintingReport();
		return serviceOrderPaintingReport.getReport(id);	
		
	}
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
	 * @return 
     * @return 
     *
     */
	@GET @Path("/bom/{id}/production")
    @Produces("application/pdf") 
    public  Response serviceOrderProduction(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("id") String id) throws ServletException, IOException {
		
		ServiceOrderProductionReport serviceOrderProductionReport = new ServiceOrderProductionReport();
		return serviceOrderProductionReport.getReport(id);	
		
	}
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
	 * @return 
     * @return 
     *
     */
	@GET @Path("/bom/{id}/shipping")
    @Produces("application/pdf") 
    public  Response serviceOrderShipping(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("id") String id) throws ServletException, IOException {
		
		ServiceOrderShippingReport serviceOrderShippingReport = new ServiceOrderShippingReport();
		return serviceOrderShippingReport.getReport(id);	
		
	}
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
	 * @return 
     * @return 
     *
     */
	@GET @Path("/bom/{id}/editor")
    @Produces("application/pdf") 
    public  Response serviceOrderEditor(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("id") String id) throws ServletException, IOException {
		
		ServiceOrderEditorReport serviceOrderEditorReport = new ServiceOrderEditorReport();
		return serviceOrderEditorReport.getReport(id);	
		
	}
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
	 * @return 
     * @return 
     *
     */
	@GET @Path("/bom/{id}/weld")
    @Produces("application/pdf") 
    public  Response serviceOrderWeld(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("id") String id) throws ServletException, IOException {
		
		ServiceOrderWeldReport serviceOrderWeldReport = new ServiceOrderWeldReport();
		return serviceOrderWeldReport.getReport(id);	
		
	}
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
	 * @return 
     * @return 
     *
     */
	@GET @Path("/bom/{id}/warehouse")
    @Produces("application/pdf") 
    public  Response serviceOrderWarehouse(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("id") String id) throws ServletException, IOException {
		
		ServiceOrderWarehouseReport serviceOrderWarehouseReport = new ServiceOrderWarehouseReport();
		return serviceOrderWarehouseReport.getReport(id);	
		
	}
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
	 * @return 
     * @return 
     *
     */
	@GET @Path("/bom/{id}/transportation")
    @Produces("application/pdf") 
    public  Response serviceOrderTransportation(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("id") String id) throws ServletException, IOException {
		
		ServiceOrderTransportationReport serviceOrderTransportationReport = new ServiceOrderTransportationReport();
		return serviceOrderTransportationReport.getReport(id);	
		
	}
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
	 * @return 
     * @return 
     *
     */
	@GET @Path("/process/{id}")
    @Produces("application/pdf") 
    public  Response manufacturingProcess(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("id") String id) throws ServletException, IOException {
	
		MfgProcessReport mfgProcessReport = new MfgProcessReport();
		return mfgProcessReport.getReport(id);	
		
	}
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
	 * @return 
     * @return 
     *
     */
	@GET @Path("/packinglist/{id}")
    @Produces("application/pdf") 
    public  Response manufacturingOrder(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("id") String id) throws ServletException, IOException {
		
		MfgOrderReport mfgOrderReport = new MfgOrderReport();
		return mfgOrderReport.getReport(id);	
		
	}
	
}
