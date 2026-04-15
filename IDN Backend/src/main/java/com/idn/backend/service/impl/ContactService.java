package com.idn.backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.idn.backend.dto.ContactDTO;
import com.idn.backend.entity.Contact;
import com.idn.backend.mapper.ContactMapper;
import com.idn.backend.repo.ContactRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepo contactRepo;
    private final ContactMapper contactMapper;

    public ContactDTO saveMessage(ContactDTO dto) {

        Contact message = contactMapper.toEntity(dto);
        Contact savedmessage = contactRepo.save(message);
        return contactMapper.toContactDTO(savedmessage);

    }

    public ContactDTO getContactMessageById(Long id) {
        Contact contact = contactRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("message not found by:" + id));
        return contactMapper.toContactDTO(contact);

    }

    public List<ContactDTO> getAllMessages() {
        List<Contact> messageList = contactRepo.findAll();
        return contactMapper.toContactDtoList(messageList);
    }

}