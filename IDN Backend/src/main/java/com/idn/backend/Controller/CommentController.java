package com.idn.backend.Controller;

import com.idn.backend.DTO.CommentRequestDTO;
import com.idn.backend.DTO.CommentResponseDTO;
import com.idn.backend.Services.CommentsService;

import lombok.AllArgsConstructor;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("private/api/v1/comment")
public class CommentController {

    private final CommentsService commentsService;

    @PostMapping()
    public ResponseEntity<CommentResponseDTO> saveComment(@RequestBody CommentRequestDTO dto) {

        CommentResponseDTO response = commentsService.saveComment(dto);
        return ResponseEntity.ok(response);

    }

    @GetMapping()
    public ResponseEntity<List<CommentResponseDTO>> getComments() {
        List<CommentResponseDTO> reponse = commentsService.getCommnets();
        return ResponseEntity.ok(reponse);
    }

    @GetMapping("/id")
    public ResponseEntity<CommentResponseDTO> getComments(@RequestParam Long id) {
        CommentResponseDTO reponse = commentsService.getCommentsById(id);
        return ResponseEntity.ok(reponse);
    }

    @DeleteMapping
    public ResponseEntity<CommentResponseDTO> deleteComment(@RequestParam Long id) {
        CommentResponseDTO deletedComment = commentsService.deleteComment(id);
        return ResponseEntity.ok(deletedComment);
    }

    @PutMapping
    public ResponseEntity<CommentResponseDTO> updatedComment(@RequestParam Long id, CommentRequestDTO comment,
            Principal principal) {

        CommentResponseDTO updatedCommnet = commentsService.editComment(id, comment, principal);

        return ResponseEntity.ok(updatedCommnet);

    }

    @GetMapping("/{id}/replies")
    public ResponseEntity<List<CommentResponseDTO>> getReplies(@PathVariable Long id) {
        List<CommentResponseDTO> replies = commentsService.getReplies(id);
        return ResponseEntity.ok(replies);
    }

}
