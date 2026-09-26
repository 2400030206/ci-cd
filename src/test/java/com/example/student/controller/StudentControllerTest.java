package com.example.student.controller;

import com.example.student.entity.Student;
import com.example.student.exception.StudentNotFoundException;
import com.example.student.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student(1L, "Sathwik", "sathwik@gmail.com", "CSE", 21);
    }

    @Test
    void addStudent_ShouldReturnCreatedStudent() throws Exception {
        when(studentService.addStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Sathwik"))
                .andExpect(jsonPath("$.email").value("sathwik@gmail.com"));
    }

    @Test
    void addStudent_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        Student invalidStudent = new Student(null, "", "invalid-email", "", 0);

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidStudent)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllStudents_ShouldReturnStudentList() throws Exception {
        when(studentService.getAllStudents()).thenReturn(Arrays.asList(student));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Sathwik"));
    }

    @Test
    void getStudentById_ShouldReturnStudent() throws Exception {
        when(studentService.getStudentById(1L)).thenReturn(student);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sathwik"));
    }

    @Test
    void getStudentById_WhenNotFound_ShouldReturn404() throws Exception {
        when(studentService.getStudentById(1L)).thenThrow(new StudentNotFoundException("Student not found"));

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Student not found"));
    }

    @Test
    void updateStudent_ShouldReturnUpdatedStudent() throws Exception {
        Student updatedStudent = new Student(1L, "Sathwik Updated", "updated@gmail.com", "IT", 22);
        
        when(studentService.updateStudent(eq(1L), any(Student.class))).thenReturn(updatedStudent);

        mockMvc.perform(put("/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sathwik Updated"));
    }

    @Test
    void deleteStudent_ShouldReturnNoContent() throws Exception {
        doNothing().when(studentService).deleteStudent(1L);

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isNoContent());
    }
}
