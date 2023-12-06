package dto.TableModel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Getter
public class OrderTm extends RecursiveTreeObject<OrderTm> {
    private String code;
    private String desc;
    private int quantity;
    private double amount;
    private JFXButton btn;
}
