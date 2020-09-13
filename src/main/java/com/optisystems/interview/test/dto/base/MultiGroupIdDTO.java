package com.optisystems.interview.test.dto.base;

import com.optisystems.interview.test.model.base.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MultiGroupIdDTO<Entity extends AbstractBaseEntity> extends AbstractIdDTO<Entity> {

    private List<GroupDTO> groups = new ArrayList<>();

}
