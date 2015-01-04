package models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.fridge.Fridge;
import org.apache.xerces.impl.dv.util.Base64;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.Constraint;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@Entity
public class User {

    @OneToOne(cascade = CascadeType.ALL)
    public Fridge fridge;

    public String name;

    @Id
    @Column(length = 256, unique = true, nullable = false)
    public String userName;

    @Column(length = 88, nullable = false)
    @JsonIgnore
    private String shaPassword;

    public DateTime createdAt;

    @Transient
    @Constraints.MinLength(8)
    @Constraints.MaxLength(256)
    @JsonIgnore
    private String password;

    @JsonIgnore
    @Column(length = 64, unique = false, nullable = true)
    private String authToken;

    @Column(length = 256, nullable = false)
    @Constraints.MaxLength(256)
    @Constraints.Required
    @Constraints.Email
    public String email;

    public User(String name) {
        this.name = name;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        if (password != null)
            setShaPassword(Base64.encode(getSha512(password)));
    }

    public String createToken() {
        authToken = UUID.randomUUID().toString();
        return authToken;
    }

    public void deleteAuthToken() {
        authToken = null;
    }

    public static byte[] getSha512(String value) {
        try {
            return MessageDigest.getInstance("SHA-512").digest(value.getBytes("UTF-8"));
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        catch (NullPointerException e) {
            return new byte[]{};
        }
    }

    public String getShaPassword() {
        return shaPassword;
    }

    public void setShaPassword(String shaPassword) {
        this.shaPassword = shaPassword;
    }
}
