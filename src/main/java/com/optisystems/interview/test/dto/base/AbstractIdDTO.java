package com.optisystems.interview.test.dto.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.optisystems.interview.test.model.base.AbstractBaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Data
@ToString(exclude = "entity")
@NoArgsConstructor
public abstract class AbstractIdDTO<T extends AbstractBaseEntity> implements Serializable {

    @SuppressWarnings("DefaultAnnotationParam")
    @ApiModelProperty(
        value = "ID соответствующей сущности в базе данных",
        position = 0,
        example = "'\"4a048087-557d-4184-bc54-dc04e3bf0881\"'"
    )
    private UUID id;

    @JsonIgnore
    private T entity;

    public AbstractIdDTO(UUID id) {
        this.id = id;
    }
}
