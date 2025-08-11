package com.idn.backend.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.Model.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {

}