package com.idn.backend.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.Model.Media;

public interface MediaRepo extends JpaRepository<Media, Long> {

}
