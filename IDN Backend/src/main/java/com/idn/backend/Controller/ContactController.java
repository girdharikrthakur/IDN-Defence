package com.idn.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idn.backend.dto.ContactDTO;
import com.idn.backend.dto.response.ApiResponse;
import com.idn.backend.dto.response.CursorPageResponse;
import com.idn.backend.service.impl.ContactService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping()
    public ResponseEntity<ApiResponse<ContactDTO>> postMethodName(@RequestBody ContactDTO dto) {

        ContactDTO message = contactService.saveMessage(dto);
        ApiResponse<ContactDTO> response = new ApiResponse<>("contact Saved", message);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping()
    public ResponseEntity<CursorPageResponse<ContactDTO>> getALlMessage(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "10") int limit) {
        CursorPageResponse<ContactDTO> messageList = contactService.getAllMessages(cursor, limit);

        return ResponseEntity.ok().body(messageList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ContactDTO>> getContactMessage(Long id) {

        ContactDTO message = contactService.getContactMessageById(id);
        ApiResponse<ContactDTO> response = new ApiResponse<>("Message Recived", message);
        return ResponseEntity.ok().body(response);
    }

}
