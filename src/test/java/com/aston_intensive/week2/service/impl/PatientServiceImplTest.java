package com.aston_intensive.week2.service.impl;

import com.aston_intensive.week2.model.Patient;
import com.aston_intensive.week2.repository.impl.PatientRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceImplTest {

    @InjectMocks
    private PatientServiceImpl service;

    @Mock
    private PatientRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void service_findAll() {
        List<Patient> patients = Arrays.asList(
                new Patient(1, "Name 1", 10, "address 1"),
                new Patient(2, "Name 2", 20, "address 2")
        );

        when(service.findAll()).thenReturn(patients);

        assertEquals(patients, service.findAll());
    }

    @Test
    void service_findById() {
        Patient patient = new Patient(1, "Name 1", 10, "address 1");

        when(service.findById(1)).thenReturn(patient);
        when(service.findById(2)).thenReturn(null);

        assertEquals(patient, service.findById(1));
        assertNull(service.findById(2));
    }

    @Test
    void service_save() {
        Patient patient = new Patient(1, "Name 1", 10, "address 1");
        Patient savedPatient = new Patient("Name 1", 10, "address 1");

        when(service.save(patient)).thenReturn(savedPatient);
        when(service.save(null)).thenReturn(null);

        assertEquals(savedPatient, service.save(patient));
        assertNull(service.save(null));
    }

    @Test
    void service_update() {
        Patient patient = new Patient(1, "Name 1", 10, "address 1");
        Patient updatedPatient = new Patient("Name 1", 10, "address 1");

        when(service.findById(1)).thenReturn(patient);
        when(service.findById(2)).thenReturn(null);
        when(service.findById(2)).thenReturn(patient);
        when(service.update(1, patient)).thenReturn(updatedPatient);
        when(service.update(3, null)).thenReturn(null);

        assertEquals(updatedPatient, service.update(1, patient));
        assertNull(service.update(2, patient));
        assertNull(service.update(3, null));
    }

    @Test
    void service_delete() {
        when(service.delete(1)).thenReturn(true);
        when(service.delete(2)).thenReturn(false);

        assertTrue(service.delete(1));
        assertFalse(service.delete(2));
    }

    @Test
    void repository_findAll() {
        List<Patient> patients = Arrays.asList(
                new Patient(1, "Name 1", 10, "address 1"),
                new Patient(2, "Name 2", 20, "address 2")
        );
        when(repository.findAll()).thenReturn(patients);
        assertEquals(patients, repository.findAll());
        verify(repository, times(1)).findAll();
    }

    @Test
    void repository_findById() {
        Patient patient = new Patient(1, "Name 1", 10, "address 1");
        when(repository.findById(1)).thenReturn(patient);
        when(repository.findById(2)).thenReturn(null);
        assertEquals(patient, repository.findById(1));
        assertNull(repository.findById(2));
        verify(repository, times(2)).findById(any());

    }

    @Test
    void repository_save() {
        Patient patient = new Patient(1, "Name 1", 10, "address 1");
        Patient forSave = new Patient("Name 1", 10, "address 1");
        when(repository.save(forSave)).thenReturn(patient);
        assertEquals(patient, repository.save(forSave));
        verify(repository, times(1)).save(forSave);
    }

    @Test
    void repository_update() {
        Patient patient = new Patient(1, "Name 1", 10, "address 1");
        Patient forUpdate = new Patient("Name 1", 10, "address 1");
        when(repository.update(1, forUpdate)).thenReturn(patient);
        when(repository.update(2, forUpdate)).thenReturn(null);
        assertEquals(patient, repository.update(1, forUpdate));
        assertNull(repository.update(2, forUpdate));
        verify(repository, times(1)).update(1, forUpdate);
    }

    @Test
    void repository_delete() {
        when(repository.deleteById(1)).thenReturn(true);
        when(repository.deleteById(2)).thenReturn(false);
        assertTrue(repository.deleteById(1));
        assertFalse(repository.deleteById(2));
        verify(repository, times(2)).deleteById(any());
    }
}