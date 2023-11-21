package com.aston_intensive.week2.service.impl;

import com.aston_intensive.week2.model.Doctor;
import com.aston_intensive.week2.model.Hospital;
import com.aston_intensive.week2.repository.impl.DoctorRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class DoctorServiceImplTest {

    @Mock
    DoctorServiceImpl service;

    @Mock
    DoctorRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new DoctorServiceImpl(mock(DoctorRepositoryImpl.class));
    }

    @Test
    void service_findAll() {
        List<Doctor> doctors = Arrays.asList(
                new Doctor(1, "Smith", 50000, new Hospital()),
                new Doctor(2, "Brown", 60000, new Hospital())
        );
        when(service.findAll()).thenReturn(doctors);
        assertEquals(doctors, service.findAll());
    }

    @Test
    void service_findById() {
        Doctor doctor = new Doctor(1, "Smith", 50000, new Hospital());
        when(service.findById(1)).thenReturn(doctor);

        assertEquals(doctor, service.findById(1));
        assertNull(service.findById(500));
    }

    @Test
    void service_save() {
        Doctor doctor = new Doctor("Smith", 50000, new Hospital());
        Doctor savedDoctor = new Doctor(1, "Smith", 50000, new Hospital());
        when(service.save(doctor)).thenReturn(savedDoctor);

        assertEquals(savedDoctor, service.save(doctor));
        assertNull(service.save(null));
    }

    @Test
    void service_update() {
        Doctor doctor = new Doctor("Smith", 50000, new Hospital(1, "Name", "Address", LocalDateTime.MIN));
        Doctor updatedDoctor = new Doctor(1, "Smith", 50000, new Hospital(1, "Name", "Address", LocalDateTime.MIN));

        when(service.findById(1)).thenReturn(doctor);
        when(service.findById(2)).thenReturn(null);
        when(service.findById(2)).thenReturn(doctor);
        when(service.update(1, doctor)).thenReturn(updatedDoctor);
        when(service.update(3, null)).thenReturn(null);

        assertEquals(updatedDoctor, service.update(1, doctor));
        assertNull(service.update(2, doctor));
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
    void repository_findById() {
        Doctor doctor = new Doctor(1, "Smith", 50000, new Hospital());
        when(repository.findById(1)).thenReturn(doctor);
        Doctor actual = repository.findById(1);
        assertEquals(doctor, actual);
        verify(repository, times(1)).findById(1);
    }

    @Test
    void repository_save() {
        Doctor doctor = new Doctor(1, "Smith", 50000, new Hospital());
        Doctor forSave = new Doctor("Smith", 50000, new Hospital());
        when(repository.save(forSave)).thenReturn(doctor);
        assertEquals(doctor, repository.save(forSave));
        verify(repository, times(1)).save(forSave);
    }

    @Test
    void repository_update() {
        Doctor doctor = new Doctor(1, "Smith", 50000, new Hospital());
        Doctor forUpdate = new Doctor("Smith", 50000, new Hospital());
        when(repository.update(1, forUpdate)).thenReturn(doctor);
        when(repository.update(2, forUpdate)).thenReturn(null);
        assertEquals(doctor, repository.update(1, forUpdate));
        assertNull(repository.update(2, forUpdate));
        verify(repository, times(1)).update(2, forUpdate);
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