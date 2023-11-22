package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsDto {
    private String toolId;
    private String orderId;
    private String qty;
    private String unitprice;
    private String date;
    private String status;

    public OrderDetailsDto(String orderId, String toolId, int qty) {
        this.orderId = orderId;
        this.toolId = toolId;
        this.qty = String.valueOf(qty);
    }
}
