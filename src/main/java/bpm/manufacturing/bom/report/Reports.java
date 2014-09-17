package bpm.manufacturing.bom.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
	@GET @Path("/materials/{orderId}")
    @Produces("application/pdf") 
    public  void getIt(@Context HttpServletRequest req, @Context HttpServletResponse resp, @PathParam("orderId") String paramOrderId) throws ServletException, IOException {
		
		//CALCULATE THE PATH OF THE JASPER FILE
		URL resource = getClass().getResource("/");
		String path = resource.getPath();
		path = path.replace("WEB-INF/classes/", "");
		String sourceFileName = "reports/bom-by-order.jasper";
		String reportPath = path + sourceFileName; 
		String logoPath = path + "images/report-header-logo.png";
    	
    	
    	ArrayList<BomDataBean> dataList = new ArrayList<BomDataBean>();
    	Map<String, Object> parameters = new HashMap<String, Object>();
    	JasperPrint jasperPrint;
		
		//GET THE REPORT DATA FROM API
		try {
			Client client = ClientBuilder.newClient();
			WebTarget webTarget = client.target("http://esb.altamira.com.br:8081/manufacturing/bom/checklist/");
			String responseEntity = webTarget.path(paramOrderId).request(MediaType.APPLICATION_JSON).get(String.class);
			System.out.println(responseEntity);
			JSONParser parser=new JSONParser();
			
			//PARSE JSON DATA- STRING TO JSON OBJECT
			try {
				Object responseObj = parser.parse(responseEntity);
				JSONObject responseJsonObj = (JSONObject)responseObj;
				JSONObject orderJsonObj = (JSONObject)responseJsonObj.get("order");
				String orderNumber = (String) orderJsonObj.get("number");
				String customer = (String) orderJsonObj.get("customer");
				String salesRepresentative = (String) orderJsonObj.get("sales_representative");
				long orderTimeStamp = Long.valueOf((String)orderJsonObj.get("order_date")).longValue();
				long deliveryTimeStamp = Long.valueOf((String)orderJsonObj.get("delivery_date")).longValue() ;
				float sumTotalWeight = (float) 0.00;
				
				String quotation = (String) orderJsonObj.get("quotation");
				// TODO NEED TO CHECK WHERE TO USE THIS COMMENT VALUE
				String comment = (String) orderJsonObj.get("comment");
				
				//FORMATING ORDER DATE
				String orderDateDisplay = new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date(orderTimeStamp));
				
				//FORMATING DELIVERT DATE
				String deliveryDateDisplay = new java.text.SimpleDateFormat("w/yyyy").format(new java.util.Date(deliveryTimeStamp));
				    
				//SET THE PARAMETERS FOR JASPER REPORT
		        parameters.put("Title", "Lista de Material - " + orderNumber);
		     	parameters.put("UserName", "MASTER");
		     	parameters.put("Customer", customer);
		     	parameters.put("Representative", salesRepresentative);
		     	parameters.put("OrderID", orderNumber);
		     	parameters.put("ImgUrl", logoPath);
		     	parameters.put("DateRequest", orderDateDisplay);
		     	parameters.put("DeliveryTime", deliveryDateDisplay);
		     	parameters.put("NoBudget", quotation);
		     	// TODO REMOVE THE HARD-CODE VALUE FOR NoProject
		     	parameters.put("NoProject", "00000");
		     	parameters.put("Finish", "ACABAMENTO ESPECIAL");
		     	parameters.put("FooterText1", "￼Observações DO PEDIDO");
		     	parameters.put("FooterText2", "￼ENTREGAR NA OBRA: RUADAS PALMEIRAS, 4587 - LAPA -SP");
		     	Object itemJsonObject = orderJsonObj.get("item");
		     	if (itemJsonObject instanceof JSONArray) {
		     		JSONArray itemJsonArray = (JSONArray)orderJsonObj.get("item");
		     		for(int i = 0; i < itemJsonArray.size(); i++) {
						Object itemObject = itemJsonArray.get(i);
						JSONObject itemJsonObj = (JSONObject)itemObject;
						String itemNo = (String) itemJsonObj.get("item");
						String itemDescription = (String) itemJsonObj.get("description");
						
						Object prodJsonObject = itemJsonObj.get("product");
						if (prodJsonObject instanceof JSONArray) {
							JSONArray prodJsonArray = (JSONArray)itemJsonObj.get("product");
				            for(int j = 0; j < prodJsonArray.size(); j++) {
								Object prodObject = prodJsonArray.get(j);
								JSONObject prodJsonObj = (JSONObject)prodObject;
								//String weight = (String) prodJsonObj.get("weight");
								Float weightTotal = Float.parseFloat((String)prodJsonObj.get("weight"));
								String color = (String) prodJsonObj.get("color");
								String prodDescription = (String) prodJsonObj.get("description");
								Float quantity = Float.parseFloat((String) prodJsonObj.get("quantity"));
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
							//String weight = (String) prodJsonObj.get("weight");
							Float weightTotal = Float.parseFloat((String)prodJsonObj.get("weight"));
							String color = (String) prodJsonObj.get("color");
							String prodDescription = (String) prodJsonObj.get("description");
							Float quantity = Float.parseFloat((String) prodJsonObj.get("quantity"));
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
					String itemNo = (String) itemJsonObj.get("item");
					String itemDescription = (String) itemJsonObj.get("description");
					Object prodJsonObject = itemJsonObj.get("product");
					if (prodJsonObject instanceof JSONArray) {
						JSONArray prodJsonArray = (JSONArray)itemJsonObj.get("product");
			            for(int j = 0; j < prodJsonArray.size(); j++) {
							Object prodObject = prodJsonArray.get(j);
							JSONObject prodJsonObj = (JSONObject)prodObject;
							//String weight = (String) prodJsonObj.get("weight");
							Float weightTotal = Float.parseFloat((String)prodJsonObj.get("weight"));
							String color = (String) prodJsonObj.get("color");
							String prodDescription = (String) prodJsonObj.get("description");
							Float quantity = Float.parseFloat((String) prodJsonObj.get("quantity"));
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
						//String weight = (String) prodJsonObj.get("weight");
						Float weightTotal = Float.parseFloat((String)prodJsonObj.get("weight"));
						String color = (String) prodJsonObj.get("color");
						String prodDescription = (String) prodJsonObj.get("description");
						Float quantity = Float.parseFloat((String) prodJsonObj.get("quantity"));
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
		     	System.out.println(sumTotalWeight);
		        
			} catch(ParseException pe) {
				System.out.println("position: " + pe.getPosition());
				System.out.println(pe);
			}

		} catch (Exception e) {
				e.printStackTrace();
		}
		
		//PRINT THE PDF REPORT
    	try {
    		JRBeanCollectionDataSource beanColDataSource =  new JRBeanCollectionDataSource(dataList);
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
