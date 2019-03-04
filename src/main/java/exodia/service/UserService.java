package exodia.service;

import exodia.domain.models.service.UserServiceModel;

import java.util.List;

public interface UserService {

    boolean registerUser(UserServiceModel userServiceModel);

    UserServiceModel logInUser(UserServiceModel userServiceModel);

    List<UserServiceModel> findAllUsers();
}
