package com.aston_intensive.week2;

import com.aston_intensive.week2.db.PostgreSQLConnectionManager;
import com.aston_intensive.week2.model.Hospital;
import com.aston_intensive.week2.repository.impl.HospitalRepositoryImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        HospitalRepositoryImpl repository = new HospitalRepositoryImpl(new PostgreSQLConnectionManager());
        System.out.println(repository.findAll());
        Hospital hospital = new Hospital("Blokhina1saddsdghb", "address 4");
        System.out.println(repository.save(hospital));
        System.out.println(repository.findById(3));
        System.out.println(repository.findById(20));
        System.out.println(repository.deleteById(3));
    }

}
