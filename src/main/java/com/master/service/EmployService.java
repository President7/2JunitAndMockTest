package com.master.service;

import com.master.model.Employe;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface EmployService {

    Employe saveEmployee(Employe employe);
    List<Employe> getAllEmploye();
    Optional<Employe> getEmployeById(long id);
    Employe updateEmploye(Employe employe, long id);
    void deleteEmploye(long id);
}
