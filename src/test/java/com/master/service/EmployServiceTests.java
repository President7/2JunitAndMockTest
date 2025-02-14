package com.master.service;


import com.master.model.Employe;
import com.master.repository.EmployRepository;
import com.master.seviceImpl.EmployeServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

//This class is implementation for Junit and mock
@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployServiceTests {

    @Mock
    private EmployRepository employRepository;
    @InjectMocks
    private EmployeServiceImpl employeServiceImpl;
    private Employe employe;

    @BeforeEach
    public void setup() {
        employe = new Employe();
        employe.setId(1l);
        employe.setFirstName("Ravi");
        employe.setLastName("Singh");
        employe.setEmail("test@gmail.com");
        employRepository.save(employe);

    }

    @Test
    @Order(1)
    public void saveEmployeTest() {
        //peconditoin
        given(employRepository.save(employe)).willReturn(employe);
        //action
        Employe saveEmploye = employeServiceImpl.saveEmployee(employe);
        //verify output
        System.out.println("saveEmploye : " + saveEmploye);
        assertThat(saveEmploye).isNotNull();
    }

    @Test
    @Order(2)
    public void getEmployeById() {
        // precondition
        given(employRepository.findById(1l)).willReturn(Optional.of(employe));
        //action
        Employe existingEmploye = employeServiceImpl.getEmployeById(employe.getId()).get();
        // verify
        System.out.println("existingEmploye : " + existingEmploye);
        assertThat(existingEmploye).isNotNull();
    }

    @Test
    @Order(3)
    public void getAllEmploye() {
        Employe employe1 = new Employe();
        employe1.setId(2l);
        employe1.setFirstName("Shri Shari");
        employe1.setLastName("Last Name");
        employe1.setEmail("t1137ravi@gmailc.om");
        employRepository.save(employe1);
        //pre
        given(employRepository.findAll()).willReturn(List.of(employe, employe1));
        //action
        List<Employe> allEmploye = employeServiceImpl.getAllEmploye();
        //verify
        System.out.println("allEmploye : " + allEmploye);
        assertThat(allEmploye).isNotNull();
        assertThat(allEmploye.size()).isGreaterThan(1);
    }

    @Test
    @Order(4)
    public void getUpdateEmployeTest() {
//precondition
        given(employRepository.findById(employe.getId())).willReturn(Optional.of(employe));
        employe.setEmail("billion@gmail.com");
        employe.setFirstName("billion");
        given(employRepository.save(employe)).willReturn(employe);
        Employe employeUpdated = employeServiceImpl.updateEmploye(employe, employe.getId());
        //verify
        System.out.println("employeUpdated : " + employeUpdated);
        assertThat(employeUpdated.getEmail()).isEqualTo("billion@gmail.com");
        assertThat(employeUpdated.getFirstName()).isEqualTo("billion");

    }

    @Test
    @Order(5)
    public void deleteEmployeByIdTest() {
        willDoNothing().given(employRepository).deleteById(employe.getId());
        employeServiceImpl.deleteEmploye(employe.getId());
        //verify
        verify(employRepository, times(1)).deleteById(employe.getId());
    }
}
