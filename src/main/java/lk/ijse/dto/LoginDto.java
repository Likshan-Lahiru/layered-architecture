package lk.ijse.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class LoginDto {
    private String username;
    private String password;

    public LoginDto(String string, String string1, String string2, String string3, String string4) {
    }
}
