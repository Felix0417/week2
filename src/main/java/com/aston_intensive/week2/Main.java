package com.aston_intensive.week2;

import com.aston_intensive.week2.db.PostgreSQLConnectionManager;
import com.aston_intensive.week2.model.Doctor;
import com.aston_intensive.week2.model.Hospital;
import com.aston_intensive.week2.model.Patient;
import com.aston_intensive.week2.repository.impl.DoctorRepositoryImpl;
import com.aston_intensive.week2.repository.impl.HospitalRepositoryImpl;
import com.aston_intensive.week2.repository.impl.PatientRepositoryImpl;
import com.aston_intensive.week2.service.impl.HospitalServiceImpl;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Hospital hospital = new Hospital("Blokh", "address 4");

        HospitalRepositoryImpl repository = new HospitalRepositoryImpl(new PostgreSQLConnectionManager());
        HospitalServiceImpl service = new HospitalServiceImpl(repository);
        System.out.println(service.findAll());
        System.out.println(service.findById(5));
//        System.out.println(service.save(hospital));

//        System.out.println(repository.findAll());

//        System.out.println(repository.save(hospital));
//        System.out.println(repository.findById(3));
//        System.out.println(repository.findById(20));
//        System.out.println(repository.deleteById(3));

//        DoctorRepositoryImpl repository = new DoctorRepositoryImpl(new PostgreSQLConnectionManager());
//        System.out.println(repository.findAll());
        Doctor doctor = new Doctor("Terapevtov", 90, hospital);
//        System.out.println(repository.save(doctor));
//        System.out.println(repository.findById(3));
//        System.out.println(repository.findById(20));
//        System.out.println(repository.deleteById(3));

//        PatientRepositoryImpl repository = new PatientRepositoryImpl((new PostgreSQLConnectionManager()));
//        System.out.println(repository.findAll());
//        Patient patient = new Patient("Felix", 30, "address101");
//
//        System.out.println(repository.save(patient));
//        System.out.println(repository.findById(3));
//        System.out.println(repository.findById(20));
//        System.out.println(repository.deleteById(3));
    }

}
