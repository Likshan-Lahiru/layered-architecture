package lk.ijse.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AttandanceDto {

    private String employeeId;
    private String employeeName;
    private String date;
    private String NIC;
    private String status;



}
