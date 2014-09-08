package bpm.manufacturing.bom.report;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import net.sf.dynamicreports.report.exception.DRException;

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
    	
    	BillOfMaterial report = new BillOfMaterial();
    	OutputStream out = resp.getOutputStream();
    	try {
			report.build().toPdf(out);
		} catch (DRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
