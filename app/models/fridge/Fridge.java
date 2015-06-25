package models.fridge;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Fridge {

    @Id
    @GeneratedValue
    public Long id;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.REMOVE)
    public User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    public List<Item> items;

    @Column(nullable = false)
    public Long createdAt;

    public Fridge() {
        this.items = new ArrayList<>();
    }

    public Fridge(final User user, final List<Item> items) {
        this.user = user;
        this.items = items;
    }
}
