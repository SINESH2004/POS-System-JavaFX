package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportWindowController implements Initializable {
    public Text pointOfSaleStyling;
    public JFXButton HomeBtn;
    public JFXButton InvoiceBtn;
    public JFXButton ReportBtn;
    public JFXButton CustomerBtn;
    public JFXButton ProductBtn;
    public AnchorPane Pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void ProductBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) Pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/Assets/Assets/fxml/ProductsWindow.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CustomerBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) Pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/Assets/Assets/fxml/CustomerWindow.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReportBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) Pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/Assets/Assets/fxml/ReportWindow.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void InvoiceBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) Pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/Assets/Assets/fxml/InvoiceWindow.fxml"))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void HomeBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) Pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/Assets/Assets/fxml/DashBoardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
