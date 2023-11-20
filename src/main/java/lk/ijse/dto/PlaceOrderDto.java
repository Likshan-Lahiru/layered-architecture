package lk.ijse.dto;

import lk.ijse.dto.tm.CartTm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PlaceOrderDto {
    private String orderId;
    private String customerId;
    private String orderDate;
    private List<CartTm> cartTms;
    private String name;




}
