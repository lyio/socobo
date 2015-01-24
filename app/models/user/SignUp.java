package models.user;

import play.data.validation.Constraints;

public class SignUp {

    @Constraints.MaxLength(256)
    @Constraints.Required
    @Constraints.Email
    public String email;

    @Constraints.MinLength(8)
    @Constraints.MaxLength(256)
    public String password;

    @Constraints.Required
    @Constraints.MinLength(8)
    @Constraints.MaxLength(256)
    public String passwordRepeat;

    @Constraints.MinLength(3)
    @Constraints.MaxLength(20)
    @Constraints.Required
    public String userName;
}
