package com.onrkrdmn.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by onur on 22.01.17.
 * persistent data interface
 */
public interface Entity extends Serializable, Cloneable{

    /**
     * the unique id for persistent object
     *
     * @return
     */
    public String getId();

    /**
     * created date of persistent object
     *
     * @return
     */
    public Date getCreatedDate();

    /**
     * updated date of persistent object
     *
     * @return
     */
    public Date getUpdatedDate();

    /**
     * active flag of persistent object.
     * Nothing will be deleted from database.
     * For now, only active flag is set to false instead of deleting permanently.
     *
     * @return
     */
    public boolean isActive();

    /**
     * active flage should be set as 0 if record needs to be removed
     *
     * @return
     */
    public void setActive(boolean active);
}
