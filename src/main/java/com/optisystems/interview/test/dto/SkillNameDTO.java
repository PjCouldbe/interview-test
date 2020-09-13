package com.optisystems.interview.test.dto;

import com.optisystems.interview.test.dto.base.AbstractIdDTO;
import com.optisystems.interview.test.dto.base.Naming;
import com.optisystems.interview.test.model.Skill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SkillNameDTO extends AbstractIdDTO<Skill> implements Naming {

    private String name;

    public SkillNameDTO(UUID id, String name) {
        super(id);
        this.name = name;
    }
}
