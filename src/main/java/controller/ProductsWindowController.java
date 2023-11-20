package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.DBConnection;
import dto.ProductsDto;
import dto.TableModel.ProductsTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ProductsWindowController implements Initializable {
    public BorderPane pane;
    public JFXButton SearchBtn;
    public JFXButton UpdateBtn;
    public JFXButton SaveBtn;
    public TreeTableColumn Option;
    public TreeTableColumn Qty;
    public TreeTableColumn UnitPrice;
    public TreeTableColumn Desc;
    public TreeTableColumn Code;
    public JFXTreeTableView<ProductsTm> TableShown;
    public JFXTextField SearchIDInput;
    public JFXTextField QtyInput;
    public JFXTextField PriceInput;
    public JFXTextField DescriptionInput;
    public JFXTextField txtQty;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtDesc;
    public JFXTextField CodeInput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Code.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        Desc.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        UnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitPrice"));
        Qty.setCellValueFactory(new TreeItemPropertyValueFactory<>("quantity"));
        Option.setCellValueFactory(new TreeItemPropertyValueFactory<>("delete"));
        loadProductsTable();
    }

    private void loadProductsTable() {
        ObservableList<ProductsTm> tmList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM products";

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet result = stm.executeQuery(sql);

            while (result.next()){
                JFXButton btn = new JFXButton("Delete");

                ProductsTm tableModel = new ProductsTm(
                        result.getString(1),
                        result.getString(2),
                        result.getDouble(3),
                        result.getInt(4),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deleteProduct(tableModel.getCode());
                });

                tmList.add(tableModel);
            }

            TreeItem<ProductsTm> treeItem = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            TableShown.setRoot(treeItem);
            TableShown.setShowRoot(false);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    private void deleteProduct(String code) {
        String sql = "DELETE from item WHERE code=?";

        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,code);
            int result = pstm.executeUpdate(sql);
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted!").show();
                loadProductsTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void CustomerBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/Assets/Assets/fxml/CustomerWindow.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReportBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/Assets/Assets/fxml/ReportWindow.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void InvoiceBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/Assets/Assets/fxml/InvoiceWindow.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void HomeBtnOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/Assets/Assets/fxml/DashBoardForm.fxml"))));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SearchBtnOnAction(ActionEvent actionEvent) {

    }

    public void UpdateBtnOnAction(ActionEvent actionEvent) {

    }

    public void SaveBtnOnAction(ActionEvent actionEvent) {
        ProductsDto dto = new ProductsDto(CodeInput.getText(),
                DescriptionInput.getText(),
                Double.parseDouble(PriceInput.getText()),
                Integer.parseInt(QtyInput.getText())
        );
        String sql = "INSERT INTO item VALUES(?,?,?,?)";

        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,dto.getCode());
            pstm.setString(2,dto.getDescription());
            pstm.setDouble(3,dto.getSalary());
            pstm.setInt(4,dto.getQuantity());
            int result = pstm.executeUpdate();
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Item Saved!").show();
                loadProductsTable();
            }

        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}