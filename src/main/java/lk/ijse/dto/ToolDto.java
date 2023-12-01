package lk.ijse.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class ToolDto {
    private String toolId;
    private String toolName;
    private int qtyOnhand;
    private  double rentPerDay;


    public ToolDto(String searchIdText) {
        this.toolId = searchIdText;
    }



}
