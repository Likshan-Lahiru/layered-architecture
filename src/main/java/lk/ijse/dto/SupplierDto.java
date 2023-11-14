package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDto {
    private String supplierId;
    private String supplierName;
    private String supplierNIC;
    private String supplierAddress;
    private String supplierContactNumber;
}
