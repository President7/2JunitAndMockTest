package com.master.repository;

import com.master.model.Employe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployRepository extends JpaRepository<Employe, Long> {

    Optional<Employe> findByEmail(String email);
}
