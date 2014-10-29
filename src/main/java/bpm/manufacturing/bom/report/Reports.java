package bpm.manufacturing.bom.report;

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
	@GET @Path("/mfg-order/{code}")
    @Produces("application/pdf") 
    public  Response manufacturingOrder(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("code") String paramCode) throws ServletException, IOException {
		
		MfgOrderReport mfgOrderReport = new MfgOrderReport();
		return mfgOrderReport.getReport(paramCode);	
		
	}
	
}
