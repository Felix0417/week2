package com.aston_intensive.week2.servlet.dto.hospital;

public class HospitalInputDto {

    private String name;

    private String address;

    public HospitalInputDto() {
    }

    public HospitalInputDto(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
