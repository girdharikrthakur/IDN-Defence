package com.idn.backend.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.Model.Tag;;

public interface TagRepo extends JpaRepository<Tag, Long> {

}