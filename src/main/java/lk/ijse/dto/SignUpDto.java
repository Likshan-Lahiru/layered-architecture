package lk.ijse.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode

public class SignUpDto {
    private String firstName;
    private String ScondName;
    private String userName;
    private String passsword;
    private  String email;
}
