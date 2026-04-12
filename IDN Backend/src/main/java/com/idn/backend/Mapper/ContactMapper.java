package com.idn.backend.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.idn.backend.dto.ContactDTO;
import com.idn.backend.entity.Contact;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    @Mapping(target = "id", ignore = true)
    Contact toEntity(ContactDTO contactDTO);

    ContactDTO toContactDTO(Contact comment);

    List<ContactDTO> toContactDtoList(List<Contact> contacts);

}
