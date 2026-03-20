package com.idn.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.entity.Tag;;

public interface TagRepo extends JpaRepository<Tag, Long> {

}