package dto.TableModel;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class ProductsTm extends RecursiveTreeObject<ProductsTm> {
    private String code;
    private String description;
    private double unitPrice;
    private int quantity;
    private JFXButton delete;


}
