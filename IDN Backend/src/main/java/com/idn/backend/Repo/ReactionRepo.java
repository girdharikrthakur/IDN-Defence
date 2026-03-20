package com.idn.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.entity.Reaction;

public interface ReactionRepo extends JpaRepository<Reaction, Long> {

}
