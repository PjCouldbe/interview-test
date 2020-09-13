package com.optisystems.interview.test.mapper;

import com.optisystems.interview.test.dto.SkillDTO;
import com.optisystems.interview.test.dto.SkillNameDTO;
import com.optisystems.interview.test.mapper.base.AbstractMultiGroupedMapper;
import com.optisystems.interview.test.model.Skill;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillMapper extends AbstractMultiGroupedMapper<SkillDTO, Skill> {

    {
        dtoConstructor = SkillDTO::new;
        entityConstructor = Skill::new;
    }

    @Override
    public SkillDTO toDto(Skill entity) {
        SkillDTO dto = super.toDto(entity);

        dto.setExternalId(entity.getExternalId());
        dto.setName(entity.getName());

        return dto;
    }

    @Override
    public Skill toEntity(SkillDTO dto) {
        Skill skill = super.toEntity(dto);

        skill.setExternalId(dto.getExternalId());
        skill.setName(dto.getName());

        return skill;
    }
    
    public SkillNameDTO toNameDTO(Skill skill) {
        return new SkillNameDTO(
            skill.getId(),
            skill.getName()
        );
    }
    
}