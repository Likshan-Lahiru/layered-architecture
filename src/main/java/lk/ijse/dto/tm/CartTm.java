package lk.ijse.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartTm {
    private String toolId;
    private String lblDescriptionText;
    private int qty;
    private int rentalDaysText;
    private double rentPerDay;
    private double total;
    private Button btn;
    private String orderId;
    private String orderDate;

    public CartTm(String toolId, String orderId, int qty, double unitprice, String date) {
        this.toolId = toolId;
        this.orderId = orderId;
        this.qty = qty;
        this.rentPerDay = unitprice;
    }
}
