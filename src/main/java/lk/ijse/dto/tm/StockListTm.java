package lk.ijse.dto.tm;

import javafx.scene.control.Button;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockListTm {


    private String supplierId;
   // private String supplierName;
    private String toolId;
    private String toolName;
    private int qtyOnHand;
    private int qty;
    private double unitPrice;
    private double total;
    private Button btn;

}
