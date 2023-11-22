package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.CustomerDto;
import dto.TableModel.CustomerTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.CustomerModel;
import model.impl.CustomerModelImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerWindowController implements Initializable {

    public Text pointOfSaleStyling;
    public JFXButton HomeBtn;
    public JFXButton InvoiceBtn;
    public JFXButton ReportBtn;
    public JFXButton CustomerBtn;
    public JFXButton ProductBtn;
    public AnchorPane Pane;
    public TableColumn DeleteBtn;
    public TableColumn Salary;
    public TableColumn Name;
    public TableColumn Address;
    public JFXButton UpdateBtn;
    public JFXButton SaveBtn;
    public JFXTextField CustomerSalaryField;
    public JFXTextField CustomerAddressField;
    public JFXTextField CustomerNameField;
    public JFXTextField CustomerIDField;
    public TableView<CustomerTm> CustomerTableID;
    public TableColumn ID;

    private CustomerModel customerModel = new CustomerModelImpl();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        Name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Address.setCellValueFactory(new PropertyValueFactory<>("address"));
        Salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        DeleteBtn.setCellValueFactory(new PropertyValueFactory<>("btn"));
        loadCustomerTable();

        CustomerTableID.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setData(newValue);
        });
    }
    private void clearFields() {
        CustomerTableID.refresh();
        CustomerSalaryField.clear();
        CustomerAddressField.clear();
        CustomerNameField.clear();
        CustomerIDField.clear();
        CustomerIDField.setEditable(true);
    }
    private void setData(CustomerTm newValue) {
        if (newValue != null) {
            CustomerIDField.setEditable(false);
            CustomerIDField.setText(newValue.getId());
            CustomerNameField.setText(newValue.getName());
            CustomerAddressField.setText(newValue.getAddress());
            CustomerSalaryField.setText(String.valueOf(newValue.getSalary()));
        }
    }
    private void loadCustomerTable() {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();

        try {
            List<CustomerDto> dtoList = customerModel.allCustomers();

            for (CustomerDto dto:dtoList) {
                Button btn = new Button("Delete");
                btn.setStyle("-fx-background-color: #f70000;-fx-font-weight: bold;-fx-text-fill: #ffffff;");

                CustomerTm c = new CustomerTm(
                        dto.getId(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getSalary(),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deleteCustomerBtn(c.getId());
                });

                tmList.add(c);
            }

            CustomerTableID.setItems(tmList);
            CustomerTableID.setStyle("-fx-font-weight: bold;-fx-font-family: 'Arial Black'");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void deleteCustomerBtn(String id) {
        try {
            boolean isDeleted = customerModel.deleteCustomer(id);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Customer Deleted!").show();
                loadCustomerTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void SaveBtnOnAction(ActionEvent actionEvent) {
        try {
            boolean isSaved = customerModel.SaveBtn(new CustomerDto(CustomerIDField.getText(),
                    CustomerNameField.getText(),
                    CustomerAddressField.getText(),
                    Double.parseDouble(CustomerSalaryField.getText())
            ));
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Customer Saved!").show();
                loadCustomerTable();
                clearFields();
            }

        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void UpdateBtnOnAction(ActionEvent actionEvent) {
        try {
            boolean isUpdated = customerModel.UpdateBtn(new CustomerDto(CustomerIDField.getText(),
                    CustomerNameField.getText(),
                    CustomerAddressField.getText(),
                    Double.parseDouble(CustomerSalaryField.getText())
            ));
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Customer Updated!").show();
                loadCustomerTable();
                clearFields();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    //===============================================================================================================================
    public void ProductBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) Pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/Assets/Assets/fxml/ProductsWindow.fxml"))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void CustomerBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) Pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/Assets/Assets/fxml/CustomerWindow.fxml"))));
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReportBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) Pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/Assets/Assets/fxml/ReportWindow.fxml"))));
            stage.centerOnScreen();
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
