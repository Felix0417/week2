package com.aston_intensive.week2.servlet.dto.hospital;

public class HospitalOutputDto {
    private int id;

    private String name;

    private String address;

    public HospitalOutputDto(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
