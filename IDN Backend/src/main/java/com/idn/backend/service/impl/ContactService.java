package com.idn.backend.service.impl;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idn.backend.dto.ContactDTO;
import com.idn.backend.dto.response.CursorPageResponse;
import com.idn.backend.entity.Contact;
import com.idn.backend.mapper.ContactMapper;
import com.idn.backend.repo.ContactRepo;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepo contactRepo;
    private final ContactMapper contactMapper;

    @Transactional
    public ContactDTO saveMessage(ContactDTO dto) {

        Contact message = contactMapper.toEntity(dto);
        Contact savedmessage = contactRepo.save(message);
        return contactMapper.toContactDTO(savedmessage);

    }

    public CursorPageResponse<ContactDTO> getAllMessages(Long cursor, int limit) {

        Pageable pageable = PageRequest.of(0, limit + 1);

        List<Contact> messageList;

        if (cursor == null) {
            messageList = contactRepo.findAllByOrderByIdDesc(pageable);
        } else {
            messageList = contactRepo.findByIdLessThanOrderByIdDesc(cursor, pageable);
        }
        Boolean hasMore = messageList.size() > limit;
        if (hasMore) {
            messageList = messageList.subList(0, limit);
        }

        List<ContactDTO> contactResponseList = contactMapper.toContactDtoList(messageList);

        Long nextCursor = messageList.isEmpty()
                ? null
                : messageList.get(messageList.size() - 1).getId();

        return new CursorPageResponse<>(contactResponseList, nextCursor, hasMore);
    }

    public ContactDTO getContactMessageById(Long id) {
        Contact contact = contactRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("message not found by:" + id));
        return contactMapper.toContactDTO(contact);

    }

}