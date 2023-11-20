package dto.TableModel;

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CustomerTm {
    private String id;
    private String name;
    private String address;
    private double salary;
    private Button btn;

}
