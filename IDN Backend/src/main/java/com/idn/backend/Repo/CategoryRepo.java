package com.idn.backend.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    List<Category> findAllByActiveTrue();

    Optional<Category> findByIdAndActiveTrue(Long id);

}