package com.aston_intensive.week2.service.impl;

import com.aston_intensive.week2.model.Hospital;
import com.aston_intensive.week2.repository.impl.HospitalRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HospitalServiceImplTest {

    @InjectMocks
    private HospitalServiceImpl service;

    @Mock
    private HospitalRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void service_findAll() {
        List<Hospital> hospitals = Arrays.asList(
                new Hospital(1, "Hospital 1", "address 1", LocalDateTime.now()),
                new Hospital(2, "Hospital 2", "address 2", LocalDateTime.now())
        );

        when(service.findAll()).thenReturn(hospitals);
        assertEquals(hospitals, service.findAll());
    }

    @Test
    void service_findById() {
        Hospital hospital = new Hospital(1, "Hospital 1", "address 1", LocalDateTime.now());

        when(service.findById(1)).thenAnswer(invocation -> Optional.of(hospital));
        when(service.findById(2)).thenAnswer(invocation -> Optional.empty());

        assertEquals(hospital, service.findById(1));
        assertNull(service.findById(2));
    }

    @Test
    void service_save() {
        Hospital hospital = new Hospital(1, "Hospital 1", "address 1", LocalDateTime.now());
        Hospital savedHospital = new Hospital("Hospital 1", "address 1");

        when(service.save(hospital)).thenAnswer(invocation -> Optional.of(savedHospital));
        when(service.save(null)).thenAnswer(invocation -> Optional.empty());

        assertEquals(savedHospital, service.save(hospital));
        assertNull(service.save(null));
    }

    @Test
    void service_update() {
        Hospital hospital = new Hospital(1, "Hospital 1", "address 1", LocalDateTime.now());
        Hospital updatedHospital = new Hospital("Hospital 1", "address 1");

        when(service.findById(1)).thenAnswer(invocation -> Optional.of(hospital));
        when(service.findById(2)).thenAnswer(invocation -> Optional.empty());
        when(service.findById(2)).thenAnswer(invocation -> Optional.of(hospital));
        when(service.update(1, hospital)).thenAnswer(invocation -> Optional.of(updatedHospital));
        when(service.update(3, null)).thenAnswer(invocation -> Optional.empty());

        assertEquals(updatedHospital, service.update(1, hospital));
        assertNull(service.update(2, hospital));
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
        List<Hospital> hospitals = Arrays.asList(
                new Hospital(1, "Hospital 1", "address 1", LocalDateTime.now()),
                new Hospital(2, "Hospital 2", "address 2", LocalDateTime.now())
        );

        when(repository.findAll()).thenReturn(hospitals);

        assertEquals(hospitals, repository.findAll());
        verify(repository, times(1)).findAll();
    }

    @Test
    void repository_findById() {
        Hospital hospital = new Hospital(1, "Hospital 1", "address 1", LocalDateTime.now());
        when(repository.findById(1)).thenReturn(Optional.of(hospital));
        when(repository.findById(2)).thenReturn(Optional.empty());

        assertEquals(hospital, repository.findById(1).get());
        assertFalse(repository.findById(2).isPresent());
        verify(repository, times(2)).findById(any());
    }

    @Test
    void repository_save() {
        Hospital hospital = new Hospital(1, "Hospital 1", "address 1", LocalDateTime.now());
        Hospital forSave = new Hospital("Hospital 1", "address 1");
        when(repository.save(forSave)).thenReturn(Optional.of(hospital));

        assertEquals(hospital, repository.save(forSave).get());
        verify(repository, times(1)).save(forSave);
    }

    @Test
    void repository_update() {
        Hospital hospital = new Hospital(1, "Hospital 1", "address 1", LocalDateTime.now());
        Hospital forUpdate = new Hospital("Hospital 1", "address 1");
        when(repository.update(1, forUpdate)).thenAnswer(invocation -> Optional.of(hospital));
        when(repository.update(2, forUpdate)).thenAnswer(invocation -> Optional.empty());

        assertEquals(hospital, repository.update(1, forUpdate).get());
        assertFalse(repository.update(2, forUpdate).isPresent());
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