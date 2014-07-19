package models.fridge;

import models.produce.Produce;
import models.user.User;
import play.db.ebean.Model;
import sun.util.resources.sv.CalendarData_sv;

import javax.persistence.*;
import java.util.List;

@Entity
public class Fridge extends Model {

    @Id
    @GeneratedValue
    public Long id;

    @OneToOne
    public final User owner;

    @OneToMany(cascade=CascadeType.ALL)
    public final List<Item> items;

    public Fridge(final User owner, final List<Item> items) {
        this.owner = owner;
        this.items = items;
    }

    public static Fridge findForUser(final Long userId) {
        return (Fridge) new Finder(Long.class, Fridge.class).byId(userId);
    }
}
