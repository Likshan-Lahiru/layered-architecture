package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AttandanceTm {
    private String employeeId;
    private String employeeName;
    private String date;
    private String NIC;
    private String status;
}
