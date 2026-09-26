package com.example.student.service;

import com.example.student.entity.Student;
import com.example.student.exception.StudentNotFoundException;
import com.example.student.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student(1L, "Sathwik", "sathwik@gmail.com", "CSE", 21);
    }

    @Test
    void addStudent_ShouldReturnSavedStudent() {
        when(studentRepository.save(student)).thenReturn(student);

        Student savedStudent = studentService.addStudent(student);

        assertNotNull(savedStudent);
        assertEquals("Sathwik", savedStudent.getName());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void getAllStudents_ShouldReturnStudentList() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student));

        List<Student> students = studentService.getAllStudents();

        assertEquals(1, students.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void getStudentById_WhenStudentExists_ShouldReturnStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        Student foundStudent = studentService.getStudentById(1L);

        assertNotNull(foundStudent);
        assertEquals(1L, foundStudent.getId());
    }

    @Test
    void getStudentById_WhenStudentDoesNotExist_ShouldThrowException() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.getStudentById(1L));
    }

    @Test
    void updateStudent_ShouldReturnUpdatedStudent() {
        Student updatedDetails = new Student(1L, "Sathwik Updated", "updated@gmail.com", "IT", 22);
        
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedDetails);

        Student result = studentService.updateStudent(1L, updatedDetails);

        assertEquals("Sathwik Updated", result.getName());
        assertEquals("IT", result.getDepartment());
    }

    @Test
    void deleteStudent_ShouldCallRepositoryDelete() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        doNothing().when(studentRepository).delete(student);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).delete(student);
    }
}
