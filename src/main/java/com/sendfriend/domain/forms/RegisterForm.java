<<<<<<< HEAD:src/main/java/com/sendfriend/domain/forms/RegisterForm.java
package com.sendfriend.domain.forms;

import javax.validation.constraints.NotNull;

public class RegisterForm extends LoginForm {

    @NotNull(message = "Passwords do not match")
    private String verifyPassword;

    @NotNull
    private String email;

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
        checkPasswordForRegistration();
    }

    public String getVerifyPassword() {
        return  verifyPassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() { return email; }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
        checkPasswordForRegistration();
    }

    private void checkPasswordForRegistration() {
        if (!getPassword().equals(verifyPassword)) {
            verifyPassword = null;
        }
    }
}
=======
package com.sendfriend.models.forms;

import javax.validation.constraints.NotNull;

public class RegisterForm extends LoginForm {

    @NotNull(message = "Passwords do not match")
    private String verifyPassword;

    @NotNull
    private String email;

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
        checkPasswordForRegistration();
    }

    public String getVerifyPassword() {
        return  verifyPassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() { return email; }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
        checkPasswordForRegistration();
    }

    private void checkPasswordForRegistration() {
        if (!getPassword().equals(verifyPassword)) {
            verifyPassword = null;
        }
    }
}
>>>>>>> develop:src/main/java/com/sendfriend/models/forms/RegisterForm.java
