package com.nguyenhungtuan.lab2.Model;

public class Employee {
    private String id;
    private String fullName;
    private boolean isManager;

    public Employee(String id, String fullName, boolean isManager) {
        this.id = id;
        this.fullName = fullName;
        this.isManager = isManager;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }
}
