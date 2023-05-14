package jp.ac.uaizu.azumaza.dto;

import jp.ac.uaizu.azumaza.annotation.Column;
import jp.ac.uaizu.azumaza.annotation.Table;

@Table("user")
public class User extends Record {

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
