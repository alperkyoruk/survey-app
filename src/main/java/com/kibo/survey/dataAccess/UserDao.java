package com.kibo.survey.dataAccess;

import com.kibo.survey.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    User findById(int id);

    User findByUsername(String userName);

    User findByEmail(String email);

}
