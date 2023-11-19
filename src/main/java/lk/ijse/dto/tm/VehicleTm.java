package lk.ijse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTm {
    private String vehicleId;
    private String vehicleStatus;
    private String lastServiceDate;
    private String numberPlateNo;
}
