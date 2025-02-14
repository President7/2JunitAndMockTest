package com.master.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.master.model.Employe;
import com.master.service.EmployService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

@WebMvcTest(EmployeController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployService employService;
    @Autowired
    private ObjectMapper objectMapper;

    Employe employe;

    @BeforeEach
    public void setup() {
        employe = new Employe();
        employe.setEmail("ravi@gmail.com");
        employe.setFirstName("ravi");
        employe.setLastName("Singh");
        employe.setId(1l);
        employService.saveEmployee(employe);
    }

    //Post controller
    @Test
    @Order(1)
    public void saveEmployeTest() throws Exception {
        //precondition
        given(employService.saveEmployee(any(Employe.class))).willReturn(employe);
        //action
        ResultActions response = mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(employe)));
        //verify
        response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.firstName", is(employe.getFirstName()))).andExpect(jsonPath("$.lastName", is(employe.getLastName()))).andExpect(jsonPath("$.email", is(employe.getEmail())));
    }

    @Test
    @Order(2)
    public void getEmployeTest() throws Exception {
        List<Employe> employeList = new ArrayList<>();
        employeList.add(employe);
        employe = new Employe();
        employe.setEmail("ravi@gmail.com");
        employe.setFirstName("ravi");
        employe.setLastName("Singh");
        employe.setId(2l);
        employeList.add(employService.saveEmployee(employe));
        given(employService.getAllEmploye()).willReturn(employeList);
        //Action
        ResultActions response = mockMvc.perform(get("/api/employees"));
//verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(employeList.size())));

    }

    //get by id controller
    @Test
    @Order(3)
    public void getEmployeByIdTest() throws Exception {
        //pre condition
        given(employService.getEmployeById(employe.getId())).willReturn(Optional.of(employe));
        //Action
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employe.getId()));
//verify
        response.andExpect(status().isOk()).andDo(print()).
                andExpect(jsonPath("$.firstName", is(employe.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employe.getLastName())))
                .andExpect(jsonPath("$.email", is(employe.getEmail())));
    }

    @Test
    @Order(4)
    public void updateEmployeByIdTest() throws Exception {
        //precondition
        given(employService.getEmployeById(employe.getId())).willReturn(Optional.of(employe));
        employe.setFirstName("Max");
        employe.setEmail("max@gmail.com");
        given(employService.updateEmploye(employe,employe.getId())).willReturn(employe);

        //action
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employe.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employe)));
        //verify
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employe.getFirstName())))
                .andExpect(jsonPath("$.email", is(employe.getEmail())));
    }

    @Test
    @Order(5)
    public void deleteEmployeTest() throws Exception {
        //Precondition
        willDoNothing().given(employService).deleteEmploye(employe.getId());
        //action
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employe.getId()));
        //verify
        response.andExpect(status().isOk())
                .andDo(print());


    }
}
