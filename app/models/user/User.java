package models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.fridge.Fridge;
import play.data.validation.Constraints;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Entity
@Table(name="socobo_user")
public class User {

    public String name;

    @Id
    @Column(length = 256, unique = true, nullable = false)
    public String userName;

    @Column(length = 88, nullable = false)
    @JsonIgnore
    public String shaPassword;

    @Column(nullable = false)
    public long createdAt;

    @JsonIgnore
    @Column(length = 64, unique = false, nullable = true)
    public String authToken;

    @Column(length = 256, nullable = false)
    @Constraints.MaxLength(256)
    @Constraints.Required
    @Constraints.Email
    public String email;

    @Column(nullable = false)
    public String pictureUrl;

    public User(String name) {
        this.name = name;
    }

    public User() {
    }

    public String createToken() {
        authToken = UUID.randomUUID().toString();
        return authToken;
    }

    public void deleteAuthToken() {
        authToken = null;
    }

    public static byte[] createSha512(String value) {
        try {
            return MessageDigest.getInstance("SHA-512").digest(value.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            return new byte[]{};
        }
    }
}
