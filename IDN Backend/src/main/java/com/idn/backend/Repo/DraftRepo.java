package com.idn.backend.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.Model.Draft;

public interface DraftRepo extends JpaRepository<Draft, Long> {

}
