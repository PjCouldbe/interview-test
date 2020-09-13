package com.optisystems.interview.test.mapper.base;

import com.optisystems.interview.test.dto.base.IdDTO;

/**
 * @author Sapiosexual
 */
public interface CrudMapper<DTO, Entity> extends Mapper<DTO, Entity> {
    IdDTO toId(Entity entity);
}
