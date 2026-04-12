package com.idn.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.protobuf.Api;
import com.idn.backend.dto.ContactDTO;
import com.idn.backend.dto.response.ApiResponse;
import com.idn.backend.services.impl.ContactService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ContactDTO>> getContactMessage(Long id) {

        ContactDTO message = contactService.getContactMessageById(id);
        ApiResponse<ContactDTO> response = new ApiResponse<>("Message Recived", message);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<ContactDTO>>> getALlMessage() {
        List<ContactDTO> messageList = contactService.getAllMessages();
        ApiResponse<List<ContactDTO>> responseList = new ApiResponse<>("All Messages", messageList);
        return ResponseEntity.ok().body(responseList);
    }

}
