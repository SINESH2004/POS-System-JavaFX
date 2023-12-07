package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.ProductsDto;
import dto.TableModel.ProductsTm;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import model.ProductsModel;
import model.impl.ProductsModelImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;

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
    public JFXTextField CodeInput;

    private ProductsModel productsModel= new ProductsModelImpl();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Code.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        Desc.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));
        UnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitPrice"));
        Qty.setCellValueFactory(new TreeItemPropertyValueFactory<>("quantity"));
        Option.setCellValueFactory(new TreeItemPropertyValueFactory<>("delete"));
        loadProductsTable();

        TableShown.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue!=null) {
                setData(newValue.getValue());
            }
        });

        SearchIDInput.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                TableShown.setPredicate(new Predicate<TreeItem<ProductsTm>>() {
                    @Override
                    public boolean test(TreeItem<ProductsTm> productsTmTreeItem) {
                        return productsTmTreeItem.getValue().getCode().contains(newValue)|
                                productsTmTreeItem.getValue().getDescription().contains(newValue);
                    }
                });
            }
        });

    }
    private void clearFields() {
        TableShown.refresh();
        PriceInput.clear();
        CodeInput.clear();
        QtyInput.clear();
        DescriptionInput.clear();
        TableShown.setEditable(true);
    }
    private void setData(ProductsTm newValue) {
        if (newValue != null) {
            TableShown.setEditable(false);
            CodeInput.setText(newValue.getCode());
            DescriptionInput.setText(newValue.getDescription());
            PriceInput.setText(String.valueOf(newValue.getUnitPrice()));
            QtyInput.setText(String.valueOf(newValue.getQuantity()));
        }
    }

    private void loadProductsTable() {
        ObservableList<ProductsTm> tmList = FXCollections.observableArrayList();

        try {
            List<ProductsDto> dtoList = productsModel.allProducts();

            for (ProductsDto dto : dtoList) {
                JFXButton btn = new JFXButton("Delete");
                btn.setStyle("-fx-background-color: #f70000;-fx-font-weight: bold;-fx-text-fill: #ffffff;");


                ProductsTm c = new ProductsTm(
                        dto.getCode(),
                        dto.getDescription(),
                        dto.getUnitPrice(),
                        dto.getQuantity(),
                        btn
                );

                btn.setOnAction(actionEvent -> {
                    deleteProduct(c.getCode());
                });

                tmList.add(c);
            }

            TreeItem<ProductsTm> root = new RecursiveTreeItem<>(tmList, RecursiveTreeObject::getChildren);
            TableShown.setRoot(root);
            TableShown.setStyle("-fx-font-weight: bold;-fx-font-family: 'Arial Black'");
            TableShown.setShowRoot(false);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void deleteProduct(String code) {
        try {
            boolean isDeleted = productsModel.deleteProduct(code);
            if (isDeleted){
                new Alert(Alert.AlertType.INFORMATION,"Product Deleted!").show();
                loadProductsTable();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public void SearchBtnOnAction(ActionEvent actionEvent) {
        String searchCode = SearchIDInput.getText().trim();

        if (!searchCode.isEmpty()) {
            try {
                ProductsDto product = productsModel.getProductByCode(searchCode);

                if (product != null) {
                    CodeInput.setText(product.getCode());
                    DescriptionInput.setText(product.getDescription());
                    PriceInput.setText(String.valueOf(product.getUnitPrice()));
                    QtyInput.setText(String.valueOf(product.getQuantity()));
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Product not found!").show();
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Please enter a product code to search.").show();
        }
    }

    public void UpdateBtnOnAction(ActionEvent actionEvent) {
        try {
            boolean isUpdated = productsModel.productUpdateBtn(new ProductsDto(CodeInput.getText(),
                    DescriptionInput.getText(),
                    Double.parseDouble(PriceInput.getText()),
                    Integer.parseInt(QtyInput.getText())
            ));
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Product Updated!").show();
                loadProductsTable();
                clearFields();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void SaveBtnOnAction(ActionEvent actionEvent) {
        try {
            boolean isSaved = productsModel.productSaveBtn(new ProductsDto(CodeInput.getText(),
                    DescriptionInput.getText(),
                    Double.parseDouble(PriceInput.getText()),
                    Integer.parseInt(QtyInput.getText())
            ));
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Product Saved!").show();
                loadProductsTable();
                clearFields();
            }

        } catch (SQLIntegrityConstraintViolationException ex){
            new Alert(Alert.AlertType.ERROR,"Duplicate Entry").show();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
//=======================================================================================

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
            stage.centerOnScreen();
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
}
