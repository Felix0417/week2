package com.aston_intensive.week2.model;

import org.mapstruct.Mapping;

import java.time.LocalDateTime;

public class Hospital {

    private int id;

    private String name;

    private String address;

    private LocalDateTime estimated;

    public Hospital() {
    }

    public Hospital(int id, String name, String address, LocalDateTime estimated) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.estimated = estimated;
    }

    public Hospital(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getEstimated() {
        return estimated;
    }

    public void setEstimated(LocalDateTime estimated) {
        this.estimated = estimated;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", estimated=" + estimated +
                '}';
    }
}
