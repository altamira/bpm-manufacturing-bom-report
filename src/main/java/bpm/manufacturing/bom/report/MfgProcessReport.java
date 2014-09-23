package bpm.manufacturing.bom.report;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class MfgProcessReport {
	
	private MfgProcessDataBean mfgProcessObj;
	
	public MfgProcessDataBean getData(String code) {
		
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://localhost:8080/bpm-manufacturing-bom-report/mfg-process.json");
		MfgProcessDataBean mfgProcessData = webTarget.path("").request(MediaType.APPLICATION_JSON).get(MfgProcessDataBean.class);
		return mfgProcessData;
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
