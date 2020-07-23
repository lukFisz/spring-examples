package luk.fisz.springsecurityjwtauthentication.security.jwt;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginViewModel {
    private String username;
    private String password;
}
