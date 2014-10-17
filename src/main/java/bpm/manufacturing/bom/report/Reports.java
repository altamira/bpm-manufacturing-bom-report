package bpm.manufacturing.bom.report;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Root resource (exposed at "reports" path)
 */

@Path("reports")
public class Reports {
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
	 * @return 
     * @return 
     *
     */
	@GET @Path("/materials/{orderId}")
    @Produces("application/pdf") 
    public  Response materialListReport(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("orderId") String orderId) throws ServletException, IOException {
		
		MaterialListReport materialListReport = new MaterialListReport();
		return materialListReport.getReport(orderId);	
		
	}

	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
	 * @return 
     * @return 
     *
     */
	@GET @Path("/mfg-process/{code}")
    @Produces("application/pdf") 
    public  Response manufacturingProcess(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("code") String paramCode) throws ServletException, IOException {
	
		MfgProcessReport mfgProcessReport = new MfgProcessReport();
		return mfgProcessReport.getReport(paramCode);	
		
	}
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
	 * @return 
     * @return 
     *
     */
	@GET @Path("/mfg-order/{code}")
    @Produces("application/pdf") 
    public  Response manufacturingOrder(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("code") String paramCode) throws ServletException, IOException {
		
		MfgOrderReport mfgOrderReport = new MfgOrderReport();
		return mfgOrderReport.getReport(paramCode);	
		
	}
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
	 * @return 
     * @return 
     *
     */
	@GET @Path("/service-order-painting/{code}")
    @Produces("application/pdf") 
    public  Response serviceOrderPainting(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("code") String paramCode) throws ServletException, IOException {
		
		ServiceOrderPaintingReport serviceOrderPaintingReport = new ServiceOrderPaintingReport();
		return serviceOrderPaintingReport.getReport(paramCode);	
		
	}
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
	 * @return 
     * @return 
     *
     */
	@GET @Path("/service-order-production/{code}")
    @Produces("application/pdf") 
    public  Response serviceOrderProduction(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("code") String paramCode) throws ServletException, IOException {
		
		ServiceOrderProductionReport serviceOrderProductionReport = new ServiceOrderProductionReport();
		return serviceOrderProductionReport.getReport(paramCode);	
		
	}
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
	 * @return 
     * @return 
     *
     */
	@GET @Path("/service-order-shipping/{code}")
    @Produces("application/pdf") 
    public  Response serviceOrderShipping(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("code") String paramCode) throws ServletException, IOException {
		
		ServiceOrderShippingReport serviceOrderShippingReport = new ServiceOrderShippingReport();
		return serviceOrderShippingReport.getReport(paramCode);	
		
	}
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
	 * @return 
     * @return 
     *
     */
	@GET @Path("/service-order-editor/{code}")
    @Produces("application/pdf") 
    public  Response serviceOrderEditor(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("code") String paramCode) throws ServletException, IOException {
		
		ServiceOrderEditorReport serviceOrderEditorReport = new ServiceOrderEditorReport();
		return serviceOrderEditorReport.getReport(paramCode);	
		
	}
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
	 * @return 
     * @return 
     *
     */
	@GET @Path("/service-order-weld/{code}")
    @Produces("application/pdf") 
    public  Response serviceOrderWeld(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("code") String paramCode) throws ServletException, IOException {
		
		ServiceOrderWeldReport serviceOrderWeldReport = new ServiceOrderWeldReport();
		return serviceOrderWeldReport.getReport(paramCode);	
		
	}
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
	 * @return 
     * @return 
     *
     */
	@GET @Path("/service-order-warehouse/{code}")
    @Produces("application/pdf") 
    public  Response serviceOrderWarehouse(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("code") String paramCode) throws ServletException, IOException {
		
		ServiceOrderWarehouseReport serviceOrderWarehouseReport = new ServiceOrderWarehouseReport();
		return serviceOrderWarehouseReport.getReport(paramCode);	
		
	}
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "application/pdf" media type.
	 * @return 
     * @return 
     *
     */
	@GET @Path("/service-order-transportation/{code}")
    @Produces("application/pdf") 
    public  Response serviceOrderTransportation(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("code") String paramCode) throws ServletException, IOException {
		
		ServiceOrderTransportationReport serviceOrderTransportationReport = new ServiceOrderTransportationReport();
		return serviceOrderTransportationReport.getReport(paramCode);	
		
	}
	
}
