package models.user;

import com.avaje.ebean.Model;

import javax.persistence.*;

@Entity
@Table(name = "socobo_user")
public class User extends Model {

    @Id
    public Long id;

    @Column(length = 256, unique = true, nullable = false)
    public String userName;
}
