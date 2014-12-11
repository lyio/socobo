package models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.fridge.Fridge;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class User {

    @OneToOne(cascade = CascadeType.ALL)
    public Fridge fridge;

    public String name;

    @Id
    public String userName;

    @JsonIgnore
    public String password;

    public User(String name) {
        this.name = name;
    }

    public User() {
    }
}
