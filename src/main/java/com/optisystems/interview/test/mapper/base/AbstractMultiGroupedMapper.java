package com.optisystems.interview.test.mapper.base;

import com.optisystems.interview.test.dto.base.GroupDTO;
import com.optisystems.interview.test.dto.base.IdDTO;
import com.optisystems.interview.test.dto.base.MultiGroupIdDTO;
import com.optisystems.interview.test.facade.PermissionGroupFacade;
import com.optisystems.interview.test.model.PermissionGroup;
import com.optisystems.interview.test.model.base.AbstractMultiGroupedEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Supplier;

public abstract class AbstractMultiGroupedMapper<DTO extends MultiGroupIdDTO<Entity>, Entity extends AbstractMultiGroupedEntity>
    implements CrudMapper<DTO, Entity> {

    protected Supplier<DTO> dtoConstructor;
    protected Supplier<Entity> entityConstructor;

    @Autowired
    protected PermissionGroupFacade permissionGroupFacade;

    @Override
    public DTO toDto(Entity entity) {
        DTO dto = dtoConstructor.get();

        dto.setId(entity.getId());

        for (PermissionGroup group : entity.getGroups()) {
            dto.getGroups().add(new GroupDTO(
                group.getId(),
                group.getExternalId(),
                group.getName()
            ));
        }

        return dto;
    }

    @Override
    public Entity toEntity(DTO dto) {
        Entity entity = entityConstructor.get();

        entity.setId(dto.getId());

        for (GroupDTO groupDTO : dto.getGroups()) {
            if (groupDTO != null && groupDTO.getId() != null) {
                PermissionGroup group = permissionGroupFacade.find(groupDTO.getId());
                entity.getGroups().add(group);
            }
        }

        return entity;
    }

    @Override
    public IdDTO<?> toId(Entity entity) {
        return new IdDTO<>(entity.getId());
    }
}
