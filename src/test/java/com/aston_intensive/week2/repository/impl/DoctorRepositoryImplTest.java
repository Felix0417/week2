package com.aston_intensive.week2.repository.impl;

import com.aston_intensive.week2.db.ConnectionManager;
import com.aston_intensive.week2.model.Doctor;
import com.aston_intensive.week2.model.Hospital;
import com.aston_intensive.week2.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.print.Doc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


class DoctorRepositoryImplTest {

    @BeforeEach
    void setUp() {

    }


    @Test
    void findAll() {
    }

    @Test
    void findById() throws SQLException {

    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findAllByPatientId() {
    }

    @Test
    void mapObject() {
    }
}