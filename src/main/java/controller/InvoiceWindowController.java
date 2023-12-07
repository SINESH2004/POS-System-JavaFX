package controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.CustomerDto;
import dto.OrderDetailsDto;
import dto.OrderDto;
import dto.ProductsDto;
import dto.TableModel.OrderTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.CustomerModel;
import model.OrderModel;
import model.ProductsModel;
import model.impl.CustomerModelImpl;
import model.impl.OrderModelImpl;
import model.impl.ProductsModelImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InvoiceWindowController implements Initializable {
    public JFXButton InvoiceBtn;
    public JFXButton ReportBtn;
    public JFXButton CustomerBtn;
    public JFXButton ProductBtn;
    public JFXButton HomeBtn;
    public AnchorPane Pane;
    public JFXComboBox ProductIDDragDown;
    public JFXTextField NameLabel;
    public JFXTextField UnitPriceLabel;
    public JFXTextField AmountID;
    public TreeTableColumn DescribeCell;
    public TreeTableColumn QuantityCell;
    public TreeTableColumn AmountCell;
    public TreeTableColumn OptionCell;
    public Label InvoiceIncrement;
    public JFXButton CheckOutID;
    public JFXTextField QuantityLabel;
    public JFXButton AddToCartBtn;
    public JFXTreeTableView<OrderTm> TableView;
    public JFXComboBox CustomerIDragDown;
    public JFXTextField ProductName;
    public TreeTableColumn CodeCell;
    public Label TotalLabel;
    public Label InvoiceNo;
    private List<CustomerDto> customers;
    private List<ProductsDto> products;
    private double total = 0;

    private CustomerModel customerModel = new CustomerModelImpl();
    private ProductsModel productsModel = new ProductsModelImpl();
    private OrderModel orderModel = new OrderModelImpl();
    private ObservableList<OrderTm> tmList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CodeCell.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        DescribeCell.setCellValueFactory(new TreeItemPropertyValueFactory<>("desc"));
        QuantityCell.setCellValueFactory(new TreeItemPropertyValueFactory<>("quantity"));
        AmountCell.setCellValueFactory(new TreeItemPropertyValueFactory<>("amount"));
        OptionCell.setCellValueFactory(new TreeItemPropertyValueFactory<>("btn"));

        loadCustomerID();
        loadProductID();

        generateID();

        CustomerIDragDown.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            for (CustomerDto dto :customers) {
                if (dto.getId().equals(newValue)){
                    NameLabel.setText(dto.getName());
                }
            }
        });
        ProductIDDragDown.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            for (ProductsDto dto :products) {
                if (dto.getCode().equals(newValue)){
                    ProductName.setText(dto.getDescription());
                    UnitPriceLabel.setText(String.format("%.2f",dto.getUnitPrice()));
                }
            }
        });
    }
    private void loadProductID() {
        try {
            products = productsModel.allProducts();
            ObservableList list =FXCollections.observableArrayList();
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
            CustomerIDragDown.setItems(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void generateID(){
        try {
            OrderDto Dto = orderModel.lastOrder();
            if (Dto!=null) {
                String orderID = Dto.getOrderID();
                int s = Integer.parseInt(orderID.split("[D]")[1]);
                s++;
                InvoiceNo.setText(String.format("D%03d",s));
            }else{
                InvoiceNo.setText("D001");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void CheckOutOnAction(ActionEvent actionEvent) {
    List<OrderDetailsDto> list = new ArrayList<>();
        for (OrderTm tm:tmList) {
            list.add(new OrderDetailsDto(
               InvoiceNo.getText(),
               tm.getCode(),
                    tm.getQuantity(), tm.getAmount()/tm.getQuantity()
            ));
        }
            boolean isSaved = false;
            try {
                isSaved = orderModel.saveOrder(new OrderDto(
                        InvoiceNo.getText(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")).toString(),
                        CustomerIDragDown.getValue().toString(),
                        list
                ));

                if (isSaved){
                    new Alert(Alert.AlertType.INFORMATION,"Order Saved").show();
                }else{
                    new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

    }

    public void AddToCartBtnOnAction(ActionEvent actionEvent) {
        try {
            double amount = productsModel.getProductByCode(ProductIDDragDown.getValue().
                    toString()).getUnitPrice() *
                    Integer.parseInt(QuantityLabel.getText());
            JFXButton btn = new JFXButton("Delete");
            OrderTm tm = new OrderTm(
                    ProductIDDragDown.getValue().toString(),
                    ProductName.getText(),
                    Integer.parseInt(QuantityLabel.getText()),
                    amount,
                    btn
                    );

            btn.setOnAction(actionEvent1 -> {
                tmList.remove(tm);
                total-= tm.getAmount();
                TableView.refresh();
                TotalLabel.setText(String.valueOf(total));
            });
            boolean isExist= false;
            for (OrderTm order:tmList) {
                if (order.getCode().equals(tm.getCode())){
                    order.setQuantity(order.getQuantity()+tm.getQuantity());
                    order.setAmount(order.getAmount()+tm.getAmount());
                    isExist=true;
                    total += tm.getAmount();
                }
            }
            if (!isExist){
                tmList.add(tm);
                total += tm.getAmount();
            }
            TreeItem<OrderTm> treeItem = new RecursiveTreeItem<OrderTm>(tmList, RecursiveTreeObject::getChildren);
            TableView.setRoot(treeItem);
            TableView.setShowRoot(false);
            TotalLabel.setText(String.valueOf(total));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    //===========================================================================================================
    // ===========================================================================================================
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
}
