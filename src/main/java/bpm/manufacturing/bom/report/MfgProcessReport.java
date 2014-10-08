package bpm.manufacturing.bom.report;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

public class MfgProcessReport {
	
	public MfgProcessDataBean getData(String code) {
		
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target("http://localhost:8080/bpm-manufacturing-bom-report/mfg-process.json");
		MfgProcessDataBean mfgProcessData = webTarget.path("").request(MediaType.APPLICATION_JSON).get(MfgProcessDataBean.class);
		return mfgProcessData;
	}
	
	public Response getReport(String code) {
		
		ArrayList<MfgOperationDisplay> dataList = new ArrayList<MfgOperationDisplay>();
		JasperPrint jasperPrint;
		byte[] pdf = null;
		
		MfgProcessDataBean mfgReportData = this.getData(code);
		List<MfgMaterial> mfgInput = null;
		String revisionByData = "";
		String revisionDateData = "";
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		//SET THE PARAMETERS
		parameters.put("Title", "Processo de Fabricação");
     	parameters.put("UserName", "MASTER");
     	parameters.put("Code", mfgReportData.getCode());
     	parameters.put("Description", mfgReportData.getDescription());
     	parameters.put("Color", mfgReportData.getColor());
     	parameters.put("Weight", mfgReportData.getWeight());
     	parameters.put("Width", mfgReportData.getWidth());
     	parameters.put("Length", mfgReportData.getLength());
     	parameters.put("Finish", mfgReportData.getFinish());
     	parameters.put("LogoImage", this.getLogo());
		
		List<MfgRevision> revision = mfgReportData.getRevision();
		for (int i = 0; i < revision.size(); i++) {	
			String newLineText = "\r\n";
			if(i == (revision.size() - 1)) {
				newLineText = "";
			}
            revisionByData = revisionByData + revision.get(i).getBy()  + newLineText;
            revisionDateData = revisionDateData + new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(revision.get(i).getDate())) + newLineText;
        }
		parameters.put("RevisionByData", revisionByData);
     	parameters.put("RevisionDateData", revisionDateData);
     	
		List<MfgOperation> mfgOperations = mfgReportData.getOperation();
		for (int i = 0; i < mfgOperations.size(); i++) {
			String operationName = mfgOperations.get(i).getSequence() + " - " + mfgOperations.get(i).getName();
			String inputCodeList = "";
			String inputMaterialList = "";
			String inputQtyList = "";
			String outputCodeList = "";
			String outputMaterialList = "";
			String outputQtyList = "";
			String descriptionOperation = mfgOperations.get(i).getDescription();
			String scetchOfOperation = mfgOperations.get(i).getCroqui();
			
            mfgInput = mfgOperations.get(i).getInput();
    		for (int j = 0; j < mfgInput.size(); j++) {
    			String newLineText = "\r\n";
    			if(j == (mfgInput.size() - 1)) {
    				newLineText = "";
    			}
    			String qtyText = mfgInput.get(j).getQuantity() + " " + mfgInput.get(j).getUnit();
    			inputCodeList = inputCodeList + mfgInput.get(j).getCode()  + newLineText;
    			inputMaterialList = inputMaterialList + mfgInput.get(j).getDescription()  + newLineText;
    			inputQtyList = inputQtyList + qtyText  + newLineText;
    			
            }
    		
    		List<MfgMaterial> mfgOutput = mfgOperations.get(i).getOutput();
    		for (int j = 0; j < mfgOutput.size(); j++) {
    			String newLineText = "\r\n";
    			if(j == (mfgOutput.size() - 1)) {
    				newLineText = "";
    			}
    			String qtyText = mfgOutput.get(j).getQuantity() + " " + mfgOutput.get(j).getUnit();
    			outputCodeList = outputCodeList + mfgOutput.get(j).getCode()  + newLineText;
    			outputMaterialList = outputMaterialList + mfgOutput.get(j).getDescription()  + newLineText;
    			outputQtyList = outputQtyList + qtyText  + newLineText;
    			
            }
    		
    		//CREATE AND ADD THE OBJECT FOR MFG OPERATION DATA LIST
    		//CREATE THE OBJECT
    		MfgOperationDisplay dataBean = new MfgOperationDisplay();
			dataBean.setName(operationName);
			dataBean.setDescriptionOperation(descriptionOperation);
			dataBean.setScetchOfOperation(scetchOfOperation);
			dataBean.setInputCodeList(inputCodeList);
			dataBean.setInputMaterialList(inputMaterialList);
			dataBean.setInputQtyList(inputQtyList);
			dataBean.setOutputCodeList(outputCodeList);
			dataBean.setOutputMaterialList(outputMaterialList);
			dataBean.setOutputQtyList(outputQtyList);

			//ADD THE OBJECT TO DATALIST
			dataList.add(dataBean);	
            
        }
		
		//GET THE JASPER FILE
		InputStream reportStream = getClass().getResourceAsStream("/reports/mfg-process/mfg-process-report.jasper");
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
