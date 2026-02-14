package com.idn.backend.Mapper;

import java.util.List;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.idn.backend.DTO.DraftRequestDTO;
import com.idn.backend.DTO.DraftResponseDTO;
import com.idn.backend.Model.Draft;

public interface DraftMapper extends BaseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "published", ignore = true)
    @Mapping(target = "user", ignore = true)
    Draft toEntityDraft(DraftRequestDTO dto);

    DraftResponseDTO draftResponseDTO(Draft draft);

    List<DraftResponseDTO> toDraftListResponseDTO(List<Draft> drafts);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "published", ignore = true)
    @Mapping(target = "user", ignore = true)
    Draft updateDraftFromDto(DraftRequestDTO dto, @MappingTarget Draft draft);
}
