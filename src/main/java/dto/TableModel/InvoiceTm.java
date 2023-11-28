package dto.TableModel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Getter
public class InvoiceTm extends RecursiveTreeObject<InvoiceTm> {
    private int S1;
    private String description;
    private int quantity;
    private double rate;
    private double amount;
    private JFXButton delete;
}
