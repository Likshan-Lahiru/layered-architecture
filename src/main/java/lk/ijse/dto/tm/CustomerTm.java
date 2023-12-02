package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerTm {
    private String customerId;
    private String customerName;
    private String customerAddress;
    private String customerNIC;
    private String customerNumber;
    private String customerEmail;
}
