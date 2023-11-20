package com.aston_intensive.week2.service.impl;

import com.aston_intensive.week2.model.Patient;
import com.aston_intensive.week2.repository.impl.PatientRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceImplTest {

    @Mock
    private PatientRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        List<Patient> patients = Arrays.asList(
                new Patient(1, "Name 1", 10, "address 1"),
                new Patient(2, "Name 2", 20, "address 2")
        );
        when(repository.findAll()).thenReturn(patients);
        assertEquals(patients, repository.findAll());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById() {
        Patient patient = new Patient(1, "Name 1", 10, "address 1");
        when(repository.findById(1)).thenReturn(patient);
        when(repository.findById(2)).thenReturn(null);
        assertEquals(patient, repository.findById(1));
        assertNull(repository.findById(2));
        verify(repository, times(2)).findById(any());

    }

    @Test
    void save() {
        Patient patient = new Patient(1, "Name 1", 10, "address 1");
        Patient forSave = new Patient("Name 1", 10, "address 1");
        when(repository.save(forSave)).thenReturn(patient);
        assertEquals(patient, repository.save(forSave));
        verify(repository, times(1)).save(forSave);
    }

    @Test
    void update() {
        Patient patient = new Patient(1, "Name 1", 10, "address 1");
        Patient forUpdate = new Patient("Name 1", 10, "address 1");
        when(repository.update(1, forUpdate)).thenReturn(patient);
        when(repository.update(2, forUpdate)).thenReturn(null);
        assertEquals(patient, repository.update(1, forUpdate));
        assertNull(repository.update(2, forUpdate));
        verify(repository, times(1)).update(1, forUpdate);
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