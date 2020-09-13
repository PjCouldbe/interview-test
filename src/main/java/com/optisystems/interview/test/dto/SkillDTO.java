package com.optisystems.interview.test.dto;

import com.optisystems.interview.test.dto.base.MultiGroupIdDTO;
import com.optisystems.interview.test.model.Skill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SkillDTO extends MultiGroupIdDTO<Skill> implements Serializable {

    private String name;
    private String externalId;

}
