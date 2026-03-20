package com.idn.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.entity.Media;

public interface MediaRepo extends JpaRepository<Media, Long> {

}
