package dto;

import lombok.*;

@AllArgsConstructor@NoArgsConstructor@Setter
@Getter
@ToString
public class OrderDetailsDto {
    private String orderID;
    private String productID;
    private int quantity;
    private double unitPrice;
}
