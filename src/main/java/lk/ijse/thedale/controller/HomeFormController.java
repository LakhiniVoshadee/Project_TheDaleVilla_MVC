package lk.ijse.thedale.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import lk.ijse.thedale.db.Dbconnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeFormController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


//        try {
//            JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/item.jrxml"));
//            JasperReport compileReport = JasperCompileManager.compileReport(load);
//
//            // JasperReport  compileReport = (JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/report/item.jasper"));
//
//            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null);
//            JasperViewer.viewReport(jasperPrint, false);
//        } catch (JRException e) {
//            throw new RuntimeException(e);
//        }

       /* try {
            JasperDesign load = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/report/room_bill.jrxml"));
            JasperReport compileReport = JasperCompileManager.compileReport(load);

            //JasperReport compileReport=(JasperReport) JRLoader.loadObject(this.getClass().getResource("/views/reports/TrainScheduleReport.jasper"));
            Connection connection = Dbconnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, connection);
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }*/

    }
    @FXML
    private Pane pagingPane;

}
