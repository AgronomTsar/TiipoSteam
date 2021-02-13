package com.steamnonesteam.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.time.LocalDate;
import java.util.Objects;

@DatabaseTable(tableName="User")
public class User implements Model{
    @DatabaseField(columnName = "id",id=true)
    private int id;
    @DatabaseField
    private String fname;
    @DatabaseField
    private String lname;
    @DatabaseField
    private String nickname;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private LocalDate birthday;
    @DatabaseField
    private String password;
    @DatabaseField
    private String email;
    @DatabaseField
    private String role;
    public User(){
    }
    public User(int id, String fname, String lname, String nickname, LocalDate birthday, String password, String email,String role) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.nickname = nickname;
        this.birthday = birthday;
        this.password = password;
        this.email = email;
        this.role=role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(fname, user.fname) &&
                Objects.equals(lname, user.lname) &&
                Objects.equals(nickname, user.nickname) &&
                Objects.equals(birthday, user.birthday) &&
                Objects.equals(password, user.password) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fname, lname, nickname, birthday, password, email);
    }
}
