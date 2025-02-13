package deim.urv.cat.homework2.controller;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.FormParam;
import java.io.Serializable;

@Named("credentials")
@RequestScoped
public class Credentials implements Serializable {
    private static final long serialVersionUID = 1L;

    // JSR 303 validation for `username`
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    @FormParam("username")
    private String username;

    // JSR 303 validation for `password`
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    @FormParam("password")
    private String password;

    public Credentials() {}

    // Constructor opcional con parámetros
    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return fixNull(this.username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return fixNull(this.password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String fixNull(String in) {
        return (in == null) ? "" : in;
    }
}
