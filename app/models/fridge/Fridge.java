package models.fridge;

import com.fasterxml.jackson.annotation.JsonBackReference;
import models.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
public class Fridge {

    @Id
    @GeneratedValue
    public Long id;

    @OneToOne(cascade=CascadeType.ALL)
    @JsonBackReference
    public User user;

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    public List<Item> items;

    public Fridge() {
    }

    public Fridge(final User user, final List<Item> items) {
        this.user = user;
        this.items = items;
    }
}
