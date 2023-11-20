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
    private double qty;
    private double rentalDaysText;
    private double rentPerDay;
    private double total;
    private Button btn;

}
