package dto;

import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter@ToString
public class ProductsDto {
    private String code;
    private String description;
    private double unitPrice;
    private int quantity;
}
