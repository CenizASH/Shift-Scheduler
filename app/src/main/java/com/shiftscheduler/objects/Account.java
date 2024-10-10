package com.shiftscheduler.objects;

import java.util.Objects;

public class Account {
    private String id;
    private String name;
    private String password;
    private String email;
    private String type;

    public Account(String id, String name, String password, String email, String type) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.type = type;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) && Objects.equals(name, account.name) && Objects.equals(password, account.password) && Objects.equals(email, account.email) && Objects.equals(type, account.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, email, type);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
