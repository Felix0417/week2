package com.aston_intensive.week2.service.impl;

import com.aston_intensive.week2.model.Hospital;
import com.aston_intensive.week2.repository.impl.HospitalRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HospitalServiceImplTest {

    @Mock
    private HospitalRepositoryImpl repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll() {
        List<Hospital> hospitals = Arrays.asList(
                new Hospital(1, "Hospital 1", "address 1", LocalDateTime.now()),
                new Hospital(2, "Hospital 2", "address 2", LocalDateTime.now())
        );

        when(repository.findAll()).thenReturn(hospitals);
        assertEquals(hospitals, repository.findAll());
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById() {
        Hospital hospital = new Hospital(1, "Hospital 1", "address 1", LocalDateTime.now());
        when(repository.findById(1)).thenReturn(hospital);
        when(repository.findById(2)).thenReturn(null);
        assertEquals(hospital, repository.findById(1));
        assertNull(repository.findById(2));
        verify(repository, times(2)).findById(any());
    }

    @Test
    void save() {
        Hospital hospital = new Hospital(1, "Hospital 1", "address 1", LocalDateTime.now());
        Hospital forSave = new Hospital("Hospital 1", "address 1");
        when(repository.save(forSave)).thenReturn(hospital);
        assertEquals(hospital, repository.save(forSave));
        verify(repository, times(1)).save(forSave);
    }

    @Test
    void update() {
        Hospital hospital = new Hospital(1, "Hospital 1", "address 1", LocalDateTime.now());
        Hospital forUpdate = new Hospital("Hospital 1", "address 1");
        when(repository.update(1, forUpdate)).thenReturn(hospital);
        when(repository.update(2, forUpdate)).thenReturn(null);
        assertEquals(hospital, repository.update(1, forUpdate));
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