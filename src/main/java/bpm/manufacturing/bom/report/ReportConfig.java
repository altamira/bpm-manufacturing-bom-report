package bpm.manufacturing.bom.report;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import sun.misc.BASE64Decoder;

public abstract class ReportConfig {
	
	/**
     * Get the logo image
     * @param 
     * @return buffered logo image
     */
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
	
	/**
     * Decode string to image
     * @param imageString The string to decode
     * @return decoded image
     */
    public static BufferedImage decodeToImage(String imageString) {

        BufferedImage image = null;
        byte[] imageByte;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            imageByte = decoder.decodeBuffer(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }


}
