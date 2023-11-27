package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDto;
import dto.ProductsDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.CustomerModel;
import model.ProductsModel;
import model.impl.CustomerModelImpl;
import model.impl.ProductsModelImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InvoiceWindowController implements Initializable {
    public Text pointOfSaleStyling;
    public JFXButton InvoiceBtn;
    public JFXButton ReportBtn;
    public JFXButton CustomerBtn;
    public JFXButton ProductBtn;
    public JFXButton HomeBtn;
    public AnchorPane Pane;
    public JFXComboBox CustomerIDDragDown;
    public JFXComboBox ProductIDDragDown;
    public JFXTextField NameLabel;
    public JFXTextField DescriptionLabel;
    public JFXComboBox QuantityDragDown;
    public JFXTextField UnitPriceLabel;
    public JFXTextField AmountID;
    public TreeTableColumn SI_increment;
    public TreeTableColumn DescribeCell;
    public TreeTableColumn QuantityCell;
    public TreeTableColumn RateCell;
    public TreeTableColumn AmountCell;
    public TreeTableColumn OptionCell;
    public Label InvoiceIncrement;
    public Label TotalLabel;
    public JFXButton CheckOutID;

    private List<CustomerDto> customers;
    private List<ProductsDto> products;

    private CustomerModel customerModel = new CustomerModelImpl();
    private ProductsModel productsModel = new ProductsModelImpl();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCustomerID();
        loadProductID();
    }

    private void loadProductID() {
        try {
            products = productsModel.productAllCustomers();
            ObservableList list = FXCollections.observableArrayList();
            for (ProductsDto dto:products) {
                list.add(dto.getCode());
            }
            ProductIDDragDown.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerID() {
        try {
            customers = customerModel.allCustomers();
            ObservableList list = FXCollections.observableArrayList();
            for (CustomerDto dto:customers) {
                list.add(dto.getId());
            }
            CustomerIDDragDown.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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

    public void CheckOutOnAction(ActionEvent actionEvent) {

    }
}
