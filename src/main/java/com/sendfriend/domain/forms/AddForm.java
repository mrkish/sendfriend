<<<<<<< HEAD:src/main/java/com/sendfriend/domain/forms/AddForm.java
package com.sendfriend.domain.forms;

import javax.validation.constraints.NotNull;

public class AddForm {

    @NotNull
    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
=======
package com.sendfriend.models.forms;

import javax.validation.constraints.NotNull;

public class AddForm {

    @NotNull
    private String name;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
>>>>>>> develop:src/main/java/com/sendfriend/models/forms/AddForm.java
