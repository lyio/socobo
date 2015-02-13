package controllers.userController;


import biz.user.UserService;
import controllers.ControllerTestBase;
import controllers.UserController;
import datalayer.UserRepository;
import models.user.User;

public class UserControllerTestBase extends ControllerTestBase {
    protected UserRepository userRepository;
    protected UserService userService;
    protected User testUser;
    protected UserController controllerUnderTest;
}

