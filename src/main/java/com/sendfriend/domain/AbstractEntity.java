<<<<<<< HEAD:src/main/java/com/sendfriend/domain/AbstractEntity.java
package com.sendfriend.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue
    private int id;

    public int getId() {
        return this.id;
    }
}
=======
package com.sendfriend.models;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue
    private int id;

    public int getId() {
        return this.id;
    }
}
>>>>>>> develop:src/main/java/com/sendfriend/models/AbstractEntity.java
