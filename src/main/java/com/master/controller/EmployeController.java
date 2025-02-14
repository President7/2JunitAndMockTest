package com.master.controller;


import com.master.model.Employe;
import com.master.service.EmployService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeController {
    private EmployService employService;

    public EmployeController(EmployService employService) {
        this.employService = employService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employe createEmploye(@RequestBody Employe employe) {
        return employService.saveEmployee(employe);
    }

    @GetMapping
    public List<Employe> getAllEmploye() {
        return employService.getAllEmploye();
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Employe>> getEmployeById(@PathVariable("id") long id) {
        return new ResponseEntity<Optional<Employe>>(employService.getEmployeById(id), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<Employe> updateEmployeById(@PathVariable("id") long id, @RequestBody Employe employe) {
        return new ResponseEntity<Employe>(employService.updateEmploye(employe, id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteEmployeById(@PathVariable("id") long id) {
        employService.deleteEmploye(id);
        return new ResponseEntity<>("Employe Deleted SuccessFully !! ", HttpStatus.OK);
    }

}
