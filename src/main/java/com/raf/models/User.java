package com.raf.models;

public class User {
    private Integer users_id;
    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private String email;
    private Integer role_id;

    public User(Integer users_id, String username, String password, String first_name, String last_name, String email, Integer role_id) {
        this.users_id = users_id;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.role_id = role_id;
    }

    public User(String username, String password, Integer role_id){
        this.username = username;
        this.password = password;
        this.role_id = role_id;
    }

    public User() {
    }

    public Integer getUsers_id() {
        return this.users_id;
    }

    public void setUsers_id(Integer users_id) {
        this.users_id = users_id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRole_id() {
        return this.role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    @Override
    public String toString() {
        return "{" +
            " users_id='" + getUsers_id() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            ", first_name='" + getFirst_name() + "'" +
            ", last_name='" + getLast_name() + "'" +
            ", email='" + getEmail() + "'" +
            ", role_id='" + getRole_id() + "'" +
            "}";
    }
}
