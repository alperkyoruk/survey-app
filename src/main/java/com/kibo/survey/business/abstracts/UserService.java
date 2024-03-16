package com.kibo.survey.business.abstracts;

import com.kibo.survey.core.utilities.result.DataResult;
import com.kibo.survey.core.utilities.result.Result;
import com.kibo.survey.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    Result addUser(User user);

    DataResult<List<User>> getUsers();

    DataResult<User> getUserById(int id);

    DataResult<User> getByUsername(String username);

    DataResult<User> getByEmail(String email);

}
