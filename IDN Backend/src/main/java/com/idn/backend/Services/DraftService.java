package com.idn.backend.Services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.idn.backend.DTO.DraftRequestDTO;
import com.idn.backend.DTO.DraftResponseDTO;
import com.idn.backend.Mapper.DraftMapper;
import com.idn.backend.Model.Draft;
import com.idn.backend.Repo.DraftRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DraftService {

    private final DraftRepo draftRepo;
    private final DraftMapper draftMapper;

    public DraftResponseDTO saveDraft(DraftRequestDTO draftRequestDTO) {
        Draft savedDraft = draftMapper.toEntityDraft(draftRequestDTO);
        Draft draft = draftRepo.save(savedDraft);
        return draftMapper.draftResponseDTO(draft);
    }

    public List<DraftResponseDTO> findAllDrafts() {

        List<Draft> drafts = draftRepo.findAll();

        return draftMapper.toDraftListResponseDTO(drafts);
    }

    public DraftResponseDTO findDraftById(Long id) {

        Draft draft = draftRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("no draft avlibale with id:" + id));

        return draftMapper.draftResponseDTO(draft);
    }

    public DraftResponseDTO editDraft(Long id, DraftRequestDTO draftRequestDTO) {

        Draft draft = draftRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Do draft found with id: " + id));

        Draft editedDraft = draftMapper.updateDraftFromDto(draftRequestDTO, draft);

        return draftMapper.draftResponseDTO(editedDraft);
    }

    public DraftResponseDTO deleteDraftById(Long id) {

        Draft draft = draftRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("No Draft found by id: " + id));

        draftRepo.deleteById(id);
        return draftMapper.draftResponseDTO(draft);
    }

}
