package org.example.user;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Credentials {

    private String email;
    private String password;

    public Credentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static Credentials from (User user) {
        return new Credentials(user.getEmail(), user.getPassword());
    }
}
