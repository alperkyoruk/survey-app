package com.kibo.survey.business.concretes;

import com.kibo.survey.business.abstracts.UserService;
import com.kibo.survey.business.constants.UserMessages;
import com.kibo.survey.core.utilities.result.DataResult;
import com.kibo.survey.core.utilities.result.Result;
import com.kibo.survey.core.utilities.result.SuccessDataResult;
import com.kibo.survey.core.utilities.result.SuccessResult;
import com.kibo.survey.dataAccess.UserDao;
import com.kibo.survey.entities.Role;
import com.kibo.survey.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserManager implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public Result addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAuthorities(Set.of(Role.ROLE_ADMIN));
        userDao.save(user);

        return new SuccessResult(UserMessages.userAdded);
    }

    @Override
    public DataResult<List<User>> getUsers() {
        var result = userDao.findAll();

        return new SuccessDataResult<>(result, UserMessages.usersListed);
    }

    @Override
    public DataResult<User> getUserById(int id) {
        var result = userDao.findById(id);

        return new SuccessDataResult<>(result, UserMessages.userListed);
    }

    @Override
    public DataResult<User> getByUsername(String username) {
        var result = userDao.findByUsername(username);

        return new SuccessDataResult<User>(result, UserMessages.userListed);
    }

    @Override
    public DataResult<User> getByEmail(String email) {
        var result = userDao.findByEmail(email);

        return new SuccessDataResult<User>(result, UserMessages.userListed);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = getByUsername(username).getData();
        return user;
    }
}
