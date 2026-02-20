package com.idn.backend.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.Model.Reaction;

public interface ReactionRepo extends JpaRepository<Reaction, Long> {

}
