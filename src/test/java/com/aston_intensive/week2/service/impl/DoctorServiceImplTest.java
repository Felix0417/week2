package com.aston_intensive.week2.service.impl;

import com.aston_intensive.week2.model.Doctor;
import com.aston_intensive.week2.model.Hospital;
import com.aston_intensive.week2.repository.impl.DoctorRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class DoctorServiceImplTest {

    @Mock
    DoctorRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        List<Doctor> expectedDoctors = Arrays.asList(
                new Doctor(1, "Smith", 50000, new Hospital()),
                new Doctor(2, "Brown", 60000, new Hospital())
        );
        when(repository.findAll()).thenReturn(expectedDoctors);
        List<Doctor> actualDoctors = repository.findAll();

        assertEquals(expectedDoctors, actualDoctors);
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById() {
        Doctor doctor = new Doctor(1, "Smith", 50000, new Hospital());
        when(repository.findById(1)).thenReturn(doctor);
        Doctor actual = repository.findById(1);
        assertEquals(doctor, actual);
        verify(repository, times(1)).findById(1);
    }

    @Test
    void save() {
        Doctor doctor = new Doctor(1, "Smith", 50000, new Hospital());
        Doctor forSave = new Doctor("Smith", 50000, new Hospital());
        when(repository.save(forSave)).thenReturn(doctor);
        assertEquals(doctor, repository.save(forSave));
        verify(repository, times(1)).save(forSave);
    }

    @Test
    void update() {
        Doctor doctor = new Doctor(1, "Smith", 50000, new Hospital());
        Doctor forUpdate = new Doctor("Smith", 50000, new Hospital());
        when(repository.update(1, forUpdate)).thenReturn(doctor);
        when(repository.update(2, forUpdate)).thenReturn(null);
        assertEquals(doctor, repository.update(1, forUpdate));
        assertNull(repository.update(2, forUpdate));
        verify(repository, times(1)).update(2, forUpdate);
    }

    @Test
    void delete() {
        when(repository.deleteById(1)).thenReturn(true);
        when(repository.deleteById(2)).thenReturn(false);
        assertTrue(repository.deleteById(1));
        assertFalse(repository.deleteById(2));
        verify(repository, times(2)).deleteById(any());
    }
}