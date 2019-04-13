<<<<<<< HEAD:src/main/java/com/sendfriend/domain/forms/LoginForm.java
package com.sendfriend.domain.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class LoginForm {

    @NotNull
    @Pattern(regexp = "[a-zA-Z][a-zA-Z0-9_-]{3,11}", message = "Usernames must be between 4 and 12 characters, start with a letter, and contain only letters and numbers")
    private String username;

    @NotNull
    @Pattern(regexp = "(\\S){4,20}", message = "Password must have 4-20 non-whitespace characters")
    private String password;

    public LoginForm() {}

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
=======
package com.sendfriend.models.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class LoginForm {

    @NotNull
    @Pattern(regexp = "[a-zA-Z][a-zA-Z0-9_-]{3,11}", message = "Usernames must be between 4 and 12 characters, start with a letter, and contain only letters and numbers")
    private String username;

    @NotNull
    @Pattern(regexp = "(\\S){4,20}", message = "Password must have 4-20 non-whitespace characters")
    private String password;

    public LoginForm() {}

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
>>>>>>> develop:src/main/java/com/sendfriend/models/forms/LoginForm.java
