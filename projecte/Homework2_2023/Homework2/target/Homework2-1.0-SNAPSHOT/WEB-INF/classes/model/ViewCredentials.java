package deim.urv.cat.homework2.model;

import jakarta.mvc.binding.MvcBinding;
import java.io.Serializable;

public class ViewCredentials implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    @MvcBinding
    private String username;
    @MvcBinding
    private String password;

    public ViewCredentials() {}

    public ViewCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
