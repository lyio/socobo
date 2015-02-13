package models.fridge;

import models.user.User;

import javax.persistence.*;
import java.util.List;

@Entity
public class Fridge {

    @Id
    @GeneratedValue
    public Long id;

    @OneToOne(cascade = CascadeType.ALL)
    public User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Item> items;

    @Column(nullable = false)
    public long createdAt;

    public long bestBefore;

    public Fridge() {
    }

    public Fridge(final User user, final List<Item> items) {
        this.user = user;
        this.items = items;
    }
}
