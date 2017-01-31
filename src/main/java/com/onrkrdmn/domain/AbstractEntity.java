package com.onrkrdmn.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.util.Date;

/**
 * Created by onur on 22.01.17.
 * abstract implementation of Entity {@link Entity}
 * all persistent object should be extended from AbstractEntity
 */
@Getter
@Setter
@Log4j
public abstract class AbstractEntity implements Entity {

    @Id
    private String id;
    private Date createdDate;
    private Date updatedDate;
    private boolean active;

    @Transient
    public Object clone() {
        Object clonedObject = null;

        try {
            clonedObject = super.clone();
        } catch (CloneNotSupportedException e) {
            //swallow
        }
        return clonedObject;
    }
}
