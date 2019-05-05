package com.sendfriend.models;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    public int getId() {
        return this.id;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
