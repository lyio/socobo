package models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.fridge.Fridge;

import javax.inject.Inject;
import javax.persistence.*;

@Entity
public class User {

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
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
