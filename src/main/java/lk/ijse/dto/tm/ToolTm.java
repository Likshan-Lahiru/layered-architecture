package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToolTm {
    private String toolId;
    private String toolName;
    private int qtyOnHand;
    private double rentPerDay;


}
