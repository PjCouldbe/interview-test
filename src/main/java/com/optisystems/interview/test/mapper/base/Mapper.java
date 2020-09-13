package com.optisystems.interview.test.mapper.base;

public interface Mapper<DTO, Entity> {
    DTO toDto(Entity entity);

    Entity toEntity(DTO dto);

}
