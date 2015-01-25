package models.user;

import play.data.validation.Constraints;

public class Login {

            @Constraints.Required
            @Constraints.Email
            public String emailAddress;

            @Constraints.Required
            public String password;
}
