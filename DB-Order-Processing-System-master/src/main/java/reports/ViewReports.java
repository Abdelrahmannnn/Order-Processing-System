package reports;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ViewReports {

	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/book_store";
	private static final String DATABASE_USERNAME = "root";
	private static final String DATABASE_PASSWORD = "root";

	private Connection connection;

	public ViewReports() {
		try {
			connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void totalSalesSummary() {
		if (connection != null) {
			try {
				JasperReport jasperReport = JasperCompileManager
						.compileReport("src/main/java/reports/TotalSalesSummary.jrxml");
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
				JasperViewer.viewReport(jasperPrint, false);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void topCustomers() {
		if (connection != null) {
			try {
				JasperReport jasperReport = JasperCompileManager
						.compileReport("src/main/java/reports/TopCustomers.jrxml");
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
				JasperViewer.viewReport(jasperPrint, false);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void topSellingBooks() {
		if (connection != null) {
			try {
				JasperReport jasperReport = JasperCompileManager
						.compileReport("src/main/java/reports/TopSellingBooks.jrxml");
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);
				JasperViewer.viewReport(jasperPrint, false);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
