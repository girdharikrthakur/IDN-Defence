package com.idn.backend.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.idn.backend.Model.Author;;

public interface AuthorRepo extends JpaRepository<Author, Long> {

}
