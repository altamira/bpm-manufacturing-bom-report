package bpm.manufacturing.bom.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.math.BigDecimal;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.jasperreports.engine.JRDataSource;

/**
 * @author 
 */

public class BillOfMaterial {
	
	public JasperReportBuilder build() { 
		JasperReportBuilder report = report();
		StyleBuilder boldStyle = stl.style().bold();
		StyleBuilder boldCenteredStyle = stl.style(boldStyle).setHorizontalAlignment(HorizontalAlignment.CENTER);
		report.columns(//add columns
		//            title,     field name     data type
		col.column("Item",       "item",      type.stringType()),
		col.column("Quantity",   "quantity",  type.integerType()),
		col.column("Unit price", "unitprice", type.bigDecimalType()))

		// ADD REPORT HEADER COMPANY LOGO
		// this.getClass().getResourceAsStream("/report-header-logo.png");
		
		//SET THE PAGE HEADERS
		.pageHeader(cmp.text ("ALTAMIRA Indústria Metalúrgica Ltda.").setStyle(boldCenteredStyle))
		.pageHeader(cmp.text ("Rua Ganges, 528 - Vila Nova Manchester").setStyle(boldCenteredStyle))
		.pageHeader(cmp.text ("03445-030 - São Paulo - SP Fone: (11) 2095-2855 Fax: (11) 2095-2865").setStyle(boldCenteredStyle))
		.pageHeader(cmp.text ("CNPJ: 43.799.295/0001-10 IE: 109.095.380.110").setStyle(boldCenteredStyle))
		
		.pageFooter(cmp.pageXofY())//shows number of page at page footer
		.setDataSource(createDataSource());//set datasource
		return report;
	}
	
	private JRDataSource createDataSource() {
		DRDataSource dataSource = new DRDataSource("item", "quantity", "unitprice");
		dataSource.add("Notebook", 1, new BigDecimal(500));
		dataSource.add("DVD", 5, new BigDecimal(30));
		dataSource.add("DVD", 1, new BigDecimal(28));
		dataSource.add("DVD", 5, new BigDecimal(32));
		dataSource.add("Book", 3, new BigDecimal(11));
		dataSource.add("Book", 1, new BigDecimal(15));
		dataSource.add("Book", 5, new BigDecimal(10));
		dataSource.add("Book", 8, new BigDecimal(9));
		return dataSource;
	}
}

