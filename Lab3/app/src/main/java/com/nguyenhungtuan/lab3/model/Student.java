package com.nguyenhungtuan.lab3.model;

public class Student {
    private int id;
    private String studentId;
    private String name;
    private String phoneNumber;

    public Student(int id, String studentId, String name, String phoneNumber) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
