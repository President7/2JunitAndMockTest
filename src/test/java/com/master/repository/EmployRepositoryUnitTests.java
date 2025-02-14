package com.master.repository;

import com.master.model.Employe;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase
public class EmployRepositoryUnitTests {

    @Autowired
    private EmployRepository employRepository;

    @Test
    @DisplayName("1, save emplye test")
    @Order(1)
    @Rollback(value = false)
    public void saveEmployeTest() {
        //Action
      /*  Employe employe = Employe.builder()
                .firstName("Sam")
                .lastName("Curran")
                .email("sam@gmail.com")
                .build();
        employRepository.save(employe);*/


        Employe employe =new Employe();
        employe.setFirstName("Sam");
        employe.setLastName("Curran");
        employe.setEmail("sam@gmail.com");
        employRepository.save(employe);
        //verify
        System.out.println("employe : " + employe);
        Assertions.assertThat(employe.getId()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    public void getEmployeTest() {
        //Action
        Employe employe = employRepository.findById(1L).get();
        //verify
        System.out.println("employe : " + employe);
        Assertions.assertThat(employe.getId()).isEqualTo(1l);
    }

    @Test
    @Order(3)
    public void getEmployeListTest() {
        List<Employe> employes = employRepository.findAll();
        System.out.println("employes : " + employes);
        Assertions.assertThat(employes.size()).isGreaterThan(0);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateEmployeTest() {
        //action
        Employe employe = employRepository.findById(1l).get();
        employe.setEmail("ram@email.com");
        Employe updatedEmploye = employRepository.save(employe);
        //verify
        System.out.println("updatedEmploye : " + updatedEmploye);
        Assertions.assertThat(updatedEmploye.getEmail()).isEqualTo("ram@email.com");
    }

    @Test
    @Order(5)
    public void deleteEmployeTest() {
        employRepository.deleteById(1l);
        Optional<Employe> byId = employRepository.findById(1l);
        //verify
        Assertions.assertThat(byId).isEmpty();
    }

}
