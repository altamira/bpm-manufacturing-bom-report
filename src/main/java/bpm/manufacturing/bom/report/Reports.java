package bpm.manufacturing.bom.report;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
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
     * @return 
     *
     */
	@GET @Path("/materials/{orderId}")
    @Produces("application/pdf") 
    public  Response getIt(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("orderId") String paramOrderId) throws ServletException, IOException {
		
		//GET THE LOGO FILE FROM RESOURCE
		InputStream reportLogo = getClass().getResourceAsStream("/images/report-header-logo.png");

		 BufferedImage imfg = null;
		 try {
             //InputStream in = new ByteArrayInputStream(requestReportAltamiraimage);
             imfg = ImageIO.read(reportLogo);
		 } catch (Exception e1) {
             e1.printStackTrace();
		 }
        
		//GET THE JASPER FILE
		InputStream reportStream = getClass().getResourceAsStream("/reports/bom-by-order-report.jasper");
		if (reportStream == null) {
        	return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Não foi possivel carregar o relatorio !").build();
        }
		
    	ArrayList<BomDataBean> dataList = new ArrayList<BomDataBean>();
    	Map<String, Object> parameters = new HashMap<String, Object>();
    	JasperPrint jasperPrint;
    	byte[] pdf = null;
		
		//GET THE REPORT DATA FROM API
		try {
			Client client = ClientBuilder.newClient();
			WebTarget webTarget = client.target("http://altamira-api.elasticbeanstalk.com/webapi/order/");
			String responseEntity = webTarget.path(paramOrderId).request(MediaType.APPLICATION_JSON).get(String.class);
			JSONParser parser=new JSONParser();
			
			//PARSE JSON DATA- STRING TO JSON OBJECT
			try {
				Object responseObj = parser.parse(responseEntity);
				JSONObject responseJsonObj = (JSONObject)responseObj;
				
				JSONObject orderJsonObj = responseJsonObj;
				long orderNumber = (long) orderJsonObj.get("number");
				String customer = (String) orderJsonObj.get("customer");
				String salesRepresentative = (String) orderJsonObj.get("representative");
				long createdTimeStamp = (long) orderJsonObj.get("created");
				long deliveryTimeStamp = (long) orderJsonObj.get("delivery");
				double sumTotalWeight = (double) 0.00;
				
				String quotation = (String) orderJsonObj.get("quotation");
				String comment = (String) orderJsonObj.get("comment");
				
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
				    
				//SET THE PARAMETERS FOR JASPER REPORT
		        parameters.put("Title", "Lista de Material - " + orderNumber);
		     	parameters.put("UserName", "MASTER");
		     	parameters.put("Customer", customer);
		     	parameters.put("Representative", salesRepresentative);
		     	parameters.put("OrderID", orderNumber);
		     	parameters.put("LogoImage", imfg);
		     	parameters.put("DateRequest", orderDateDisplay);
		     	parameters.put("DeliveryTime", deliveryDateDisplay);
		     	parameters.put("DeliveryWeek", deliveryWeekDisplay);
		     	parameters.put("NoBudget", quotation);
		     	// TODO REMOVE THE HARD-CODE VALUE FOR NoProject
		     	parameters.put("NoProject", (String) orderJsonObj.get("project"));
		     	parameters.put("Finish", (String) orderJsonObj.get("finish"));
		     	parameters.put("Comment", comment);
		     	
		     	Object itemJsonObject = orderJsonObj.get("item");
		     	if (itemJsonObject instanceof JSONArray) {
		     		JSONArray itemJsonArray = (JSONArray)orderJsonObj.get("item");
		     		for(int i = 0; i < itemJsonArray.size(); i++) {
						Object itemObject = itemJsonArray.get(i);
						JSONObject itemJsonObj = (JSONObject)itemObject;
						long itemNo = (long) itemJsonObj.get("item");
						String itemDescription = (String) itemJsonObj.get("description");
						
						Object prodJsonObject = itemJsonObj.get("product");
						if (prodJsonObject instanceof JSONArray) {
							JSONArray prodJsonArray = (JSONArray)itemJsonObj.get("product");
				            for(int j = 0; j < prodJsonArray.size(); j++) {
								Object prodObject = prodJsonArray.get(j);
								JSONObject prodJsonObj = (JSONObject)prodObject;
								
								double weightTotal = (double) prodJsonObj.get("weight");
								String color = (String) prodJsonObj.get("color");
								String prodDescription = (String) prodJsonObj.get("description");
								double quantity = (double) prodJsonObj.get("quantity");
								String prodCode = (String) prodJsonObj.get("code");
								
								//ADD TO SUM TOTAL WEIGHT
								sumTotalWeight = sumTotalWeight + weightTotal;
	
								//CREATE THE OBJECT
								BomDataBean dataBean = new BomDataBean();
								dataBean.setItemNo(itemNo);
								dataBean.setItemDescription(itemDescription);
								dataBean.setProdQty(quantity);
								dataBean.setProdDescription(prodDescription);
								dataBean.setProdColor(color);
								dataBean.setProdWeight(weightTotal/quantity);
								dataBean.setProdWeightTotal(weightTotal);
	
								//ADD THE OBJECT TO DATALIST
								dataList.add(dataBean);		               
				            }
						}
						else {
							Object prodObject = prodJsonObject;
							JSONObject prodJsonObj = (JSONObject)prodObject;
							
							double weightTotal = (double) prodJsonObj.get("weight");
							String color = (String) prodJsonObj.get("color");
							String prodDescription = (String) prodJsonObj.get("description");
							double quantity = (double) prodJsonObj.get("quantity");
							String prodCode = (String) prodJsonObj.get("code");
							
							//ADD TO SUM TOTAL WEIGHT
							sumTotalWeight = sumTotalWeight + weightTotal;

							//CREATE THE OBJECT
							BomDataBean dataBean = new BomDataBean();
							dataBean.setItemNo(itemNo);
							dataBean.setItemDescription(itemDescription);
							dataBean.setProdQty(quantity);
							dataBean.setProdDescription(prodDescription);
							dataBean.setProdColor(color);
							dataBean.setProdWeight(weightTotal/quantity);
							dataBean.setProdWeightTotal(weightTotal);

							//ADD THE OBJECT TO DATALIST
							dataList.add(dataBean);		
						}
			        }
		     	}
		     	else {
		     		Object itemObject = itemJsonObject;
					JSONObject itemJsonObj = (JSONObject)itemObject;
					long itemNo = (long) itemJsonObj.get("item");
					String itemDescription = (String) itemJsonObj.get("description");
					Object prodJsonObject = itemJsonObj.get("product");
					if (prodJsonObject instanceof JSONArray) {
						JSONArray prodJsonArray = (JSONArray)itemJsonObj.get("product");
			            for(int j = 0; j < prodJsonArray.size(); j++) {
							Object prodObject = prodJsonArray.get(j);
							JSONObject prodJsonObj = (JSONObject)prodObject;
							
							double weightTotal = (double) prodJsonObj.get("weight");
							String color = (String) prodJsonObj.get("color");
							String prodDescription = (String) prodJsonObj.get("description");
							double quantity = (double) prodJsonObj.get("quantity");
							String prodCode = (String) prodJsonObj.get("code");
							
							//ADD TO SUM TOTAL WEIGHT
							sumTotalWeight = sumTotalWeight + weightTotal;

							//CREATE THE OBJECT
							BomDataBean dataBean = new BomDataBean();
							dataBean.setItemNo(itemNo);
							dataBean.setItemDescription(itemDescription);
							dataBean.setProdQty(quantity);
							dataBean.setProdDescription(prodDescription);
							dataBean.setProdColor(color);
							dataBean.setProdWeight(weightTotal/quantity);
							dataBean.setProdWeightTotal(weightTotal);

							//ADD THE OBJECT TO DATALIST
							dataList.add(dataBean);		               
			            }
					}
					else {
						Object prodObject = prodJsonObject;
						JSONObject prodJsonObj = (JSONObject)prodObject;
						
						double weightTotal = (double) prodJsonObj.get("weight");
						String color = (String) prodJsonObj.get("color");
						String prodDescription = (String) prodJsonObj.get("description");
						double quantity = (double) prodJsonObj.get("quantity");
						String prodCode = (String) prodJsonObj.get("code");
						
						//ADD TO SUM TOTAL WEIGHT
						sumTotalWeight = sumTotalWeight + weightTotal;

						//CREATE THE OBJECT
						BomDataBean dataBean = new BomDataBean();
						dataBean.setItemNo(itemNo);
						dataBean.setItemDescription(itemDescription);
						dataBean.setProdQty(quantity);
						dataBean.setProdDescription(prodDescription);
						dataBean.setProdColor(color);
						dataBean.setProdWeight(weightTotal/quantity);
						dataBean.setProdWeightTotal(weightTotal);

						//ADD THE OBJECT TO DATALIST
						dataList.add(dataBean);		
					}
		        }
		     	parameters.put("SumTotalWeight", sumTotalWeight);
			} catch(ParseException pe) {
				System.out.println("position: " + pe.getPosition());
				System.out.println(pe);
			}

		} catch (Exception e) {
				e.printStackTrace();
				return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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
		
		ArrayList<MfgOperationDisplay> dataList = new ArrayList<MfgOperationDisplay>();
		JasperPrint jasperPrint;
		byte[] pdf = null;
		
		MfgProcessReport mfgProcessReport = new MfgProcessReport();
		MfgProcessDataBean mfgReportData = mfgProcessReport.getData(paramCode);
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
     	parameters.put("LogoImage", mfgProcessReport.getLogo());
		
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
			System.out.println(i);
            System.out.println(mfgOperations.get(i).getSequence());
            System.out.println(mfgOperations.get(i).getName());
            System.out.println(mfgOperations.get(i).getDescription());
            System.out.println(mfgOperations.get(i).getCroqui());
            //System.out.println(mfgOperations.get(i).getInput());
            //System.out.println(mfgOperations.get(i).getOutput());
            mfgInput = mfgOperations.get(i).getInput();
    		for (int j = 0; j < mfgInput.size(); j++) {
    			String newLineText = "\r\n";
    			if(i == (revision.size() - 1)) {
    				newLineText = "";
    			}
    			String qtyText = mfgInput.get(j).getQuantity() + " " + mfgInput.get(j).getUnit();
    			inputCodeList = inputCodeList + mfgInput.get(j).getCode()  + newLineText;
    			inputMaterialList = inputMaterialList + mfgInput.get(j).getDescription()  + newLineText;
    			inputQtyList = inputQtyList + qtyText  + newLineText;
    			
    			System.out.println(j);
                System.out.println(mfgInput.get(j).getCode());
                System.out.println(mfgInput.get(j).getDescription());
                System.out.println(mfgInput.get(j).getQuantity());
                System.out.println(mfgInput.get(j).getUnit());
            }
    		
    		List<MfgMaterial> mfgOutput = mfgOperations.get(i).getOutput();
    		for (int j = 0; j < mfgOutput.size(); j++) {
    			String newLineText = "\r\n";
    			if(i == (revision.size() - 1)) {
    				newLineText = "";
    			}
    			String qtyText = mfgOutput.get(j).getQuantity() + " " + mfgOutput.get(j).getUnit();
    			outputCodeList = outputCodeList + mfgOutput.get(j).getCode()  + newLineText;
    			outputMaterialList = outputMaterialList + mfgOutput.get(j).getDescription()  + newLineText;
    			outputQtyList = outputQtyList + qtyText  + newLineText;
    			System.out.println(j);
                System.out.println(mfgOutput.get(j).getCode());
                System.out.println(mfgOutput.get(j).getDescription());
                System.out.println(mfgOutput.get(j).getQuantity());
                System.out.println(mfgOutput.get(j).getUnit());
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
	
	private String numberFormat(float number) {
		Locale locale = new Locale("pt", "BR");
		NumberFormat numberFormat = NumberFormat.getInstance(locale);
		String formattedValue = numberFormat.format(number);
		return formattedValue;
	}
}
