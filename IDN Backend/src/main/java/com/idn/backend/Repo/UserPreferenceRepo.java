package com.idn.backend.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.idn.backend.Model.UserPreference;

public interface UserPreferenceRepo extends JpaRepository<UserPreference, Long> {

}
