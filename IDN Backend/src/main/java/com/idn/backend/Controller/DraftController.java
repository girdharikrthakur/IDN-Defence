package com.idn.backend.Controller;

import com.idn.backend.DTO.DraftRequestDTO;
import com.idn.backend.DTO.DraftResponseDTO;
import com.idn.backend.Services.DraftService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/private/api/v1/draft")
public class DraftController {

    private final DraftService draftService;

    @PostMapping()
    public ResponseEntity<DraftResponseDTO> addDraft(@RequestBody DraftRequestDTO draftRequestDTO) {

        DraftResponseDTO draft = draftService.saveDraft(draftRequestDTO);

        return ResponseEntity.ok(draft);
    }

    @GetMapping()
    public ResponseEntity<List<DraftResponseDTO>> getAllDrafts() {
        List<DraftResponseDTO> drafts = draftService.findAllDrafts();
        return ResponseEntity.ok(drafts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DraftResponseDTO> getDraftWithId(@PathVariable Long id) {
        DraftResponseDTO draft = draftService.findDraftById(id);
        return ResponseEntity.ok(draft);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DraftResponseDTO> editDraft(@PathVariable Long id,
            @RequestBody DraftRequestDTO draftRequestDTO) {

        DraftResponseDTO editedDraft = draftService.editDraft(id, draftRequestDTO);

        return ResponseEntity.ok(editedDraft);
    }

    @DeleteMapping
    public ResponseEntity<DraftResponseDTO> deleteDraftById(@PathVariable Long id) {
        DraftResponseDTO deletedDraft = draftService.deleteDraftById(id);
        return ResponseEntity.ok(deletedDraft);
    }

}
