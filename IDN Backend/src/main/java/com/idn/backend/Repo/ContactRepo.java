package com.idn.backend.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.entity.Contact;

public interface ContactRepo extends JpaRepository<Contact, Long> {

    // First page (latest records)
    List<Contact> findAllByOrderByIdDesc(Pageable pageable);

    // Next pages using cursor
    List<Contact> findByIdLessThanOrderByIdDesc(Long cursor, Pageable pageable);

}