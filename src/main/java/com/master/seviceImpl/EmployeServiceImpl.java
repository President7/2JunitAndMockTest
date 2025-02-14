package com.master.seviceImpl;

import com.master.model.Employe;
import com.master.repository.EmployRepository;
import com.master.service.EmployService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeServiceImpl implements EmployService {
    private EmployRepository employRepository;

    EmployeServiceImpl(EmployRepository employRepository) {
        this.employRepository = employRepository;
    }

    @Override
    public Employe saveEmployee(Employe employe) {
        Optional<Employe> byEmail = employRepository.findByEmail(employe.getEmail());
        if (byEmail.isPresent()) {
            throw new RuntimeException("Employee Already present with given email !! " + employe.getEmail());
        }
        return employRepository.save(employe);
    }

    @Override
    public List<Employe> getAllEmploye() {
        return employRepository.findAll();
    }

    @Override
    public Optional<Employe> getEmployeById(long id) {
        return employRepository.findById(id);
    }

    @Override
    public Employe updateEmploye(Employe employe, long id) {
        Employe existingEmploye = employRepository.findById(id).orElseThrow(() -> new RuntimeException());
        existingEmploye.setFirstName(employe.getFirstName());
        existingEmploye.setLastName(employe.getLastName());
        existingEmploye.setEmail(employe.getEmail());
        employRepository.save(existingEmploye);
        return existingEmploye;
    }

    @Override
    public void deleteEmploye(long id) {
        employRepository.deleteById(id);
    }
}
