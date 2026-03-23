package com.idn.backend.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.entity.Tag;;

public interface TagRepo extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String tagName);

}