package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    private String CustomerId;
    private String CustomerName;
    private String CustomerAddress;
    private String CustomerNic;
    private String CustomerContactNumber;
}
