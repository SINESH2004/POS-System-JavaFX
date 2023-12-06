package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor@AllArgsConstructor
@Setter
@Getter
public class OrderDto {
    private String orderID;
    private String date;
    private String customerID;
    private List<OrderDetailsDto> list;
}
