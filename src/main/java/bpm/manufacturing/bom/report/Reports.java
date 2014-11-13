package bpm.manufacturing.bom.report;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Size;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

/**
 * Root resource (exposed at "reports" path)
 */

@Path("manufacturing")
public class Reports  extends ReportConfig{

	/**
	 * The Auth Token
	 */
	private String token;

	/**
	 * Constructor
	 *
	 */
	public Reports(@QueryParam("token") String token) {
		super();
		this.token = token;
	}

	/**
	 * Method handling HTTP GET requests. The returned object will be sent
	 * to the client as "application/pdf" media type.
	 * @return 
	 * @return 
	 *
	 */
	@GET @Path("/bom/{id}")
	@Produces("application/pdf") 
	public  Response getAllReport(
			@Context HttpServletRequest req, 
			@Context HttpServletResponse resp, 
			@PathParam("id") String id) 
					throws ServletException, IOException {

		//CHECK FOR AUTH TOKEN
		if (checkAuth(token).getStatus() != 200) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid Token: " + token).build();
		}
		
		AllReports allReports = new AllReports();
		return allReports.mergeAllReports(id);
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
	public  Response materialListReport(
			@Context HttpServletRequest req, 
			@Context HttpServletResponse resp, 
			@PathParam("id") String id) 
					throws ServletException, IOException {

		//CHECK FOR AUTH TOKEN
		if (checkAuth(token).getStatus() != 200) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid Token: " + token).build();
		}
		
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
	public  Response serviceOrderPainting(
			@Context HttpServletRequest req, 
			@Context HttpServletResponse resp, 
			@PathParam("id") String id) 
					throws ServletException, IOException {

		//CHECK FOR AUTH TOKEN
		if (checkAuth(token).getStatus() != 200) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid Token: " + token).build();
		}
		
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
	public  Response serviceOrderProduction(
			@Context HttpServletRequest req, 
			@Context HttpServletResponse resp, 
			@PathParam("id") String id) 
					throws ServletException, IOException {

		//CHECK FOR AUTH TOKEN
		if (checkAuth(token).getStatus() != 200) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid Token: " + token).build();
		}
		
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
	public  Response serviceOrderShipping(
			@Context HttpServletRequest req, 
			@Context HttpServletResponse resp, 
			@PathParam("id") String id) 
					throws ServletException, IOException {

		//CHECK FOR AUTH TOKEN
		if (checkAuth(token).getStatus() != 200) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid Token: " + token).build();
		}
		
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
	public  Response serviceOrderEditor(
			@Context HttpServletRequest req, 
			@Context HttpServletResponse resp, 
			@PathParam("id") String id) 
					throws ServletException, IOException {

		//CHECK FOR AUTH TOKEN
		if (checkAuth(token).getStatus() != 200) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid Token: " + token).build();
		}
		
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
	public  Response serviceOrderWeld(
			@Context HttpServletRequest req, 
			@Context HttpServletResponse resp, 
			@PathParam("id") String id) 
					throws ServletException, IOException {

		//CHECK FOR AUTH TOKEN
		if (checkAuth(token).getStatus() != 200) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid Token: " + token).build();
		}
		
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
	public  Response serviceOrderWarehouse(
			@Context HttpServletRequest req, 
			@Context HttpServletResponse resp, 
			@PathParam("id") String id) 
					throws ServletException, IOException {

		//CHECK FOR AUTH TOKEN
		if (checkAuth(token).getStatus() != 200) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid Token: " + token).build();
		}
		
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
	public  Response serviceOrderTransportation(
			@Context HttpServletRequest req, 
			@Context HttpServletResponse resp, 
			@PathParam("id") String id) 
					throws ServletException, IOException {
		
		//CHECK FOR AUTH TOKEN
		if (checkAuth(token).getStatus() != 200) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid Token: " + token).build();
		}

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
	public  Response manufacturingProcess(
			@Context HttpServletRequest req, 
			@Context HttpServletResponse resp, 
			@PathParam("id") String id)
					throws ServletException, IOException {

		//CHECK FOR AUTH TOKEN
		if (checkAuth(token).getStatus() != 200) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid Token: " + token).build();
		}

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
	public  Response manufacturingOrder(
			@Context HttpServletRequest req, 
			@Context HttpServletResponse resp, 
			@PathParam("id") String id) 
					throws ServletException, IOException {
		
		//CHECK FOR AUTH TOKEN
		if (checkAuth(token).getStatus() != 200) {
			return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid Token: " + token).build();
		}
		
		MfgOrderReport mfgOrderReport = new MfgOrderReport();
		return mfgOrderReport.getReport(id);	

	}

}
