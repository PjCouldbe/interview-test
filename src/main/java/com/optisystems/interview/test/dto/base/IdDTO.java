package com.optisystems.interview.test.dto.base;

import com.optisystems.interview.test.model.base.AbstractBaseEntity;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by bigblackbug on 6/16/2016.
 */
public class IdDTO<T extends AbstractBaseEntity> extends AbstractIdDTO<T> implements Serializable {

    public IdDTO() {

    }

    public IdDTO(UUID id) {
        setId(id);
    }
}
