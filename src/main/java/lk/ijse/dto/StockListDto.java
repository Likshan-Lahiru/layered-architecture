package lk.ijse.dto;

import lk.ijse.dto.tm.StockListTm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StockListDto {
    private List<StockListTm> stockListTms = new ArrayList<>();
}
