package com.idn.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.entity.Contact;

public interface ContactRepo extends JpaRepository<Contact, Long> {

}