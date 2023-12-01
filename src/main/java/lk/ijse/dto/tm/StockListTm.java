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
    private String supplierName;
    private String toolId;
    private String toolName;
    private String orderDate;
    private int qtyOnHand;
    private int qty;
    private double unitPrice;
    private double total;
    private Button btn;
    private String lastUpdatedDate;
    private String wasteCount;

    public StockListTm(String toolId, String toolName, int qtyOnhand) {
        this.toolId = toolId;
        this.toolName = toolName;
        this.qtyOnHand = qtyOnhand;
    }

}
